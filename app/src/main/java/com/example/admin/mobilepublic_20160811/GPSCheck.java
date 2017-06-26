package com.example.admin.mobilepublic_20160811;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 2016-06-23.
 */
public class GPSCheck extends Service implements LocationListener{


    private  boolean GPSEnabled = false;
    private  boolean NETWORKEnabled = false;

    private final Context mContext;

    private boolean isGetLocation = false;
    private  Location location;
    protected LocationManager locationManager;


    public GPSCheck(Context context) {
        this.mContext = context;
        Log.v("gps : ","GPS Check");
    }

    public boolean isGetLocation(){
        return this.isGetLocation;
    }


    public String getLocation(){

        String gps_type = null;

        try{
            locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            NETWORKEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!GPSEnabled && ! NETWORKEnabled){
                //gps 연결 안됨
                gps_type = "NOT";
                Toast.makeText(mContext,"gps 연결이 되지 않았습니다", Toast.LENGTH_SHORT).show();
            }
            else{
                this.isGetLocation = true;

                if(NETWORKEnabled){
                    gps_type = "NETWORK";

                  //  Toast.makeText(mContext,"gps NETWORK", Toast.LENGTH_SHORT).show();
                    //네트워크로 gps 연결
                    Log.v("gps : ","GPS NETWORK");

                }
                if(GPSEnabled){
                   // Toast.makeText(mContext,"gps GPS", Toast.LENGTH_SHORT).show();
                    gps_type = "GPS";
                    Log.v("gps : ","GPS gps");

                    //gps 연결
                }


            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return gps_type;

    }
    

    public void showSettingAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        Log.v("gps setting","gps setting");
        alertDialog.setTitle("GPS Setting");
        alertDialog.setMessage("GPS를 킨 후 다시 버튼을 눌러주세요. 설정창으로 가시겠습니까?");

                // OK 를 누르게 되면 설정창으로 이동합니다.
                alertDialog.setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                mContext.startActivity(intent);
                            }
                        });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
