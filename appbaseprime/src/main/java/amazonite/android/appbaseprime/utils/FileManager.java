package amazonite.android.appbaseprime.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FileManager {

    private static final String _IMAGE_PATH = "/.photo/";
    private static final String _CROP_IMAGE_PATH = "/.photo/cropped/";

    /**
     * Convert the file path to Uri
     *
     * @param folder   folder path or package name
     * @param fileName File path
     * @return
     */
    public static Uri getImagePath(String folder, String fileName) {
        return Uri.fromFile(new File(getFolderPath(folder, _IMAGE_PATH), fileName));
    }

    /**
     * Convert the cropped file path to Uri
     *
     * @param folder   Context of the file
     * @param fileName Cropped File path
     * @return
     */
    public static Uri getCropImagePath(String folder, String fileName) {
        return Uri.fromFile(new File(getFolderPath(folder, _CROP_IMAGE_PATH), fileName));
    }

    /**
     * Create folders according to packagename
     *
     * @param packageName of the app
     * @param name
     * @return
     */
    private static File getFolderPath(String packageName, String name) {
        File file = new File(Environment.getExternalStorageDirectory(),
                packageName == null ? "Pictures" : packageName + name);

        if (file.isDirectory())
            file.mkdirs();

        return file;
    }

    public static String readJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Compressor provideCompressor(Context context, File path) {
        if (path == null)
            path = getFolderPath(null, _IMAGE_PATH);

        Compressor compressor = new Compressor(context);
        compressor.setQuality(75);
        compressor.setDestinationDirectoryPath(path.toString());

        return compressor;
    }

    public static File compressImage(Context context, File path) {
        try {
            return provideCompressor(context, null).compressToFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert list of images to multipart file list
     *
     * @param key      specific key that need to define from server side
     * @param filePath List of image path list
     * @return Multipart file list
     */
    public List<MultipartBody.Part> convertImagePathsYoMultipart(String key, List<String> filePath) {

        List<MultipartBody.Part> files = new ArrayList<>();
        for (String path : filePath) {
            if (path != null) {
                File file = new File(path);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData(key, file.getName(), requestFile);
                files.add(body);
            }
        }
        return files;
    }

}
