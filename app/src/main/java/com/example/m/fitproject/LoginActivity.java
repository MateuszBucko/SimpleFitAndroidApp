package com.example.m.fitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.example.m.fitproject.models.User;
import com.example.m.fitproject.session.AlertDialogManager;
import com.example.m.fitproject.session.SessionManager;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;

    SessionManager sessionManager;

    private AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (EditText) findViewById(R.id.usernameRegisterText);
        passwordText = (EditText) findViewById(R.id.passwordRegisterText);
        loginButton = (Button) findViewById(R.id.loginButton);
        sessionManager = new SessionManager(getApplicationContext());
        ActiveAndroid.initialize(this);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameText.getText() != null && passwordText.getText() != null && usernameText.getText().length() > 0 && passwordText.getText().length() > 0) {
                    User loginUser = User.getUser(usernameText.getText().toString(),passwordText.getText().toString());
                    if(loginUser != null)
                    {
                        sessionManager.createLoginSession(loginUser.getUsername(),loginUser.getId());
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username and Password doesn't exist in database", false);
                    }
                } else {
                    Log.d("ELSE","Poszlo");
                    alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username and Password cannot be empty", false);
                }
            }
        });



        List<User> users = User.getAllUsers();
        for (User tempuser : users) {
            Log.d("Username", tempuser.getUsername());

        }

    }
}
