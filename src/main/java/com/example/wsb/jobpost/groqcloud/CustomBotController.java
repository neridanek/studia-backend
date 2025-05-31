package com.example.wsb.jobpost.groqcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1/bot")
public class CustomBotController {

    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt){
        GroqCloudRequest request=new GroqCloudRequest(model, prompt);
        GroqCloudResponse groqCloudResponse = template.postForObject(apiURL, request, GroqCloudResponse.class);
        return groqCloudResponse.getChoices().get(0).getMessage().getContent();
    }
}
