package com.desss.zcms.service;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.dto.SmsSendRequest;
import com.desss.zcms.entity.SmsConfiguration;
import com.desss.zcms.repository.SmsConfigurationRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsService {

    private final SmsConfigurationRepository repo;
    private final EncryptionService encryptionService;

    public void send(SmsSendRequest request) {
        SmsConfiguration config = repo.findByWebsiteIdAndStatus(request.getWebsiteId(), 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));
        sendSms(config, request);
    }

    private void sendSms(SmsConfiguration cfg, SmsSendRequest req) {
        try {
            String accountSid = encryptionService.decrypt(cfg.getAccountSid());
            String authToken = encryptionService.decrypt(cfg.getAuthToken());

            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(req.getMobileNumber()),
                    new PhoneNumber(cfg.getTwilioNumber()),
                    req.getMessage()
            ).create();

            log.info("SMS sent successfully. SID: {}", message.getSid());

        } catch (Exception e) {
            log.error("SMS sending failed: {}", e.getMessage(), e);
            throw new RuntimeException("SMS sending failed");
        }
    }

    public String sendOtp(Long websiteId, String mobileNumber) {
        SmsConfiguration config = repo.findByWebsiteIdAndStatus(websiteId, 1)
                .orElseThrow(() -> new RuntimeException("SMS config not found"));
        String otp = generateOtp();
        try {
            String accountSid = encryptionService.decrypt(config.getAccountSid());
            String authToken = encryptionService.decrypt(config.getAuthToken());
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                    new PhoneNumber(mobileNumber),
                    new PhoneNumber(config.getTwilioNumber()),
                    "Your OTP is: " + otp
            ).create();
            log.info("OTP sent successfully: {} | SID: {}", otp, message.getSid());
            return otp;
        } catch (Exception e) {
            log.error("OTP sending failed", e);
            throw new RuntimeException("OTP sending failed");
        }
    }

    private String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }
}