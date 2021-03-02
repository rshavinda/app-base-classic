package amazonite.android.appbaseprime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.util.Log;

import amazonite.android.appbaseprime.R;


public class CheckboxAlter extends AppCompatCheckBox {

    private static final String TAG = CheckboxAlter.class.getSimpleName();

    public CheckboxAlter(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(attrs);
    }

    public CheckboxAlter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(attrs);
    }

    private void setCustomFont(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String customFont = a.getString(R.styleable.CustomFont_fontType);
        setCustomFont(customFont);
        a.recycle();
    }

    /**
     * Set custom font
     *
     * @param asset Assert path of the font
     * @return The font changed or not
     */
    public boolean setCustomFont(String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(getContext().getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }
}
