package com.etiennelawlor.hackernews.network;

public enum EndpointUrl {

    PRODUCTION ("https://hacker-news.firebaseio.com/v0");

    // region Member Variables
    public final String mValue;
    // endregion

    EndpointUrl(String value) {
        this.mValue = value;
    }

    @Override
    public String toString() {
        return mValue;
    }
}