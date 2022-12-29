package com.example.resentsms;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Bot
{
    String token;
    String chat_id;
    OkHttpClient client = new OkHttpClient();
    MainActivity a = MainActivity.getInstance();

    public Bot(String token, String chat_id)
    {
        this.token = token;
        this.chat_id = chat_id;
    }
    public void SendMessage(String message)
    {
        HttpsPost("https://api.telegram.org/bot" + token + "/sendMessage?text=" + message +"&chat_id=" + chat_id, true);
    }

    public void HttpsPost(String url, boolean log)
    {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                a.log.setText(e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    a.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if(log)
                            {
                                a.log.setText(myResponse);
                            }
                        }
                    });
                }
            }
        });
    }
}
