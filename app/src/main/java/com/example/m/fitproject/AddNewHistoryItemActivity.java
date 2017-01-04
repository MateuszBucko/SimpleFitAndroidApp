package com.example.m.fitproject;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.AlertDialogManager;
import com.example.m.fitproject.session.SessionManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddNewHistoryItemActivity extends AppCompatActivity {
    public static final String FILE_URI_KEY = "FILE_URI_KEY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private EditText actualWeight;
    private Button addPhoto, addHistory;
    private Uri imageFileUri;
    private ImageView imageView;
    String mCurrentPhotoPath = null;
    private SessionManager sessionManager;
    private User actualUser;
    private AlertDialogManager alert = new AlertDialogManager();
    private String uriScheme;


    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("FILE", "ERROR CREATE FILE");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.m.fitproject",
                        photoFile);
                imageFileUri = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
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
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void openImageInGallery() {

//        Uri uri =  imageFileUri;
//        Log.d("CURSOR", imageFileUri.toString());
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//        String mime = "*/*";
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        if (mimeTypeMap.hasExtension(
//                mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
//            mime = mimeTypeMap.getMimeTypeFromExtension(
//                    mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
//        intent.setDataAndType(uri,mime);
//        startActivity(intent);
        if(mCurrentPhotoPath != null) {
            PackageManager manager = getApplicationContext().getPackageManager();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + mCurrentPhotoPath), "image/*");
            List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
            if (infos.size() > 0) {
                startActivity(intent);
            } else {
                alert.showAlertDialog(AddNewHistoryItemActivity.this, "Opening failed", "No program available to open the image");
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                setPic();
                galleryAddPic();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCurrentPhotoPath != null) {
            setPic();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_history_item);

        actualWeight = (EditText) findViewById(R.id.actualWeight);
        addPhoto = (Button) findViewById(R.id.addPhoto);
        addHistory = (Button) findViewById(R.id.addHistory);
        imageView = (ImageView) findViewById(R.id.imageView);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        actualUser = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    getPermission();
                } else {
                    dispatchTakePictureIntent();
                }
            }
        });

        addHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actualWeight.getText().toString().length() != 0) {
                    if (isInteger(actualWeight.getText().toString())) {
                        UserFitHistory userFitHistory = new UserFitHistory();
                        userFitHistory.setWeight(Integer.parseInt(actualWeight.getText().toString()));
                        if (mCurrentPhotoPath != null) {
                            if (mCurrentPhotoPath.length() > 0) {
                                userFitHistory.setPhoto(mCurrentPhotoPath);
                            }
                        }
                        userFitHistory.setUser(actualUser);
                        userFitHistory.save();
                       finish();
                    } else {
                        alert.showAlertDialog(AddNewHistoryItemActivity.this, "Adding new history failed", "Wrong format of weight");
                    }


                } else {
                    alert.showAlertDialog(AddNewHistoryItemActivity.this, "Adding new history failed", "Weight cannot be empty!");
                }
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageInGallery();
            }
        });


    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void getPermission() {
        String[] permissions = {"android.permission.CAMERA"};
        ActivityCompat.requestPermissions(this,
                permissions,
                0
        );
    }



}
