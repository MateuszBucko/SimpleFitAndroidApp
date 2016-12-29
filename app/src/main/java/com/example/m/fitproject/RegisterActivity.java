package com.example.m.fitproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.m.fitproject.models.User;
import com.example.m.fitproject.session.AlertDialogManager;

public class RegisterActivity extends AppCompatActivity {

    EditText accountUsername, accountPassword, accountPasswordRepeat, accountWeight, accountHeight;
    Button registerButton;
    private AlertDialogManager alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeFields();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accountUsername.getText().toString().length() > 0 && accountPassword.getText().toString().length() > 0 &&
                        accountPasswordRepeat.getText().toString().length() > 0 && accountWeight.getText().toString().length() > 0 && accountHeight.getText().toString().length() > 0) {
                    User tempUser = User.getUser(accountUsername.getText().toString());
                    if (tempUser == null) {
                        if (accountPassword.getText().toString().equals(accountPasswordRepeat.getText().toString())) {

                            if (isInteger(accountWeight.getText().toString())) {
                                if (isInteger(accountHeight.getText().toString())) {

                                    User finalUser = new User();
                                    finalUser.setUsername(accountUsername.getText().toString());
                                    finalUser.setPassword(accountPassword.getText().toString());
                                    finalUser.setStartWeight(Integer.parseInt(accountWeight.getText().toString()));
                                    finalUser.setHeight(Integer.parseInt(accountHeight.getText().toString()));
                                    finalUser.save();

                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);

                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(i);

                                } else {
                                    alert.showAlertDialog(RegisterActivity.this, "Creating account failed", "Wrong format of height value !");
                                }

                            } else {
                                alert.showAlertDialog(RegisterActivity.this, "Creating account failed", "Wrong format of weight value !");
                            }


                        } else {
                            alert.showAlertDialog(RegisterActivity.this, "Creating account failed", "Passwords must be the same !");
                        }
                    } else {
                        alert.showAlertDialog(RegisterActivity.this, "Creating account failed", "Username already exist !");
                    }
                } else {
                    alert.showAlertDialog(RegisterActivity.this, "Creating account failed", "All fields cannot be empty !");
                }
            }
        });
    }

    private void initializeFields() {
        accountUsername = (EditText) findViewById(R.id.accountUsername);
        accountPassword = (EditText) findViewById(R.id.accountPassword);
        accountPasswordRepeat = (EditText) findViewById(R.id.accountPasswordRepeat);
        accountWeight = (EditText) findViewById(R.id.accountWeight);
        accountHeight = (EditText) findViewById(R.id.accountHeight);
        registerButton = (Button) findViewById(R.id.registerButton);
        alert = new AlertDialogManager();
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
