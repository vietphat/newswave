package com.vietphat.newswave.api;

import com.vietphat.newswave.dto.EmailDetails;
import com.vietphat.newswave.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailAPI {

    private EmailService emailService;

    @Autowired
    public EmailAPI(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails emailDetails) {

        String status = emailService.sendSimpleMail(emailDetails);

        return status;
    }

    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails emailDetails) {

        String status = emailService.sendMailWithAttachment(emailDetails);

        return status;
    }
}
