/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phucdk.emailsender.object;

/**
 *
 * @author Administrator
 */
public class EmailConfig {

    private String smtpHost;
    private String smtpSocketPort;
    private String smtpClass;
    private String smtpAuth;
    private String smtpPort;
    private String emailAddress;
    private String password;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpSocketPort() {
        return smtpSocketPort;
    }

    public void setSmtpSocketPort(String smtpSocketPort) {
        this.smtpSocketPort = smtpSocketPort;
    }

    public String getSmtpClass() {
        return smtpClass;
    }

    public void setSmtpClass(String smtpClass) {
        this.smtpClass = smtpClass;
    }

    public String getSmtpAuth() {
        return smtpAuth;
    }

    public void setSmtpAuth(String smtpAuth) {
        this.smtpAuth = smtpAuth;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
