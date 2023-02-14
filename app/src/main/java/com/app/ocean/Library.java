package com.app.ocean;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Library {

    final OkHttpClient client = new OkHttpClient();

    String post(String url) throws IOException {
        RequestBody body = RequestBody.create(null, new byte[]{});
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void main(String nameVal, String phoneNumberVal, String passwordVal) throws IOException {

        //String nameVal = "Krikunov";
        //String phoneNumberVal = "89039043556";
        //String passwordVal = "mkk243k434";

        Library example = new Library();
        String response = example.post("http://localhost:8080/demo/add?name=" + nameVal +
                "&phoneNumber=" + phoneNumberVal + "&password=" + passwordVal);
        System.out.println(response);
    }

}
