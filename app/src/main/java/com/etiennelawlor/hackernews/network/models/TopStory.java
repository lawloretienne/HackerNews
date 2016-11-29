package com.etiennelawlor.hackernews.network.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by etiennelawlor on 3/21/15.
 */

public final class TopStory {
    
    // region Fields
    @SerializedName("by")
    private String by;
    @SerializedName("descendants")
    private Integer descendants;
    @SerializedName("id")
    private Long id;
    @SerializedName("kids")
    private List<Long> kids;
    @SerializedName("score")
    private Integer score;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private Long time;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;
    // endregion

    // region Getters

    public String getBy() {
        if (TextUtils.isEmpty(by))
            return "";
        else
            return by;
    }

    public Integer getDescendants() {
        if(descendants == null)
            return -1;
        else
            return descendants;
    }

    public Long getId() {
        if(id == null)
            return -1L;
        else
            return id;
    }

    public List<Long> getKids() {
        return kids;
    }

    public Integer getScore() {
        if(score == null)
            return -1;
        else
            return score;
    }

    public String getText() {
        if (TextUtils.isEmpty(text))
            return "";
        else
            return text;
    }

    public Long getTime() {
        if(time == null)
            return -1L;
        else
            return time;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(title))
            return "";
        else
            return title;
    }

    public String getType() {
        if (TextUtils.isEmpty(type))
            return "";
        else
            return type;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(url))
            return "";
        else
            return url;
    }

    // endregion

    // region Setters

    public void setBy(String by) {
        this.by = by;
    }

    public void setDescendants(Integer descendants) {
        this.descendants = descendants;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // endregion
}