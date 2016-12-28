package com.example.m.fitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.SessionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());

        HashMap<String,String> userDetails = sessionManager.getUserDetails();

        Log.d("USERNAME", userDetails.get(SessionManager.KEY_USERNAME));
        Log.d("ID", userDetails.get(SessionManager.KEY_ID));

        User u = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));
        Log.d("USERNAME",u.getPassword());


    }
}
