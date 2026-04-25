package com.desss.zcms.service;

import com.desss.zcms.dto.PaypalRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaypalService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String CLIENT_ID = "AW2_6pXQELpIq2PJNHuSsjaMVv9S_dTgT2C8uIcy2fFUtN60-ug5DhA0iEWyfN9Dy42WBrBHqkj-2915";
    private final String SECRET = "EEeX-SVYRPucAoSjAdVeDuAZeFavCMk_HJogDDCVs6vNGoPyqKcYR4mPUGSKM-UskqoZY-NPEcMasTGx";

    // 🔑 GET TOKEN
    private String getToken() {

        String url = "https://api-m.sandbox.paypal.com/v1/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, SECRET);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // 💳 CREATE ORDER
    public String createPayment(PaypalRequest req) {

        try {

            String token = getToken();

            String url = "https://api-m.sandbox.paypal.com/v2/checkout/orders";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = Map.of(
                    "intent", "CAPTURE",
                    "purchase_units", List.of(
                            Map.of(
                                    "amount", Map.of(
                                            "currency_code", req.getCurrency(),
                                            "value", req.getAmount().toString()
                                    )
                            )
                    )
            );

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(payload, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            System.out.println("PAYPAL RESPONSE: " + response.getBody());

            return response.getBody();

        } catch (Exception e) {
            log.error("PayPal Error: {}", e.getMessage(), e);
            return "PayPal API Failed";
        }
    }
}
