package com.neoress.typics;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";

    final int REQUEST_PERMISSION = 0;

    //take photo by camera or choose from gallery
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_FROM    = 2;

    public static Uri mLastPhotoUri;
    public static File file;
    public static File tempFile;

    public static String imageAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setREQUEST_PERMISSION();

        initialFrontEnd();
    }

    @SuppressLint("InlinedApi")
    public void setREQUEST_PERMISSION()
    {
        int checkRequest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (checkRequest != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS}, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_FROM && resultCode == RESULT_OK)
        {
            mLastPhotoUri = data.getData();
            //getAddressImg();
            finish();
            Intent intentEditPic = new Intent(getApplicationContext(), EditPicActivity.class);
            startActivity(intentEditPic);
        }
        else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //getAddressImg();
            finish();
            Intent intentEditPic = new Intent(getApplicationContext(), EditPicActivity.class);
            startActivity(intentEditPic);
        }

    }

    public void getAddressImg()
    {
        try {
            //ExifInterface exif = new ExifInterface(getRealPathFromURI(getApplicationContext(), mLastPhotoUri));
            //StringBuilder builder = new StringBuilder();

            float []loc = new float[2];

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(loc[0], loc[1], 5);
            String cityName = addresses.get(0).getAdminArea();
            String countryName = addresses.get(0).getCountryName();

            imageAddress = cityName + " - " + countryName;

            //get all info of image
            //

            //builder.append("Date & Time: " + getExifTag(exif, ExifInterface.TAG_DATETIME) + "\n\n");

            //builder.append("Flash: " + getExifTag(exif, ExifInterface.TAG_FLASH) + "\n");

            //builder.append("Focal Length: " + getExifTag(exif, ExifInterface.TAG_FOCAL_LENGTH) + "\n\n");

            //builder.append("GPS Datestamp: " + getExifTag(exif, ExifInterface.TAG_FLASH) + "\n");

            //builder.append("GPS Latitude: " + getExifTag(exif, ExifInterface.TAG_GPS_LATITUDE) + "\n");

            //builder.append("GPS Latitude Ref: " + getExifTag(exif, ExifInterface.TAG_GPS_LATITUDE_REF) + "\n");

            //builder.append("GPS Longitude: " + getExifTag(exif, ExifInterface.TAG_GPS_LONGITUDE) + "\n");

            //builder.append("GPS Longitude Ref: " + getExifTag(exif, ExifInterface.TAG_GPS_LONGITUDE_REF) + "\n");

            //builder.append("GPS Processing Method: " + getExifTag(exif, ExifInterface.TAG_GPS_PROCESSING_METHOD) + "\n");

            //builder.append("GPS Timestamp: " + getExifTag(exif, ExifInterface.TAG_GPS_TIMESTAMP) + "\n\n");

            //builder.append("Image Length: " + getExifTag(exif, ExifInterface.TAG_IMAGE_LENGTH) + "\n");

            //builder.append("Image Width: " + getExifTag(exif, ExifInterface.TAG_IMAGE_WIDTH) + "\n\n");

            //builder.append("Camera Make: " + getExifTag(exif, ExifInterface.TAG_MAKE) + "\n");

            //builder.append("Camera Model: " + getExifTag(exif, ExifInterface.TAG_MODEL) + "\n");

            //builder.append("Camera Orientation: " + getExifTag(exif, ExifInterface.TAG_ORIENTATION) + "\n");

            //builder.append("Camera White Balance: " + getExifTag(exif, ExifInterface.TAG_WHITE_BALANCE) + "\n");

            //

            //builder = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //

    //private String getExifTag(ExifInterface exif, String tag)

    //{

    //String attribute = exif.getAttribute(tag);

    //return (null != attribute ? attribute : "");

    //}

    //

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getRealPathFromURI(final Context context, final Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void initialFrontEnd() {
        tempFile = new File(getExternalCacheDir().getAbsolutePath());

        createBaseFolder();
        //receiving data from another app
        getDataFromAnotherApp();
    }

    public void takePhoto(View v)
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(cameraIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Log.i(TAG, "IOException");
            }

            if(photoFile != null) {
                file = photoFile;
                mLastPhotoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mLastPhotoUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void chooseFrom(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_IMAGE_FROM);
    }

    // Utils

    protected void createBaseFolder(){

        String currDir= Environment.getExternalStorageDirectory().toString();
        File folder = new File (currDir,"TyPics");
        if (!folder.exists())
            folder.mkdir();
        if (folder==null) {
            currDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            folder = new File(currDir, "TyPics");
            if (!folder.exists())
                folder.mkdir();
        }
    }
    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void getDataFromAnotherApp()
    {
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null)
        {
            if (type.startsWith("image/")) {
                mLastPhotoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                Log.i("getData", mLastPhotoUri.getPath());
                if (mLastPhotoUri != null) {
                    Intent intentEditPic = new Intent(getApplicationContext(), EditPicActivity.class);
                    startActivity(intentEditPic);
                }
            }
        }
    }
}
