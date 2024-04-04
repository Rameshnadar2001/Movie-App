package com.bej.authentication.service;


public interface EmailService {

    void sendEmailWithAttachments(String name,String to,String token);
}
