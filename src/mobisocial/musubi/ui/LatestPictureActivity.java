package mobisocial.musubi.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;

/**
 * Shares the latest image from a given bucket from the Android gallery,
 * defaulting to the Camera.
 */
public class LatestPictureActivity extends Activity {
    public static final String EXTRA_BUCKET = "bucket";

    public static final String BUCKET_CAMERA = "Camera";
    public static final String BUCKET_SCREENSHOTS = "Screenshots";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent result = new Intent();
        String bucket;
        if (getIntent().hasExtra(EXTRA_BUCKET)) {
            bucket = getIntent().getStringExtra(EXTRA_BUCKET);
        } else {
            bucket = BUCKET_CAMERA;
        }
        result.setData(getLastPhoto(bucket));
        setResult(RESULT_OK, result);
        finish();
    }

    private Uri getLastPhoto(String bucket) {
        String selection = ImageColumns.BUCKET_DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[] { bucket };
        String sort = ImageColumns.DATE_TAKEN + " desc";
        Cursor c =
            android.provider.MediaStore.Images.Media.query(getContentResolver(),
                    Images.Media.EXTERNAL_CONTENT_URI, 
                    new String[] { ImageColumns._ID }, selection, selectionArgs, sort );

        int idx = c.getColumnIndex(ImageColumns._ID);
        if (c.moveToFirst()) {
            return Uri.withAppendedPath(Images.Media.EXTERNAL_CONTENT_URI, c.getString(idx));
        }
        return null;
    }
}
