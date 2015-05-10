package com.etiennelawlor.hackernews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateUtils;

import java.util.Calendar;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class HackerNewsUtility {

    public static void openWebPage(Context context, String url) {
        if(!TextUtils.isEmpty(url)){
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }

    public static String getRelativeDate(Calendar future){

        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(future.getTimeInMillis(), System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_NO_NOON);

        String relativeDate;
        if(relativeTime.toString().equals("0 minutes ago") || relativeTime.toString().equals("in 0 minutes")){
            relativeDate = "Just now";
        } else {
            relativeDate = relativeTime.toString();
        }

        return relativeDate;
    }
}
