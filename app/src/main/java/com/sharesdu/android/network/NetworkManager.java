package com.sharesdu.android.network;
import org.json.JSONObject;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NetworkManager {

    private OkHttpClient client;
    private Handler mainHandler; //用于在主线程中处理结果

    public NetworkManager() {
        client = new OkHttpClient();
        mainHandler = new Handler(Looper.getMainLooper()); //初始化主线程的Handler
    }

    public void get(Context context, String url, NetworkCallback callback) {
        // 在新线程中执行网络请求
        new Thread(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // 处理请求失败
                    mainHandler.post(() -> callback.onFailure(e));
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        // 请求成功，返回结果
                        mainHandler.post(() -> callback.onSuccess(responseData));
                    } else {
                        // 处理错误情况
                        mainHandler.post(() -> callback.onError(response.code(), response.message()));
                    }
                }
            });
        }).start(); // 启动新线程
    }
    // 定义回调接口
    public interface NetworkCallback {
        void onSuccess(String result);
        void onFailure(IOException e);
        void onError(int code, String message);
    }
}