package com.example.m.fitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.SessionManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView helloText, startWeightText, startBMIText, actualWeightText, actualBMIText, differenceWeightText, differenceBMIText;
    private User actualUser;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        initializeFileds();

        actualUser = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));



        helloText.setText(helloText.getText() + "  " + actualUser.getUsername() + " !");

        startWeightText.setText(startWeightText.getText() + " " + actualUser.getStartWeight());

        double mHeight = actualUser.getHeight()/100.0;

        double startBmi = actualUser.getStartWeight() / Math.pow(mHeight, 2);
        startBMIText.setText(startBMIText.getText() + " " + doublePrecision(startBmi));

        UserFitHistory lastestHistory = actualUser.getLastestRecord();
        if (lastestHistory != null) {
            actualWeightText.setText(actualWeightText.getText() + " " + lastestHistory.getWeight());
        } else {
            actualWeightText.setText(actualWeightText.getText() + " " + actualUser.getStartWeight());
        }

        if(lastestHistory != null){
            double actualBMI = lastestHistory.getWeight() / Math.pow(mHeight, 2);
            actualBMIText.setText(actualBMIText.getText() + " " + doublePrecision(actualBMI));
        }
        else
        {
            actualBMIText.setText(actualBMIText.getText() + " " + doublePrecision(startBmi));
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                sessionManager.logoutUser();
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
    }

    private double doublePrecision(double d)
    {
        Double truncatedDouble = BigDecimal.valueOf(d)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();

        return truncatedDouble;
    }
}
