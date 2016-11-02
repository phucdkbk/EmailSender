/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.utils;

import com.phucdk.emailsender.object.EmailConfig;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Administrator
 */
public class EmailUtils {

    public static void sendEmail(EmailConfig emailConfig, String content, String subject, String senderEmail, String receiverEmail) {
        Session session = loginEmail(emailConfig);
        try {
            //Message message = new MimeMessage(session);
            MimeMessage message= new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail));
            message.setSubject(subject, "utf-8");            
            message.setContent(content, "text/html; charset=utf-8");
            Transport.send(message);            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Session loginEmail(EmailConfig emailConfig) {
        Properties props = new Properties();
        props.put("mail.smtp.host", emailConfig.getSmtpHost());
        props.put("mail.smtp.socketFactory.port", emailConfig.getSmtpSocketPort());
        props.put("mail.smtp.socketFactory.class", emailConfig.getSmtpClass());
        props.put("mail.smtp.auth", emailConfig.getSmtpAuth());
        props.put("mail.smtp.port", emailConfig.getSmtpPort());               

        final String userName = emailConfig.getEmailAddress();
        final String password = emailConfig.getPassword();

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });
        return session;
    }
}
