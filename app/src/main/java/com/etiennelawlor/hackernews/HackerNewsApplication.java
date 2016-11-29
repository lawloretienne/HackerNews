package com.etiennelawlor.hackernews;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.RefWatcher;

import java.io.File;

import timber.log.Timber;

/**
 * Created by etiennelawlor on 3/21/15.
 */
public class HackerNewsApplication extends Application {

    // region Static Variables
    private static HackerNewsApplication currentApplication = null;
    // endregion

    // region Member Variables
    private RefWatcher refWatcher;
    // endregion

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        currentApplication = this;
    }

    // region Helper Methods
    public static HackerNewsApplication getInstance() {
        return currentApplication;
    }

    public static File getCacheDirectory()  {
        return currentApplication.getCacheDir();
    }

    public static RefWatcher getRefWatcher(Context context) {
        HackerNewsApplication application = (HackerNewsApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    // endregion

    // region Inner Classes

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

//            FakeCrashLibrary.log(priority, tag, message);
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
//                }
//            }
        }
    }

    // endregion
}
