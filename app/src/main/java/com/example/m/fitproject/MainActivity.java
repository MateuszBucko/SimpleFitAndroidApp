package com.example.m.fitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.m.fitproject.adapters.HistoryItemAdapter;
import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.SessionManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView helloText, startWeightText, startBMIText, actualWeightText, actualBMIText, differenceWeightText, differenceBMIText;
    private User actualUser;
    String helloTextBase,startWeightTextBase,startBMITextBase,actualWeightTextBase,actualBMITextBase,differenceWeightTextBase,differenceBMITextBase;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeFileds();
        updateFields();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFields();

    }



    private void updateFields() {
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        actualUser = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));

        helloText.setText(helloTextBase + "  " + actualUser.getUsername() + " !");

        startWeightText.setText(startWeightTextBase + " " + actualUser.getStartWeight());

        double mHeight = actualUser.getHeight() / 100.0;

        double startBmi = actualUser.getStartWeight() / Math.pow(mHeight, 2);
        startBmi = doublePrecision(startBmi);
        startBMIText.setText(startBMITextBase + " " + startBmi);

        UserFitHistory lastestHistory = actualUser.getLastestRecord();
        Integer differenceWeight = 0;
        if (lastestHistory != null) {
            actualWeightText.setText(actualWeightTextBase + " " + lastestHistory.getWeight());
            differenceWeight = actualUser.getStartWeight() - lastestHistory.getWeight();
        } else {
            actualWeightText.setText(actualWeightTextBase + " " + actualUser.getStartWeight());
            differenceWeight = 0;
        }
        double actualBMI = 0;
        if (lastestHistory != null) {
            actualBMI = lastestHistory.getWeight() / Math.pow(mHeight, 2);
            actualBMI = doublePrecision(actualBMI);
            actualBMIText.setText(actualBMITextBase + " " + doublePrecision(actualBMI));
        } else {
            actualBMI = startBmi;
            actualBMIText.setText(actualBMITextBase + " " + doublePrecision(startBmi));
        }

        differenceWeightText.setText(differenceWeightTextBase.toString() + "  " + differenceWeight.toString());
        Double bmiDifference = doublePrecision(startBmi - actualBMI);

        differenceBMIText.setText(differenceBMITextBase.toString() + "  " + bmiDifference.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                sessionManager.logoutUser();
                finish();
                break;
            case R.id.list:
                Intent listIntent = new Intent(this, HistoryItemsListActivity.class);
                startActivity(listIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeFileds() {
        helloText = (TextView) findViewById(R.id.helloText);
        startWeightText = (TextView) findViewById(R.id.startWeightText);
        startBMIText = (TextView) findViewById(R.id.startBMIText);
        actualWeightText = (TextView) findViewById(R.id.actualWeightText);
        actualBMIText = (TextView) findViewById(R.id.actualBMIText);
        differenceWeightText = (TextView) findViewById(R.id.differenceWeightText);
        differenceBMIText = (TextView) findViewById(R.id.differenceBMIText);

        helloTextBase = helloText.getText().toString();
        startWeightTextBase = startWeightText.getText().toString();
        startBMITextBase = startBMIText.getText().toString();
        actualWeightTextBase = actualWeightText.getText().toString();
        actualBMITextBase = actualBMIText.getText().toString();
        differenceWeightTextBase = differenceWeightText.getText().toString();
        differenceBMITextBase = differenceBMIText.getText().toString();
    }

    private double doublePrecision(double d) {
        Double truncatedDouble = BigDecimal.valueOf(d)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return truncatedDouble;
    }
}
