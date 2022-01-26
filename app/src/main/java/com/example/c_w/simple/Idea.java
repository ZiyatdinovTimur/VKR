package com.example.c_w.simple;

public class Idea {

    private String id;
    private String creator;
    private String topic;
    private String description;
    private String request;
    private String people;
    private String imageref;

    public String getImageref() { return imageref; }

    public void setImageref(String imageref) { this.imageref = imageref; }

    public String getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public Idea() {
    }

    public Idea(String creator, String topic, String description, String request, String people, String imageref) {
        this.creator = creator;
        this.topic = topic;
        this.description = description;
        this.request = request;
        this.people = people;
        this.imageref = imageref;
    }
}
