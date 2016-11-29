package com.etiennelawlor.hackernews.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.etiennelawlor.hackernews.R;
import com.etiennelawlor.hackernews.customtabs.CustomTabActivityHelper;

import java.util.Calendar;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class HackerNewsUtility {

//    public static void openWebPage(Context context, String url) {
//        if (!TextUtils.isEmpty(url)) {
//            Uri webpage = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//            if (intent.resolveActivity(context.getPackageManager()) != null) {
//                context.startActivity(intent);
//            }
//        }
//    }

    public static String getRelativeDate(Calendar future) {

        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(future.getTimeInMillis(), System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS,
                DateUtils.FORMAT_NO_NOON);

        String relativeDate;
        if (relativeTime.toString().equals("0 minutes ago") || relativeTime.toString().equals("in 0 minutes")) {
            relativeDate = "Just now";
        } else {
            relativeDate = relativeTime.toString();
        }

        return relativeDate;
    }

    public static void openCustomTab(Activity activity, String url) {
        final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(activity, R.color.primary));
        builder.setStartAnimations(activity, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(activity, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        builder.setCloseButtonIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_arrow_left));
        builder.setShowTitle(true);

        final CustomTabsIntent customTabsIntent = builder.build();

        CustomTabActivityHelper.openCustomTab(activity, customTabsIntent, Uri.parse(url),
                new CustomTabActivityHelper.CustomTabFallback() {
                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
    }
}
