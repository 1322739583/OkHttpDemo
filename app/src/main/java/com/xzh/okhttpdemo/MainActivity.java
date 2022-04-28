package com.xzh.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);




    }

    public void nonasynGet(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.d("MainActivity", response.body().string());
                        Log.d("MainActivity", "response.code():" + response.code());
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asynGet(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.baidu.com").build();


        Log.d("MainActivity", "request.headers():" + request.headers().toString());


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                //   textView.setText(json);
                Log.d("MainActivity", "response.code():" + response.code());
                Log.d("MainActivity", json);
                Log.d("MainActivity", "response.headers():" + response.headers());
            }
        });
    }


    /**
     * 主要证明header和addHeader的区别，addHeader在key和value都相同的情况下，也可以多个一起添加.
     * header也是可以添加多个请求头的，但key一样的话，后面的会被替代。
     *
     * @param view
     */
    public void header(View view) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("header1", "v1")
                .addHeader("header1", "v1")
                .addHeader("header2", "v2")
                .addHeader("header3", "v3")
                .url("http://www.baidu.com")
                .build();



//        Request request = new Request.Builder()
//                .header("header1", "v1")
//                .header("header1", "v11")
//                .header("header2", "v2")
//                .header("header3", "v3")
//                .url("http://www.baidu.com")
//                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        Log.d("MainActivity", "request.headers():" + request.headers());

    }
}
