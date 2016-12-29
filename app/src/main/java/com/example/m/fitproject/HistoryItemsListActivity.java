package com.example.m.fitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.m.fitproject.adapters.HistoryItemAdapter;
import com.example.m.fitproject.models.User;
import com.example.m.fitproject.models.UserFitHistory;
import com.example.m.fitproject.session.SessionManager;


import java.util.HashMap;
import java.util.List;

public class HistoryItemsListActivity extends AppCompatActivity {
    ListView listView;
    SessionManager sessionManager;
    User actualUser;
    List<UserFitHistory> userFitHistory;

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
        userFitHistory = actualUser.getUserFitHistory();

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(getApplicationContext(),userFitHistory);
        listView.setAdapter(historyItemAdapter);

        Log.d("ONCREATE","tak");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserFitHistory selectedHistory = userFitHistory.get(position);

                Intent detailIntent = new Intent(getApplicationContext(),HistoryDetailsActivity.class);
                detailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(detailIntent);

            }
        });



    }
}
