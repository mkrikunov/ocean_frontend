package com.app.ocean;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import kotlin.internal.IntrinsicConstEvaluation;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumber, password;
    boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumber = findViewById(R.id.inputPhoneNumberL);
        password = findViewById(R.id.inputPasswordL);

        //Operation of the icon hiding the password:
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >=password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordVisible) {
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            //for hide password:
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        } else {
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_security, 0, R.drawable.ic_baseline_visibility_24, 0);
                            //for show password:
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

    }

    //move to reg page:
    public void moveToReg(View v) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    //move to main page:
    public void moveToMain(View v) {
        //some code for request???
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}