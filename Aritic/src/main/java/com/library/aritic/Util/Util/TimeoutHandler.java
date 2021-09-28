package com.library.aritic.Util.Util;
import android.os.HandlerThread;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.NonNull;
import com.library.aritic.AriticLogger;

public class TimeoutHandler extends HandlerThread {

    private static final String TAG = TimeoutHandler.class.getCanonicalName();
    private static final Object SYNC_LOCK = new Object();

    private static TimeoutHandler timeoutHandler;

    static TimeoutHandler getTimeoutHandler() {
        if (timeoutHandler == null) {
            synchronized (SYNC_LOCK) {
                if (timeoutHandler == null)
                    timeoutHandler = new TimeoutHandler();
            }
        }
        return timeoutHandler;
    }

    private final Handler mHandler;

    private TimeoutHandler() {
        super(TAG);
        start();
        this.mHandler = new Handler(getLooper());
    }

    void startTimeout(long timeout, @NonNull Runnable runnable) {
        synchronized (SYNC_LOCK) {
            // Avoid duplicate runnable postDelayed
            destroyTimeout(runnable);
            AriticLogger.Log("Running startTimeout with timeout: " + timeout + " and runnable: " + runnable.toString());
            mHandler.postDelayed(runnable, timeout);
        }
    }

    void destroyTimeout(Runnable runnable) {
        synchronized (SYNC_LOCK) {
            AriticLogger.Log("Running destroyTimeout with runnable: " + runnable.toString());
            mHandler.removeCallbacks(runnable);
        }
    }
}
