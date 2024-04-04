package com.bej.authentication.domain;


import javax.persistence.*;


import java.util.UUID;

@Entity
public class Confirmations {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long CId;
   private String token;
   @ManyToOne
   private User user;

    public Confirmations() {
    }

    public Confirmations(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }

    public long getCId() {
        return CId;
    }

    public void setCId(long CId) {
        this.CId = CId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Confirmations{" +
                "CId=" + CId +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
