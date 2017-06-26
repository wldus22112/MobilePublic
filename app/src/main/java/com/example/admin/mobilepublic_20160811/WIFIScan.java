package com.example.admin.mobilepublic_20160811;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by admin on 2016-06-10.
 */
public class WIFIScan {

    ConnectivityManager conMgr;

    public String getWIFISSID(Context context){

        WifiManager manager = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("SSID : ",ssid);

        return ssid;
    }

    public String checkWIFI(String ssid){

        String url = "";
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if((info!=null)&&(info.isAvailable()==true)) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //WIFI 연결
                if(ssid.contains("Sundo")==true){

                    url = "http://192.168.100.132:8080/mobile/";
                    Log.d("url : ",url);
                    Log.d("인터넷연결 : ","선도내부망");
                }
                else{
                    url = "http://210.113.102.132:8080/mobile/";;
                    Log.d("인터넷연결 : ","외부망");
                }

            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                //Data 연결
                url = "http://210.113.102.132:8080/mobile/";;
                Log.d("인터넷연결 : ","데이터");
            }
            else{
            }
        }

        return url;
    }


    public String ArcgisCheckWIFI(String ssid){

        String url = "";
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if((info!=null)&&(info.isAvailable()==true)) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //WIFI 연결
                if(ssid.contains("Sundo")==true){

                    url = "http://192.168.100.147:8399/arcgis/rest/services/PubMap2/MapServer";
                    Log.d("url : ",url);
                    Log.d("인터넷연결 : ","선도내부망");
                }
                else{
                    url = "http://210.113.102.147:8399/arcgis/rest/services/PubMap2/MapServer";
                    Log.d("인터넷연결 : ","외부망");
                }

            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                //Data 연결
                url = "http://210.113.102.147:8399/arcgis/rest/services/PubMap2/MapServer";
                Log.d("인터넷연결 : ","데이터");
            }
            else{
            }
        }

        return url;
    }





}

