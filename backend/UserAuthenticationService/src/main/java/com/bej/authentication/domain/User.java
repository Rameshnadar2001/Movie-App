package com.bej.authentication.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

// Add the @Entity annotation
@Entity
public class User {
    // Make userId as the primary key by using the @Id annotation
    @Id
    private String emailId;
    private String password;
    private  String userName;
    private Boolean isEnabled;

    public User() {
    }
    public User(String emailId, String password, String userName) {
        this.emailId = emailId;
        this.password = password;
        this.userName = userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                '}';
    }
}
