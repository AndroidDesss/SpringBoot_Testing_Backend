package com.desss.zcms.service;

import com.desss.zcms.config.EncryptionService;
import com.desss.zcms.entity.MailConfiguration;
import com.desss.zcms.repository.MailConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final MailConfigurationRepository mailConfigRepo;
    private final EncryptionService encryptionService;

    public void send(Long websiteId, String toEmail, String subject, String body) {
        mailConfigRepo.findByWebsiteIdAndStatus(websiteId, 1).ifPresentOrElse(
            config -> doSend(config, toEmail, subject, body),
            () -> log.warn("[MailService] No active mail config for websiteId={}", websiteId)
        );
    }

    private void doSend(MailConfiguration cfg, String to, String subject, String body) {
        try {
            JavaMailSenderImpl sender = buildSender(cfg);
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(cfg.getMailFrom() != null ? cfg.getMailFrom() : cfg.getEmail());
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            sender.send(msg);
            log.info("[MailService] Email sent to={} subject={}", to, subject);
        } catch (Exception e) {
            log.error("[MailService] Failed to send email: {}", e.getMessage(), e);
        }
    }

    private JavaMailSenderImpl buildSender(MailConfiguration cfg) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(cfg.getHost());
        sender.setPort(Integer.parseInt(cfg.getPort() != null ? cfg.getPort() : "587"));
        sender.setUsername(cfg.getEmail());
        // Decrypt the password before use — it is stored encrypted in DB
        sender.setPassword(encryptionService.decrypt(cfg.getPassword()));

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        return sender;
    }
}
