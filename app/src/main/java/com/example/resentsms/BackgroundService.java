package com.example.resentsms;
import android.app.IntentService;
import android.content.*;
import android.content.SharedPreferences;

public class BackgroundService extends IntentService
{
    SharedPreferences shP;
    public BackgroundService()
    {
        super("myname");
    }
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String text = intent.getStringExtra("sms_body");

        shP = getSharedPreferences("key", 0);
        String token = shP.getString("TOKEN", null);
        String tgid = shP.getString("TGID", null);
        String check = shP.getString("CHECKBOX", null);

        if (text.isEmpty() == false)
        {
            if (token != null && tgid != null && check != null)
            {
                Bot bot = new Bot(token, tgid);
                if (check.equals("true"))
                {
                    bot.SendMessage(text);
                }

            }
        }
    }

}