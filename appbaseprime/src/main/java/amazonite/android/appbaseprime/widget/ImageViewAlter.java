package amazonite.android.appbaseprime.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import amazonite.android.appbaseprime.R;
import amazonite.android.appbaseprime.utils.CircleTransform;
import amazonite.android.appbaseprime.utils.Helper;


public class ImageViewAlter extends AppCompatImageView {
    public ImageViewAlter(Context context) {
        super(context);
    }

    public ImageViewAlter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewAlter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Load image from piccaso lib
     *
     * @param path url or file path of the image
     */
    public void loadImage(String path) {

        Helper.loadImage(this, path, R.drawable.ic_placeholder, R.drawable.ic_placeholder);
    }

    /**
     * Load image from piccaso lib
     *
     * @param path url or file path of the image
     * @param placeholder Image place holder for empty images
     * @param errorPlaceholder If image failed to load
     */
    public void loadImage(String path, int placeholder, int errorPlaceholder) {
        Helper.loadImage(this, path, placeholder, errorPlaceholder);
    }

    /**
     * Load image from piccaso lib
     *
     * @param path url or file path of the image
     */
    public void loadImage(String circleTransform, String path) {
        Helper.loadImage(this, path, R.drawable.ic_placeholder, R.drawable.ic_placeholder, new CircleTransform(
                200, 0
        ));
    }

    /**
     * Load image from piccaso lib
     *
     * @param path url or file path of the image
     * @param scaleType selected scaling option for the image
     */
    public void loadAndScaleImage(String path, ImageView.ScaleType scaleType) {

        Helper.loadImage(this, path, scaleType);
    }

    /**
     * Load image from url/ file path Without displaying placeholder and scaling image
     *
     * @param path url or file path of the image
     */
    public void loadPlainImage(String path) {

        Helper.loadPlainImage(this, path);
    }
}
