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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    public BotController(RestTemplate restTemplate,
                         NotebookService notebookService) {
        this.restTemplate = restTemplate;
        this.notebookService = notebookService;
    }

    @GetMapping("/sentences/{accountId}/{amount}")
    public String getSentences(
            @PathVariable int accountId,
            @PathVariable int amount) {
        List<Notebook> notebooks = notebookService.getAllNotebooksByAccountId(accountId);
        ShuffleArray.shuffle(notebooks.toArray());

        String prompt = getPrompt(amount, notebooks);

        System.out.println(prompt);
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse response = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
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
