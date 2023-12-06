package com.pbl4.monolingo.rest;

import com.pbl4.monolingo.entity.Notebook;
import com.pbl4.monolingo.service.AccountService;
import com.pbl4.monolingo.service.NotebookService;
import com.pbl4.monolingo.utility.ShuffleArray;
import com.pbl4.monolingo.utility.dto.ChatGPTRequest;
import com.pbl4.monolingo.utility.dto.ChatGPTResponse;
import gov.nih.nlm.nls.lvg.Util.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/practice")
public class BotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate restTemplate;
    private final NotebookService notebookService;

    HashMap<Integer, String> answerCache = new HashMap<>();
    HashMap<Integer, Boolean> inProcessing = new HashMap<>();

    public BotController(RestTemplate restTemplate,
                         NotebookService notebookService) {
        this.restTemplate = restTemplate;
        this.notebookService = notebookService;
    }

    @GetMapping("/sentences/{accountId}/{amount}")
    public String getSentences(
            @PathVariable int accountId,
            @PathVariable int amount,
            @RequestHeader(value = "request-source", required = false) String requestSource) throws InterruptedException {
        if (answerCache.get(accountId) == null) {

            if (inProcessing.get(accountId) == null || !inProcessing.get(accountId)) {
                inProcessing.put(accountId, true);
                System.out.println("I'm getting");

                List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(accountId);
                ShuffleArray.shuffle(notebooks.toArray());

                String prompt = getPrompt(amount, notebooks);

                System.out.println(prompt);
                ChatGPTRequest request = new ChatGPTRequest(model, prompt);
                ChatGPTResponse response = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);

                answerCache.put(accountId, response.getChoices().get(0).getMessage().getContent());

                inProcessing.put(accountId, false);
                System.out.println(answerCache.get(accountId));
            } else if (requestSource != null) {
                while (answerCache.get(accountId) == null) { Thread.sleep(1000); System.out.println("I'm waiting");}
            }
        }

        return answerCache.get(accountId);
    }

    private String getPrompt(int amount, List<Notebook> notebooks) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < notebooks.size(); ++i) {
            builder.append("'");
            builder.append(notebooks.get(i).getLexicon().getWord().replace('_', ' '));
            builder.append("'");
            if (i != notebooks.size() - 1)
                builder.append(", ");
        }

        return "Create " + amount +" common sentences containing the following words and their Vietnamese meanings in JSON format with keys are 'English' and 'Vietnamese': " + builder.toString();
    }

}
