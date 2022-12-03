package com.publisher.firstproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView text;
    EditText editText;
    private int curBrightnessValue;
    private boolean wifiTerm = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnActionVies();
        actions();
    }

    private void actions() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("Authorization check");

        alertDialog.setMessage("Are you authorized to access the application?");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                onStop();

            } });

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "    ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // Passed first term
            }});

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

                onStop();

            }});
        alertDialog.show();

    }

    private void setOnActionVies() {

        button.setOnClickListener(e->{
            checkForInternetConaction();



            curBrightnessValue = android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS,-1);
            if(wifiTerm && curBrightnessValue == 255 && checkForBatteryPrecents() == Integer.parseInt(String.valueOf(editText.getText())))
                text.setText("Connected");
        });

    }

    private int checkForBatteryPrecents() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return (level * 100) / (int)scale;
//        Toast.makeText(MainActivity.this, String.valueOf(batteryPct),
//                Toast.LENGTH_LONG).show();
    }

    private void checkForInternetConaction() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            wifiTerm = false;
        }
        else
            wifiTerm = true;
    }


    private void initViews() {
        button = findViewById(R.id.Button);
        text = findViewById(R.id.lableText);
        editText = findViewById(R.id.simpleEditText);
    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

}