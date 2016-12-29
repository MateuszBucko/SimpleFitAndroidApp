package com.example.m.fitproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.m.fitproject.models.User;
import com.example.m.fitproject.session.AlertDialogManager;
import com.example.m.fitproject.session.SessionManager;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    private EditText oldPasswordText, newPasswordText, repeatNewPasswordText;
    private TextView passwordChangedText;
    private Button changeButton;
    private SessionManager sessionManager;
    private User actualUser;
    private AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> userDetails = sessionManager.getUserDetails();

        initializeFields();

        actualUser = User.getUser(Long.parseLong(userDetails.get(SessionManager.KEY_ID)));

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oldPasswordText.getText().toString().equals(actualUser.getPassword().toString())) {
                    if(newPasswordText.getText().toString().equals(repeatNewPasswordText.getText().toString()))
                    {
                        actualUser.setPassword(newPasswordText.getText().toString());
                        actualUser.save();
                        passwordChangedText.setVisibility(View.VISIBLE);
                        oldPasswordText.setText("");
                        newPasswordText.setText("");
                        repeatNewPasswordText.setText("");
                        oldPasswordText.setInputType(InputType.TYPE_NULL);
                        newPasswordText.setInputType(InputType.TYPE_NULL);
                        repeatNewPasswordText.setInputType(InputType.TYPE_NULL);
                    }
                    else
                    {
                        alert.showAlertDialog(SettingsActivity.this, "Password change failed !", "Repeat new password doesn't match !");
                    }
                } else {
                    alert.showAlertDialog(SettingsActivity.this, "Password change failed !", "Old password doesn't match !");

                }
            }
        });




    }

    private void initializeFields() {
        oldPasswordText = (EditText) findViewById(R.id.oldPasswordText);
        newPasswordText = (EditText) findViewById(R.id.newPasswordText);
        repeatNewPasswordText = (EditText) findViewById(R.id.repeatNewPasswordText);
        changeButton = (Button) findViewById(R.id.changeButton);
        passwordChangedText = (TextView) findViewById(R.id.passwordChangedText);
    }
}
