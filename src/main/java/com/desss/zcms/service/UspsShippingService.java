package com.desss.zcms.service;

import com.desss.zcms.config.UspsConfig;
import com.desss.zcms.dto.UspsRateRequestDto;
import com.desss.zcms.dto.UspsRateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UspsShippingService {

    private final UspsAuthService authService;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<UspsRateResponseDto> getRates(UspsRateRequestDto req) {

        String token = authService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("originZIPCode", req.getOriginZip());
        body.put("destinationZIPCode", req.getDestinationZip());
        body.put("weight", req.getWeight());
        body.put("length", req.getLength());
        body.put("width", req.getWidth());
        body.put("height", req.getHeight());
        body.put("mailClass", req.getMailClass());
        body.put("processingCategory", req.getProcessingCategory());
        body.put("destinationEntryFacilityType", req.getDestinationEntryFacilityType());
        body.put("rateIndicator", req.getRateIndicator());
        body.put("priceType", req.getPriceType());

        System.out.println("FINAL USPS BODY: " + body);

        // 🔥 DEBUG (VERY IMPORTANT)
        System.out.println("USPS FINAL BODY: " + body);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                UspsConfig.RATE_URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        // Simple parsing (depends on actual response)
        List<UspsRateResponseDto> result = new ArrayList<>();

        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && responseBody.containsKey("rates")) {

            Map<String, Object> rates = (Map<String, Object>) responseBody.get("rates");

            if (rates != null && rates.containsKey("rate")) {

                Object rateObj = rates.get("rate");

                // USPS sometimes returns single object OR list
                if (rateObj instanceof Map) {

                    Map<String, Object> rate = (Map<String, Object>) rateObj;

                    result.add(new UspsRateResponseDto(
                            rate.get("mailClass").toString(),
                            Double.valueOf(rate.get("price").toString())
                    ));

                } else if (rateObj instanceof List) {
                    List<Map<String, Object>> rateList =
                            (List<Map<String, Object>>) rateObj;

                    for (Map<String, Object> rate : rateList) {
                        result.add(new UspsRateResponseDto(
                                rate.get("mailClass").toString(),
                                Double.valueOf(rate.get("price").toString())
                        ));
                    }
                }
            }
        }

        return result;
    }
}
