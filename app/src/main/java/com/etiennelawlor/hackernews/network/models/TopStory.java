package com.etiennelawlor.hackernews.network.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by etiennelawlor on 3/21/15.
 */

public final class TopStory {
    
    //region Variables
    @SerializedName("by")
    private String mBy;
    @SerializedName("descendants")
    private Integer mDescendants;
    @SerializedName("id")
    private Long mId;
    @SerializedName("kids")
    private List<Long> mKids;
    @SerializedName("score")
    private Integer mScore;
    @SerializedName("text")
    private String mText;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;
    
    //endregion

    //region Getters

    public String getBy() {
        if (TextUtils.isEmpty(mBy))
            return "";
        else
            return mBy;
    }

    public Integer getDescendants() {
        if(mDescendants == null)
            return -1;
        else
            return mDescendants;
    }

    public Long getId() {
        if(mId == null)
            return -1L;
        else
            return mId;
    }

    public List<Long> getKids() {
        return mKids;
    }

    public Integer getScore() {
        if(mScore == null)
            return -1;
        else
            return mScore;
    }

    public String getText() {
        if (TextUtils.isEmpty(mText))
            return "";
        else
            return mText;
    }

    public Long getTime() {
        if(mTime == null)
            return -1L;
        else
            return mTime;
    }

    public String getTitle() {
        if (TextUtils.isEmpty(mTitle))
            return "";
        else
            return mTitle;
    }

    public String getType() {
        if (TextUtils.isEmpty(mType))
            return "";
        else
            return mType;
    }

    public String getUrl() {
        if (TextUtils.isEmpty(mUrl))
            return "";
        else
            return mUrl;
    }

    //endregion

    // region Setters

    public void setBy(String by) {
        mBy = by;
    }

    public void setDescendants(Integer descendants) {
        mDescendants = descendants;
    }

    public void setId(Long id) {
        mId = id;
    }

    public void setKids(List<Long> kids) {
        mKids = kids;
    }

    public void setScore(Integer score) {
        mScore = score;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    // endregion
}