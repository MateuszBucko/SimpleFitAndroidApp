package com.example.m.fitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.SessionManager;


import java.util.HashMap;
import java.util.List;

public class HistoryItemsListActivity extends AppCompatActivity {
    ListView listView;
    SessionManager sessionManager;
    User actualUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item_list);
        //inicjalizacja listView
        listView = (ListView) findViewById(R.id.listView);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetails();



        actualUser = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));

        //lista z historią użytkownika
        List<UserFitHistory> userFitHistory = actualUser.getUserFitHistory();




    }
}
