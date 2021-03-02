package amazonite.android.appbaseprime.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import amazonite.android.appbaseprime.R;
import amazonite.android.appbaseprime.utils.AppTimeout;


public class ProgressDialog extends Dialog implements AppTimeout.OnTimeoutListener {

    private AppTimeout mTimeout;

    public ProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        mTimeout = new AppTimeout(this);
        mTimeout.setTimeout(60 * 1000L);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_progress);
        setCancelable(true);
    }

    @Override
    public void show() {
        super.show();

        mTimeout.start();
    }

    @Override
    public void OnTimeout() {
        if (isShowing()) {
            this.dismiss();
        }
    }
}
