package amazonite.android.appbaseprime.utils;

import android.os.Handler;
import android.util.Log;

public class AppTimeout {

    private static final String TAG = AppTimeout.class.getSimpleName();
    private OnTimeoutListener mListener;
    private boolean isCancelTimer = false;

    private long timeout = 1000 * 60;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isCancelTimer) {
                Log.d(TAG, "Function timeout");
                if (mListener != null)
                    mListener.OnTimeout();
            } else {
                Log.d(TAG, "Rabbit cancelled the timeout");
            }
        }
    };

    public AppTimeout(OnTimeoutListener mListener) {
        this.mListener = mListener;
    }

    private void timeout(long timeout) {

        mHandler.removeCallbacks(mRunnable);
        mHandler.postDelayed(mRunnable, timeout);
    }

    public void start(long timeout) {
        Log.d(TAG, "start");

        this.timeout = timeout;
        isCancelTimer = false;
        timeout(timeout);
    }

    public void start() {
        isCancelTimer = false;
        timeout(timeout);
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void stop() {
        Log.d(TAG, "stop");
        isCancelTimer = true;
    }

    public interface OnTimeoutListener {
        void OnTimeout();
    }
}
