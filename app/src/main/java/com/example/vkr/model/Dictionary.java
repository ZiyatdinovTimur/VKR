package com.example.vkr.model;

public class Dictionary {


    private int id;

    String ownerId;
    String sourcePhrase;
    String translatedPhrase;
    String sourceLanguage;
    String translateLanguage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourcePhrase() {
        return sourcePhrase;
    }

    public void setSourcePhrase(String sourcePhrase) {
        this.sourcePhrase = sourcePhrase;
    }

    public String getTranslatedPhrase() {
        return translatedPhrase;
    }

    public void setTranslatedPhrase(String translatedPhrase) {
        this.translatedPhrase = translatedPhrase;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public String getTranslateLanguage() {
        return translateLanguage;
    }

    public void setTranslateLanguage(String translateLanguage) {
        this.translateLanguage = translateLanguage;
    }
    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", sourcePhrase='" + sourcePhrase + '\'' +
                ", translatedPhrase='" + translatedPhrase + '\'' +
                ", sourceLanguage='" + sourceLanguage + '\'' +
                ", translateLanguage='" + translateLanguage + '\'' +
                '}';
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

}
