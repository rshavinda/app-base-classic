package amazonite.android.appbaseprime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import amazonite.android.appbaseprime.R;
import amazonite.android.appbaseprime.utils.DateTimeUtil;
import amazonite.android.appbaseprime.utils.Helper;


public class EditTextAlter extends AppCompatEditText {

    private static final String TAG = EditTextAlter.class.getSimpleName();
    private TextInputLayout mTextInputLayout;

    public EditTextAlter(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(attrs);
        setEmoji(context, attrs);
    }

    public EditTextAlter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(attrs);
        setEmoji(context, attrs);
    }

    private void setCustomFont(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFont);
        String customFont = a.getString(R.styleable.CustomFont_fontType);
        setCustomFont(customFont);
        a.recycle();
    }

    private void setEmoji(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.EnableEmoji);
        boolean isEmoji = a.getBoolean(R.styleable.EnableEmoji_emojiEnabled, true);

        if (!isEmoji) {
            disableEmoji();
            a.recycle();
        }
    }

    /**
     * Set text null no exception
     *
     * @param text Set text null no exception
     */
    public void setAnyText(String text) {
        setText(Helper.checkIfNull(text));
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

    @Override
    public void setError(CharSequence error) {
        setPasswordToggleIconEnable(error == null);
        super.setError(error);
    }


    /**
     * @return Get text string value return empty text if null
     */
    public String getTextString() {
        return getText() == null ? "" : getText().toString();
    }

    /**
     * @return get the int value of the custom edit text
     */
    public int getTextInt() {
        return Helper.getInt(getTextString());
    }

    /**
     * @return get the double value of the custom edit text
     */
    public double getTextDouble() {
        try {
            return getText() == null || getText().toString().isEmpty() ?
                    0 : Double.parseDouble(getText().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Set date and time '''DateTimeUtil.DEFAULT_DATE_FORMAT''' format
     * @param unix  Unix timestamp format
     * @param format Parse DateTimeUtil.DEFAULT_DATE_FORMAT, DateTimeUtil.DATE_FORMAT_1 ...
     */
    public void setDateTime(long unix, String format) {
        setText(DateTimeUtil.formatUnixTimestamp(unix, format));
    }

    /**
     * Set the text as a currency in edit text
     *
     * @param type  Currency type
     * @param value Currency value
     */
    public void setCurrency(String type, double value) {
        String currency = type == null ? "LKR" : type + " " + Helper.toCurrencyFormat(value);
        setText(currency);
    }

    /**
     * @return the trim text which removed extra space
     */
    public String getTrimText() {
        return getText() == null ? "" : getText().toString().trim();
    }

    /**
     * Set editable false if you want to deactivate the edittext
     *
     * @param status of the edit text editability
     */
    public void setEditability(boolean status) {
        setEnabled(status);
        setFocusable(status);
        setFocusableInTouchMode(status);
        setClickable(status);
        setCursorVisible(status);
    }


    /**
     * EditText bind as a password field
     * @param view
     */
    public void bindPasswordView(View view){
        mTextInputLayout = (TextInputLayout) view.findViewById(getId()).getParent().getParent();
        addTextChangedListener(mPasswordChange);
    }

    /**
     * set TextInputLayout's password visibility toggle enable/disable
     * @param status ( true - enable | false - disable)
     */
    public void setPasswordToggleIconEnable(boolean status) {
        if (mTextInputLayout != null) {
            mTextInputLayout.setPasswordVisibilityToggleEnabled(status);
        }
    }

    /**
     * get TextInputLayout's password toggle visibility
     * @return boolean - (Icon visible - true | invisible - false)
     */
    public boolean isPasswordToggleIconVisible() {
        if (mTextInputLayout != null) {
            return mTextInputLayout.isPasswordVisibilityToggleEnabled();
        }
        return false;
    }

    public void removeErrorMessage() {
        setError(null);
    }

    private TextWatcher mPasswordChange = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged: " + s.toString());
            if ((mTextInputLayout != null) && (!isPasswordToggleIconVisible())) {
                setPasswordToggleIconEnable(true);
                removeErrorMessage();
            }
        }
    };

    private void disableEmoji() {
        if (getFilters() != null && getFilters().length != 0 && getFilters()[0] != null) {
            InputFilter[] inputFilters = new InputFilter[getFilters().length + 1];
            for (int i = 0; i < getFilters().length; i++) {
                inputFilters[i] = getFilters()[i];
            }
            inputFilters[getFilters().length] = new EmojiExcludeFilter();

            setFilters(inputFilters);
        } else {
            setFilters(new InputFilter[]{new EmojiExcludeFilter()});
        }
    }


    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }
}
