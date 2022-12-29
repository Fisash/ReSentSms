package com.example.resentsms;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity
{
    public TextView phoneNum;
    public TextView tokenBot;
    public TextView tgID;
    public TextView log;
    public CheckBox isWorking;
    public Button button;

    public static String TOKEN;
    public static String PHONE;
    public static String TGID;
    public static String ISCHECK;

    SharedPreferences shP;

    public Bot bot;

    public boolean isActive = false;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        phoneNum = (EditText)findViewById(R.id.phoneNum);
        log = (TextView)findViewById(R.id.log);
        tgID = (EditText)findViewById(R.id.tgID);
        tokenBot = (EditText)findViewById(R.id.tokenBot);
        isWorking = (CheckBox) findViewById(R.id.isWorking);
        button = (Button)findViewById(R.id.saveButton);

        if(KeysExist())
        {
            LoadData();
        }
        button.setOnClickListener(v -> {
            SaveData();
            if(tokenBot.getText().toString().isEmpty() || tgID.getText().toString().isEmpty())
            {
                log.setText("Some field is empty");
            }
            else
            {
                if(isWorking.isChecked())
                {
                    isActive = true;
                    bot = new Bot(tokenBot.getText().toString(), tgID.getText().toString());
                    bot.SendMessage("Connected to the ReSentSMS");
                }
                else{
                    isActive = false;
                }
            }
        });
    }

    public void SaveData()
    {
        shP = getSharedPreferences("key", 0);
        SharedPreferences.Editor ed = shP.edit();
        ed.putString("TOKEN", tokenBot.getText().toString());
        ed.putString("PHONE", phoneNum.getText().toString());
        ed.putString("TGID", tgID.getText().toString());
        ed.putString("CHECKBOX", Boolean.toString(isWorking.isChecked()));
        ed.commit();
    }
    public boolean KeysExist()
    {
        shP =  getSharedPreferences("key", 0);
        return (shP.getString("TOKEN", null) != null) && (shP.getString("PHONE", null) != null) && (shP.getString("TGID", null) != null);

    }
    public void LoadData()
    {
        shP = getSharedPreferences("key", 0);
        TOKEN = shP.getString("TOKEN", null);
        tokenBot.setText(TOKEN);
        PHONE = shP.getString("PHONE", null);
        phoneNum.setText(PHONE);
        TGID = shP.getString("TGID", null);
        tgID.setText(TGID);
        ISCHECK = shP.getString("CHECKBOX", null);
        isWorking.setChecked(Boolean.valueOf(ISCHECK));
    }

    public static MainActivity getInstance()
    {
        return instance;
    }
}