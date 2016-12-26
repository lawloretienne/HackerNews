package com.etiennelawlor.hackernews.network.models;

import android.content.Context;
import android.text.TextUtils;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.utilities.DateUtility;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.List;

/**
 * Created by etiennelawlor on 3/21/15.
 */

public final class TopStory {
    
    // region Fields
    @SerializedName("by")
    private String by;
    @SerializedName("descendants")
    private int descendants;
    @SerializedName("id")
    private long id;
    @SerializedName("kids")
    private List<Long> kids;
    @SerializedName("score")
    private int score;
    @SerializedName("text")
    private String text;
    @SerializedName("time")
    private long time;
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

    public int getDescendants() {
        return descendants;
    }

    public long getId() {
        return id;
    }

    public List<Long> getKids() {
        return kids;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        if (TextUtils.isEmpty(text))
            return "";
        else
            return text;
    }

    public long getTime() {
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

    public String getFormattedDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000);
        String date = DateUtility.getFormattedDateAndTime(calendar, DateUtility.FORMAT_RELATIVE);
        return date;
    }

    public String getCommentCount(Context context){
        String commentCount;
        if (descendants > 0) {
            commentCount = context.getResources().getQuantityString(R.plurals.comment_count, descendants, descendants);
        } else {
            commentCount = context.getString(R.string.discuss);
        }
        return commentCount;
    }

    // endregion

    // region Setters

    public void setBy(String by) {
        this.by = by;
    }

    public void setDescendants(int descendants) {
        this.descendants = descendants;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKids(List<Long> kids) {
        this.kids = kids;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(long time) {
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