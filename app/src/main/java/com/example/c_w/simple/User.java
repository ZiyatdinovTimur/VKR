package com.example.c_w.simple;

public class User {

    private String id;
    private String name;
    private String surname;
    private String email;
    private String emailaddres;
    private String specialization;
    private String phoneNumber;
    private String ideas;
    private String matches;
    private String responses;

    public String getEmailaddres() {
        return emailaddres;
    }

    public void setEmailaddres(String emailaddres) {
        this.emailaddres = emailaddres;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public User(String name, String surname, String email, String emailaddres, String specialization,
                String phoneNumber, String ideas, String matches, String responses) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.emailaddres = emailaddres;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.ideas = ideas;
        this.matches = matches;
        this.responses = responses;
    }

    public User() {
    }

    public String getIdeas() {
        return ideas;
    }

    public void setIdeas(String ideas) {
        this.ideas = ideas;
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

}
