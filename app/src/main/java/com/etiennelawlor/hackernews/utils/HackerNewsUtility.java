package com.etiennelawlor.hackernews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

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
    }}
