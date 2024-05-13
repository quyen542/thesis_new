package com.example.thesis_new.dto;

public class registerDTO {

    private String username;
    private String password;

    private String repassword;
    private String name;
    private String email;
    private String role;
    private String verificationCode;
    private boolean isConfirm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "registerDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", repassword='" + repassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", isConfirm=" + isConfirm +
                '}';
    }
}
