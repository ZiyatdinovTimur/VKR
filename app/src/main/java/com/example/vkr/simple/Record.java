package com.example.vkr.simple;

public class Record {
    private String sourcePhrase;
    private String targetPhrase;

    public String getSourcePhrase() {
        return sourcePhrase;
    }

    public void setSourcePhrase(String sourcePhrase) {
        this.sourcePhrase = sourcePhrase;
    }

    public String getTargetPhrase() {
        return targetPhrase;
    }

    public void setTargetPhrase(String targetPhrase) {
        this.targetPhrase = targetPhrase;
    }

    public Record(String sourcePhrase, String targetPhrase) {
        this.sourcePhrase = sourcePhrase;
        this.targetPhrase = targetPhrase;
    }
}
