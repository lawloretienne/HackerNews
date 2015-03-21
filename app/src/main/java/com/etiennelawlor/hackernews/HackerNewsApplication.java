package com.etiennelawlor.hackernews;

import android.app.Application;

import java.io.File;

import timber.log.Timber;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class HackerNewsApplication extends Application {

    // region Member Variables
    private static HackerNewsApplication sCurrentApplication = null;
    // endregion

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        sCurrentApplication = this;

    }

    public static HackerNewsApplication get() {
        return sCurrentApplication;
    }


    public static File getCacheDirectory()  {
        return sCurrentApplication.getCacheDir();
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {
//            Crashlytics.log(String.format(message, args));
        }

        @Override public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override public void e(Throwable t, String message, Object... args) {
            e(message, args);
//            Crashlytics.logException(t);
        }
    }
}
