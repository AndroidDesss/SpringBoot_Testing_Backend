package com.desss.zcms.service;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.PaymentRequest;
import com.desss.zcms.entity.PaymentConfiguration;
import com.desss.zcms.repository.PaymentConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentConfigurationRepository repo;
    private final EncryptionService encryptionService;

    public String makePayment(PaymentRequest request) {
        return repo.findByWebsiteIdAndStatus(request.getWebsiteId(), 1)
                .map(config -> processPayment(config, request))
                .orElse("No active payment config found");
    }

    private String processPayment(PaymentConfiguration cfg, PaymentRequest req) {
        try {
            String transactionKey =
                    encryptionService.decrypt(cfg.getTransactionKey());

            String url = cfg.getEnvironment().equalsIgnoreCase("SANDBOX") ?
                    "https://apitest.authorize.net/xml/v1/request.api" :
                    "https://api.authorize.net/xml/v1/request.api";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Authorize.Net JSON structure
            Map<String, Object> body = new HashMap<>();

            Map<String, Object> auth = new HashMap<>();
            auth.put("name", cfg.getApiLoginId());
            auth.put("transactionKey", transactionKey);

            Map<String, Object> payment = new HashMap<>();
            Map<String, Object> creditCard = new HashMap<>();
            creditCard.put("cardNumber", req.getCardNumber());
            creditCard.put("expirationDate", req.getExpiry());
            creditCard.put("cardCode", req.getCvv());

            payment.put("creditCard", creditCard);

            Map<String, Object> transactionRequest = new HashMap<>();
            transactionRequest.put("transactionType", "authCaptureTransaction");
            transactionRequest.put("amount", req.getAmount());
            transactionRequest.put("payment", payment);

            body.put("createTransactionRequest", Map.of(
                    "merchantAuthentication", auth,
                    "transactionRequest", transactionRequest
            ));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            log.info("Payment Response: {}", response.getBody());

            return response.getBody();

        } catch (Exception e) {
            log.error("Payment failed: {}", e.getMessage(), e);
            return "Payment Failed";
        }
    }
}
