package com.example.vkr.simple;

public class User {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String emailaddres;
    private String phoneNumber;
    private String speakLanguage;
    private String additionalSpeakLanguage;
    private String learnLanguage;
    private String additionalLearnLanguage;
    private String matches;
    private String responses;
    private String followers;
    private String followings;
    private String status;
    private String countryCode;

    public String getEmailaddres() {
        return emailaddres;
    }

    public void setEmailaddres(String emailaddres) {
        this.emailaddres = emailaddres;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String name, String surname, String email, String emailaddres,
                String phoneNumber, String speakLanguage, String additionalSpeakLanguage, String learnLanguage, String additionalLearnLanguage, String matches, String responses, String followers, String followings, String status, String countryCode) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.emailaddres = emailaddres;
        this.phoneNumber = phoneNumber;
        this.speakLanguage = speakLanguage;
        this.additionalSpeakLanguage = additionalSpeakLanguage;
        this.learnLanguage = learnLanguage;
        this.additionalLearnLanguage = additionalLearnLanguage;
        this.matches = matches;
        this.responses = responses;
        this.followers = followers;
        this.followings = followings;
        this.status = status;
        this.countryCode = countryCode;
    }

    public User() {
    }

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public String getSpeakLanguage() {
        return speakLanguage;
    }

    public void setSpeakLanguage(String speakLanguage) {
        this.speakLanguage = speakLanguage;
    }

    public String getAdditionalSpeakLanguage() {
        return additionalSpeakLanguage;
    }

    public void setAdditionalSpeakLanguage(String additionalSpeakLanguage) {
        this.additionalSpeakLanguage = additionalSpeakLanguage;
    }

    public String getLearnLanguage() {
        return learnLanguage;
    }

    public void setLearnLanguage(String learnLanguage) {
        this.learnLanguage = learnLanguage;
    }

    public String getAdditionalLearnLanguage() {
        return additionalLearnLanguage;
    }

    public void setAdditionalLearnLanguage(String additionalLearnLanguage) {
        this.additionalLearnLanguage = additionalLearnLanguage;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowings() {
        return followings;
    }

    public void setFollowings(String followings) {
        this.followings = followings;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
