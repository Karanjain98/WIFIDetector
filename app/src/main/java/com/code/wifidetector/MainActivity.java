package com.code.wifidetector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="mainacta";
    List<ScanResult> wifiList;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"INSIDE ON CREATE" );
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        rv= (RecyclerView) findViewById(R.id.rv);
        WIFIAdapter wa=new WIFIAdapter();
        LinearLayoutManager la=new LinearLayoutManager(this);


// Level of a Scan Result
        wifiList = wifiManager.getScanResults();
        for (ScanResult scanResult : wifiList) {
            int level = WifiManager.calculateSignalLevel(scanResult.level,1000);
            String a=wifiList.toString();
            Log.d(TAG, "onCreate: "+level+"out of 5"+wifiList.toString());
        }
        for(ScanResult scanResult : wifiList)
        {

            int level2=WifiManager.calculateSignalLevel(scanResult.level,10)

            String ab=wifiList.toString();
        }

// Level of current connection
        int rssi = wifiManager.getConnectionInfo().getRssi();
        int level = WifiManager.calculateSignalLevel(rssi, 5);
        rv.setAdapter(wa);
        rv.setLayoutManager(la);


    }
    public class WifiHolder extends RecyclerView.ViewHolder {

        public WifiHolder(View itemView) {
            super(itemView);
            view =itemView;
            t= (TextView) itemView.findViewById(R.id.name);
        }
        View view;
        TextView t;


    }
    public class WIFIAdapter extends RecyclerView.Adapter<WifiHolder>{


        @Override
        public WifiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li= getLayoutInflater();
            View convertview=null;
            convertview=li.inflate(R.layout.list_item,parent,false);
            WifiHolder wv=new WifiHolder(convertview);
            return wv;

        }

        @Override
        public void onBindViewHolder(WifiHolder holder, final int position) {

            holder.t.setText(wifiList.get(position).SSID.toString());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(MainActivity.this,SpecificWifiActivity.class);
                    i.putExtra("rfv",wifiList.get(position).BSSID.toString());
                    i.putExtra("name",wifiList.get(position).SSID);
                    startActivity(i);
                }
            });

        }
        
        @Override
        public void onRenderViewHolder(ViewGroup parent) {

            holder.t.setText(wifiList.get(parent.position).SSID.toString());
            holder.view.setOnClickListener(new View.OnClickListener();
        }

        @Override
        public int getItemCount() {
            return wifiList.size();
        }
    }
}
