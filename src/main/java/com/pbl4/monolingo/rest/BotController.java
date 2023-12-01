package com.pbl4.monolingo.rest;

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

@RestController
@RequestMapping("/practice")
public class BotController {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sentences/{accountId}/{amount}")
    public String getSentences(
            @PathVariable int accountId,
            @PathVariable int amount) {
        String prompt = "Create 13 common sentences containing the following words and their Vietnamese meanings in JSON format: 'Tom', 'helmet', 'play', 'handsome', 'run a risk'";
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse response = restTemplate.postForObject(apiURL, request, ChatGPTResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
