package com.example.m.fitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HistoryDetailsActivity extends AppCompatActivity {
    private TextView dateText,wageText,BMIText,weightDifferenceText,BMIDifferenceText;
    private ImageView historyImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);
        initializeField();

        dateText.setText(dateText.getText() + " " + this.getIntent().getExtras().getString("date"));
        wageText.setText(wageText.getText() + " " + this.getIntent().getExtras().getString("actualWeight"));
        String startWeight = this.getIntent().getExtras().getString("startWeight");
        String startHeight = this.getIntent().getExtras().getString("startHeight");
        String actualWeight = this.getIntent().getExtras().getString("actualWeight");
        Double startWeightDouble = Double.parseDouble(startWeight);
        Double startHeightDouble = Double.parseDouble(startHeight) / 100.0;
        Double actualWeightDouble = Double.parseDouble(actualWeight);

        double startBMI = doublePrecision(startWeightDouble / Math.pow(startHeightDouble, 2));
        double actualBMI = doublePrecision(actualWeightDouble / Math.pow(startHeightDouble,2));

        BMIText.setText(BMIText.getText() + " " + actualBMI);

        Double weightDifference = startWeightDouble - actualWeightDouble;
        Double bmiDifference = doublePrecision(startBMI - actualBMI);


        weightDifferenceText.setText(weightDifferenceText.getText() + " " + weightDifference.toString());
        BMIDifferenceText.setText(BMIDifferenceText.getText()+ " " + bmiDifference.toString());







    }

    private void initializeField(){
        dateText = (TextView) findViewById(R.id.dateText);
        wageText = (TextView) findViewById(R.id.wageText);
        BMIText = (TextView) findViewById(R.id.BMIText);
        weightDifferenceText = (TextView) findViewById(R.id.weightDifferenceText);
        BMIDifferenceText = (TextView) findViewById(R.id.BMIDifferenceText);

    }

    private double doublePrecision(double d) {
        Double truncatedDouble = BigDecimal.valueOf(d)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return truncatedDouble;
    }
}
