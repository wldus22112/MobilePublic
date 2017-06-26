package com.example.admin.mobilepublic_20160811.MakerInfo;

import android.app.Application;

import com.example.admin.mobilepublic_20160811.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

/**
 * Created by admin on 2016-05-27.
 */
public class AddMarker extends Application {


    public AddMarker() {
    }


    public MapPOIItem getPoint(double x, double y, String name, String apvregno, String type, String number){




        MapPOIItem[] poiItems;

        MapPoint makerpoint = MapPoint.mapPointWithGeoCoord(x,y);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setUserObject(apvregno);
        //marker.setTag(0);
        marker.setMapPoint(makerpoint);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        //메인플랫폼 회사 마커
        if(number!="checkspot"){

            //1종
            if(number.contains("1")==true){

                if(type.contains("1")==true){
                    marker.setCustomImageResourceId(R.drawable.red_marker11);
                }
                else if (type.contains("2")==true){
                    marker.setCustomImageResourceId(R.drawable.blue_marker11);
                }
            }
            //2종
            else if (number.contains("2")== true){

                if(type.contains("1")==true){
                    marker.setCustomImageResourceId(R.drawable.red_marker22);
                }
                else if (type.contains("2")==true){
                    marker.setCustomImageResourceId(R.drawable.blue_marker22);
                }
            }
            //3종
            else if (number.contains("3")== true){

                if(type.contains("1")==true){
                    marker.setCustomImageResourceId(R.drawable.red_marker33);
                }
                else if (type.contains("2")==true){
                    marker.setCustomImageResourceId(R.drawable.blue_marker33);
                }
            }
            //4종
            else if (number.contains("4")==true){

                if(type.contains("1")==true){
                    marker.setCustomImageResourceId(R.drawable.red_marker44);
                }
                else if (type.contains("2")==true){
                    marker.setCustomImageResourceId(R.drawable.blue_marker44);
                }
            }
            //5종
            else if (number.contains("5")==true){
                if(type.contains("1")){
                    marker.setCustomImageResourceId(R.drawable.red_marker55);
                }
                else if (type.contains("2")){
                    marker.setCustomImageResourceId(R.drawable.blue_marker55);
                }
            }
            else{
                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
            }

            marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);

        }
        //측정소 마커
        else {
            if(type=="air"){
                marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
            }else if (type=="water"){
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            }
        }



        //mapView.addPOIItem(marker);

        return marker;

    }




}
