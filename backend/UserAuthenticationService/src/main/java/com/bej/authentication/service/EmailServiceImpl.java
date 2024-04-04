package com.bej.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import static com.bej.authentication.utils.EmailUtils.getVerifcationUrl;


@Service
public class EmailServiceImpl implements EmailService{
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account verification";
    @Value("${spring.mail.verify.host}")
    private String host;
  @Value("${spring.mail.username}")
    private  String fromEmail;
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendEmailWithAttachments(String name, String to, String token) {
      try{
          Context context =new Context();
          context.setVariable("name",name);
          context.setVariable("url",getVerifcationUrl(host,token));
          String text= templateEngine.process("emailTemplate",context);
          MimeMessage message=getMimeMessage();
          MimeMessageHelper helper=new MimeMessageHelper(message);
          helper.setPriority(1);
          helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
          helper.setFrom(fromEmail);
          helper.setTo(to);
          helper.setText(text, true);
          emailSender.send(message);
      }catch (Exception exception){
          System.out.println(exception.getMessage());
          throw new RuntimeException(exception.getMessage());
      }
    }

    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }
}
