package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.EmailDetails;
import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public boolean sendSimpleMail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception exception) {
            System.out.println(exception);
            return false;
        }
    }

    @Override
    public boolean sendResetPasswordUrl(ResetPasswordTokenEntity token, UserEntity user) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(token.getUser().getEmail());
        mailMessage.setSubject("[News Wave] Tạo lại mật khẩu mới");

        // TODO: set to SystemConstant
        String currentContextPath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String url = currentContextPath + "/xac-thuc/tao-lai-mat-khau/" + token.getToken();

        mailMessage.setText(url);

        javaMailSender.send(mailMessage);
        return true;
    }
}
