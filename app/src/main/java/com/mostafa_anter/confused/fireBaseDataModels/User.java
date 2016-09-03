package com.mostafa_anter.confused.fireBaseDataModels;

/**
 * Created by mostafa_anter on 9/3/16.
 */
public class User {
    private String username;
    private String email;
    private Long phone;
    private String status;
    private String avatarUrl;

    public User() {
    }

    public User(String username, String email, Long phone, String status, String avatarUrl) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
