package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.EmailDetails;
import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;

public interface EmailService {

    boolean sendSimpleMail(EmailDetails emailDetails);

    boolean sendResetPasswordUrl(ResetPasswordTokenEntity token, UserEntity user);
}
