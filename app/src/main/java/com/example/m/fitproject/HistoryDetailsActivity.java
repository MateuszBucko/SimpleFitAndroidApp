package com.example.m.fitproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m.fitproject.session.AlertDialogManager;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.activeandroid.Cache.getContext;

public class HistoryDetailsActivity extends AppCompatActivity {
    private TextView dateText,wageText,BMIText,weightDifferenceText,BMIDifferenceText;
    private ImageView historyImage;
    private AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        initializeField();
        historyImage = (ImageView) findViewById(R.id.historyImage);
        dateText.setText(dateText.getText() + " " + this.getIntent().getExtras().getString("date"));
        wageText.setText(wageText.getText() + " " + this.getIntent().getExtras().getString("actualWeight"));
        String startWeight = this.getIntent().getExtras().getString("startWeight");
        String startHeight = this.getIntent().getExtras().getString("startHeight");
        String actualWeight = this.getIntent().getExtras().getString("actualWeight");
        final String photo = this.getIntent().getExtras().getString("photo");
        Double startWeightDouble = Double.parseDouble(startWeight);
        Double startHeightDouble = Double.parseDouble(startHeight) / 100.0;
        Double actualWeightDouble = Double.parseDouble(actualWeight);

        if(photo != null){
            setPic(photo);
            historyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageInGallery(photo);
                }
            });
        }

        double startBMI = doublePrecision(startWeightDouble / Math.pow(startHeightDouble, 2));
        double actualBMI = doublePrecision(actualWeightDouble / Math.pow(startHeightDouble,2));

        BMIText.setText(BMIText.getText() + " " + actualBMI);

        Double weightDifference = startWeightDouble - actualWeightDouble;
        Double bmiDifference = doublePrecision(startBMI - actualBMI);


        weightDifferenceText.setText(weightDifferenceText.getText() + " " + weightDifference.toString());
        BMIDifferenceText.setText(BMIDifferenceText.getText()+ " " + bmiDifference.toString());





    }

    private void setPic(String mCurrentPhotoPath) {
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, null);
        historyImage.setImageBitmap(bitmap);
    }

    private void initializeField(){
        dateText = (TextView) findViewById(R.id.dateText);
        wageText = (TextView) findViewById(R.id.wageText);
        BMIText = (TextView) findViewById(R.id.BMIText);
        weightDifferenceText = (TextView) findViewById(R.id.weightDifferenceText);
        BMIDifferenceText = (TextView) findViewById(R.id.BMIDifferenceText);

    }

    private void openImageInGallery(String mCurrentPhotoPath) {

        File pFile = new File(mCurrentPhotoPath);

        if (mCurrentPhotoPath != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = pFile.getName().substring(pFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(getContext(), "com.example.m.fitproject", pFile);
                    intent.setDataAndType(contentUri, type);
                } else {
                    intent.setDataAndType(Uri.fromFile(pFile), type);
                }
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                Toast.makeText(getContext(), "No activity found to open this attachment.", Toast.LENGTH_LONG).show();
            }
        }


    }

    private double doublePrecision(double d) {
        Double truncatedDouble = BigDecimal.valueOf(d)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return truncatedDouble;
    }
}
