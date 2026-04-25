package com.desss.zcms.service;

import com.desss.zcms.dto.UpsRateResponse;
import com.desss.zcms.dto.UpsShippingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class UpsShippingService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String CLIENT_ID = "7mVZGFmn8y3Wb19fBHQXEBY4KFkn3Z8p576AZCAfywLjYM3G";
    private final String CLIENT_SECRET = "zSwRqv34FWHYoqa3flgtQ3635XGGL1sdGPsyE9rGAp4GtJowRVaCRYTvffZt9r3G";

    private final Map<String, String> serviceNames = Map.of(
            "01", "UPS Next Day Air",
            "02", "UPS 2nd Day Air",
            "03", "UPS Ground",
            "12", "UPS 3 Day Select",
            "13", "UPS Next Day Air Saver",
            "14", "UPS Next Day Air Early AM",
            "59", "UPS 2nd Day Air AM"
    );

    public List<UpsRateResponse> getRates(UpsShippingRequest req) {

        if (req.getShippingZip() == null || req.getWarehouseZipCode() == null) {
            throw new RuntimeException("Missing required inputs");
        }

        double weight = (req.getWeight() == null || req.getWeight() <= 0) ? 1.0 : req.getWeight();

        String token = getAccessToken();

        List<String> services = List.of("03","02","01","12","13","14","59");
        List<UpsRateResponse> resultList = new ArrayList<>();

        for (String serviceCode : services) {

            try {
                Map<String, Object> payload = buildPayload(req, serviceCode, weight);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(token);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(
                        "https://wwwcie.ups.com/api/rating/v2409/Rate", // SANDBOX
                        entity,
                        Map.class
                );

                Map body = response.getBody();

                Map rateResponse = (Map) body.get("RateResponse");
                List ratedShipment = (List) rateResponse.get("RatedShipment");

                if (ratedShipment != null && !ratedShipment.isEmpty()) {
                    Map shipment = (Map) ratedShipment.get(0);
                    Map totalCharges = (Map) shipment.get("TotalCharges");

                    Double amount = Double.valueOf(
                            totalCharges.get("MonetaryValue").toString()
                    );

                    resultList.add(new UpsRateResponse(
                            serviceCode,
                            serviceNames.get(serviceCode),
                            amount
                    ));
                }

            } catch (Exception e) {
                log.error("Service {} failed: {}", serviceCode, e.getMessage());
            }
        }

        return resultList;
    }

    private String getAccessToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String auth = Base64.getEncoder()
                .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        headers.set("Authorization", "Basic " + auth);
        headers.set("x-merchant-id", "777884");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://wwwcie.ups.com/security/v1/oauth/token", // SANDBOX
                entity,
                Map.class
        );

        return (String) response.getBody().get("access_token");
    }

    private Map<String, Object> buildPayload(UpsShippingRequest req,
                                             String serviceCode,
                                             double weight) {
        return Map.of(
                "RateRequest", Map.of(
                        "Shipment", Map.of(
                                "Shipper", Map.of("Address",
                                        Map.of("PostalCode", req.getWarehouseZipCode(), "CountryCode", "US")
                                ),
                                "ShipTo", Map.of("Address",
                                        Map.of("PostalCode", req.getShippingZip(), "CountryCode", "US")
                                ),
                                "ShipFrom", Map.of("Address",
                                        Map.of("PostalCode", req.getWarehouseZipCode(), "CountryCode", "US")
                                ),
                                "Service", Map.of("Code", serviceCode),
                                "Package", Map.of(
                                        "PackagingType", Map.of("Code", "02"),
                                        "PackageWeight", Map.of(
                                                "UnitOfMeasurement", Map.of("Code", "LBS"),
                                                "Weight", String.format("%.2f", weight)
                                        )
                                )
                        )
                )
        );
    }
}
