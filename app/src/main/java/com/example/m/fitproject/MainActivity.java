package com.example.m.fitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.m.fitproject.session.SessionManager;

import java.util.HashMap;

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
    }
}
