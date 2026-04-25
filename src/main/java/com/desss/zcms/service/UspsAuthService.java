package com.desss.zcms.service;

import com.desss.zcms.config.UspsConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UspsAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", UspsConfig.CLIENT_ID);
        body.add("client_secret", UspsConfig.CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                UspsConfig.TOKEN_URL,
                request,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }
}
