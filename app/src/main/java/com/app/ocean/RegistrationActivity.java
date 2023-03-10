package com.app.ocean;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RegistrationActivity extends AppCompatActivity {
    EditText name, phoneNumber, password;
    Button regButton;
    boolean passwordVisible;
    OkHttpClient client;


    //get ip method
    public static String getIP() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));
        String ip = (String)socket.getLocalAddress().getHostAddress();

        return ip;
    }

    //post request method
    public void post(String postUrl) {
        RequestBody body = RequestBody.create(null, new byte[]{});
        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.inputName);
        phoneNumber = findViewById(R.id.inputPhoneNumberR);
        password = findViewById(R.id.inputPasswordR);

        regButton = findViewById(R.id.buttonReg);


        //Operation of the icon hiding the password:
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
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


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send post request
                String nameVal = name.getText().toString();
                String phoneNumberVal = phoneNumber.getText().toString();
                String passwordVal = password.getText().toString();

                String localhost = "";
                try {
                    localhost = getIP();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url = "http://" + localhost + ":8080/demo/add?name=" + nameVal +
                        "&phoneNumber=" + phoneNumberVal + "&password=" + passwordVal;

                client = new OkHttpClient();
                post(url);

                //move to main page
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }




    //move to login page:
    public void moveToLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}