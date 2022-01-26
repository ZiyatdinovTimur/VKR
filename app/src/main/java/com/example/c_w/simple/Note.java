package com.example.c_w.simple;

public class Note {
    String id;
    String creator;
    String topic;
    String description;

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

    public Note() {
    }

    public Note(String id, String creator, String topic, String description) {
        this.id = id;
        this.creator = creator;
        this.topic = topic;
        this.description = description;
    }
}
