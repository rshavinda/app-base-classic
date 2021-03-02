package amazonite.android.appbaseprime.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.HashMap;

import amazonite.android.appbaseprime.R;
import amazonite.android.appbaseprime.dialog.ProgressDialog;


public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    private HashMap<Integer, Fragment> mFragmentMap = new HashMap<>();
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFragmentMap.remove(mFragmentMap.size()-1);
    }


    /**
     * Change the Status bar color value
     * @param res = Color resource value
     */
    public void setStatusBarColor(int res){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            if(window == null){
                Log.e(TAG, "setStatusBarColor: Error window found");
                return;
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(res);
        }
    }

    /**
     * Start activity from class name
     *
     * @param activityClass class name
     */
    public void activityToActivity(Class activityClass) {
        Intent intent = new Intent(getApplication(), activityClass);
        startActivity(intent);
    }

    public void activityToActivity(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    public void showWaiting() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.style1_progress_bar);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissWaiting() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * @param containerId       View id
     * @param fragment          Fragment that need to commit
     * @param isBackStackEnable Back stack enable or not
     */
    protected void startFragment(int containerId, Fragment fragment, boolean isBackStackEnable) {
        Log.d(TAG, "startFragment: " + fragment.getId());

        mFragmentMap.put(mFragmentMap.size(), fragment);

//        if(getSupportFragmentManager().executePendingTransactions()){
//            Log.d(TAG, "Complete pending transactions.");
//        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isBackStackEnable) transaction.addToBackStack(null);

        transaction.replace(containerId, fragment);
        transaction.commit();
    }

    /**
     * @param containerId       View id
     * @param fragment          Fragment that need to commit
     * @param isBackStackEnable Back stack enable or not
     * @param enter fragment animation pop in
     * @param exit fragment animation out
     */
    protected void startFragment(int containerId, Fragment fragment, boolean isBackStackEnable, int enter, int exit) {
        Log.d(TAG, "startFragment: " + fragment.getId());

        mFragmentMap.put(mFragmentMap.size(), fragment);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enter, exit);
        if(isBackStackEnable) transaction.addToBackStack(null);

        transaction.replace(containerId, fragment);
        transaction.commit();
    }

    public Fragment getFragment() {
        return mFragmentMap.get(mFragmentMap.size()-1);
    }

    public Fragment getFragmentByIndex(int index) {
        return mFragmentMap.get(index);
    }

    public Fragment getFragmentByTag(String tag) {
        return mFragmentMap.get(0);
    }

    /**
     * Show general massages in toast text
     *
     * @param message Toast text message
     */
    public void showMessage(String message) {
        if (message == null || message.isEmpty()) return;

        Toast.makeText(
                this,
                message, Toast.LENGTH_SHORT
        ).show();
    }
}
