package amazonite.android.appbaseprime.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private BaseActivity activity;
    private View mRootView;

    @LayoutRes
    protected abstract int layoutRes();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutRes(), container, false);
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public View getRootView() {
        return mRootView;
    }

    public void showMessage(String message) {
        activity.showMessage(message);
    }

    public void showWaiting() {
        activity.showWaiting();
    }

    public void dismissWaiting() {
        activity.dismissWaiting();
    }
}