package com.example.resentsms;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver
{
    SharedPreferences shP;

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        shP = context.getSharedPreferences("key", 0);
        Intent mIntent = new Intent(context, BackgroundService.class);

        if (intent.getAction().equals(SMS_RECEIVED))
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0)
                {
                    return;
                }
                SmsMessage[] messages = new SmsMessage[pdus.length];
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pdus.length; i++)
                {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sb.append(messages[i].getMessageBody());
                }
                String message = sb.toString();
                String sender = messages[0].getOriginatingAddress();
                //Delete space
                sender = sender.replaceAll("\\s+", "");
                if(shP.getString("PHONE", null) == null || shP.getString("TOKEN", null) == null || shP.getString("TGID", null) == null)
                {
                    return;
                }
                String phoneN = shP.getString("PHONE", null);
                phoneN = phoneN.replaceAll("\\s+", "");

                if(sender.equals(phoneN))//Received sms is from perfect number
                {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    if(shP.getString("CHECKBOX", null).equals("true"))
                    {
                        String finalMsg = message;
                        mIntent.putExtra("sms_body", finalMsg);
                        context.startService(mIntent);
                    }

                }
                abortBroadcast();
            }
        }
    }
}

