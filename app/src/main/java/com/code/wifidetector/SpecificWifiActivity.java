package com.code.wifidetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by karan jain on 16-02-2017.
 */

public class SpecificWifiActivity extends AppCompatActivity {
    String name;
    List<ScanResult> wifiList;
    Intent i;
    String wifiName;
    TextView level2,name1;
    public static final String TAG="specifiwifiactivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificwifi);
         i=getIntent();
        level2= (TextView) findViewById(R.id.level2);
        name1= (TextView) findViewById(R.id.nameWifi);
        name=i.getStringExtra("rfv");
        wifiName=i.getStringExtra("name");

        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        wifiList = wifiManager.getScanResults();
        name1.setText(wifiName.toString());
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level,1000);
            String a=wifiList.toString();
            Log.d(TAG, "onCreate: "+level+"out of 5"+wifiList.toString());
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        //Note: Not using RSSI_CHANGED_ACTION because it never calls me back.
        IntentFilter rssiFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(myRssiChangeReceiver, rssiFilter);

        WifiManager wifiMan=(WifiManager) SpecificWifiActivity.this.getSystemService(Context.WIFI_SERVICE);
        for(int i=0;i<10;i++)
            if(wifiMan.strength<0)
                wifiMAn.strength = 0;

        wifiMan.startScan();
    }


    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(myRssiChangeReceiver);

    }
    /**
     * Broadcast receiver to update
     */
    private BroadcastReceiver myRssiChangeReceiver
            = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            WifiManager wifiMan=(WifiManager)SpecificWifiActivity.this.getSystemService(Context.WIFI_SERVICE);
            wifiMan.startScan();
            int newRssi = wifiMan.getConnectionInfo().getRssi();
            wifiList = wifiMan.getScanResults();
            for (ScanResult scanResult : wifiList) {
                //int level = WifiManager.calculateSignalLevel(scanResult.level,1000);

               // Log.d(TAG, "onCreate: "+level+"out of 5"+wifiList.toString());
                if(name.equals(scanResult.BSSID))
                {  int level1 = WifiManager.calculateSignalLevel(scanResult.level,10);
                   level2.setText(""+level1);
                Log.d(TAG, "onReceive: "+ level1+"  "+scanResult.SSID);}
            }


        }};
}
