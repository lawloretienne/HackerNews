package com.etiennelawlor.hackernews.network;

public enum EndpointUrl {
    PRODUCTION ("https://hacker-news.firebaseio.com/v0");

    public final String value;

    private EndpointUrl(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}