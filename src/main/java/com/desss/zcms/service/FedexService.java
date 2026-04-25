package com.desss.zcms.service;

import com.desss.zcms.dto.FedexRateRequest;
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
public class FedexService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String CLIENT_ID = "l728516676a1d8468a82fadfc944ddcbb0";
    private final String CLIENT_SECRET = "fdc81dedca454d4db7d2886c945456fc";

    // 🔑 Get OAuth Token
    private String getToken() {

        String url = "https://apis-sandbox.fedex.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return (String) response.getBody().get("access_token");
    }

    // 📦 Rate API
    public String getRates(FedexRateRequest req) {

        try {

            String token = getToken();

            String url = "https://apis-sandbox.fedex.com/rate/v1/rates/quotes";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> payload = Map.of(
                    "accountNumber", Map.of("value", "YOUR_FEDEX_ACCOUNT"),
                    "requestedShipment", Map.of(
                            "shipper", Map.of("address",
                                    Map.of("postalCode", req.getFromZip(), "countryCode", "US")),
                            "recipient", Map.of("address",
                                    Map.of("postalCode", req.getToZip(), "countryCode", "US")),
                            "pickupType", "DROPOFF_AT_FEDEX_LOCATION",
                            "rateRequestType", List.of("ACCOUNT"),
                            "requestedPackageLineItems", List.of(
                                    Map.of(
                                            "weight", Map.of(
                                                    "units", "LB",
                                                    "value", req.getWeight()
                                            )
                                    )
                            )
                    )
            );

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(payload, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            System.out.println("FEDEX RESPONSE: " + response.getBody());

            return response.getBody();

        } catch (Exception e) {
            log.error("FedEx Error: {}", e.getMessage(), e);
            return "FedEx API Failed";
        }
    }
}
