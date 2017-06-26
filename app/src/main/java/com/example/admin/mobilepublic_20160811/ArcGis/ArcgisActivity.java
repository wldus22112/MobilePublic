package com.example.admin.mobilepublic_20160811.ArcGis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISLayerInfo;
import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.runtime.LicenseLevel;
import com.esri.core.runtime.LicenseResult;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;
import com.example.admin.mobilepublic_20160811.uploadservice.GPSInfo;

/**
 * Created by admin on 2016-07-27.
 */
public class ArcgisActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    MapView mMapView;

    private CheckBox[] checkBoxes;

    ArcGISTiledMapServiceLayer tileLayer;
    ArcGISDynamicMapServiceLayer subjectLayer;
    ArcGISLayerInfo layerInfo[];
    ArcGISLayerInfo infoLayer[];

    String tileLayerURL = "http://services.arcgisonline.com/arcgis/rest/services/World_Imagery/MapServer";
    //String dynamicLayerURL = "http://192.168.100.147:8399/arcgis/rest/services/Mobile_Subject/MapServer";
  //  String dynamicLayerURL = "http://192.168.100.147:8399/arcgis/rest/services/PubMap2/MapServer";
    //String dynamicLayerURL = "http://210.113.102.147:8399/arcgis/rest/services/Mobile_Subject/MapServer";
    String dynamicLayerURL;

    ArcGISLocalTiledLayer localTileLayer;
    FeatureLayer localFeatureLayer;
    FeatureLayer localFeatureLayer2;
    GraphicsLayer graphicsLayer;

    GraphicsLayer mPositionLayer;

    Drawable pMarkerSymbol_img;
    PictureMarkerSymbol pMarkerSymbol;

    private double pointX = 0;
    private double pointY = 0;
    private Point mPoint = null;
    private WIFIScan wifiScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arcgis_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wifiScan = new WIFIScan();
        dynamicLayerURL =   wifiScan.ArcgisCheckWIFI(wifiScan.getWIFISSID(this));


        //=========================================================================================
        //==== Basic License OK ===================================================================
        //=========================================================================================
        LicenseResult licenseResult = ArcGISRuntime.setClientId("cEWbx5PN7PLQwqnM");
        LicenseLevel licenseLevel = ArcGISRuntime.License.getLicenseLevel();

        if (licenseResult == LicenseResult.VALID && licenseLevel == LicenseLevel.BASIC) {
            //Toast.makeText(ArcgisActivity.this, "License OK", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(ArcgisActivity.this, "License Failed", Toast.LENGTH_SHORT).show();
        }
        //=========================================================================================

        mMapView = (MapView) findViewById(R.id.map);

        checkBoxes = new CheckBox[30];
        checkBoxes[0] = (CheckBox) findViewById(R.id.checkbox01);
        checkBoxes[1] = (CheckBox) findViewById(R.id.checkbox02);
        checkBoxes[2] = (CheckBox) findViewById(R.id.checkbox03);
        checkBoxes[3] = (CheckBox) findViewById(R.id.checkbox04);
        checkBoxes[4] = (CheckBox) findViewById(R.id.checkbox05);
        checkBoxes[5] = (CheckBox) findViewById(R.id.checkbox06);
        checkBoxes[6] = (CheckBox) findViewById(R.id.checkbox07);
        checkBoxes[7] = (CheckBox) findViewById(R.id.checkbox08);
        checkBoxes[8] = (CheckBox) findViewById(R.id.checkbox09);
        checkBoxes[9] = (CheckBox) findViewById(R.id.checkbox10);
        checkBoxes[10] = (CheckBox) findViewById(R.id.checkbox11);
        checkBoxes[11] = (CheckBox) findViewById(R.id.checkbox12);
        checkBoxes[12] = (CheckBox) findViewById(R.id.checkbox13);
        checkBoxes[13] = (CheckBox) findViewById(R.id.checkbox14);
        checkBoxes[14] = (CheckBox) findViewById(R.id.checkbox15);
        checkBoxes[15] = (CheckBox) findViewById(R.id.checkbox16);
        checkBoxes[16] = (CheckBox) findViewById(R.id.checkbox17);
        checkBoxes[17] = (CheckBox) findViewById(R.id.checkbox18);
        checkBoxes[18] = (CheckBox) findViewById(R.id.checkbox19);
        checkBoxes[19] = (CheckBox) findViewById(R.id.checkbox20);
        checkBoxes[20] = (CheckBox) findViewById(R.id.checkbox21);
        checkBoxes[21] = (CheckBox) findViewById(R.id.checkbox22);
        checkBoxes[22] = (CheckBox) findViewById(R.id.checkbox23);
        checkBoxes[23] = (CheckBox) findViewById(R.id.checkbox24);
        checkBoxes[24] = (CheckBox) findViewById(R.id.checkbox25);
        checkBoxes[25] = (CheckBox) findViewById(R.id.checkbox26);
        checkBoxes[26] = (CheckBox) findViewById(R.id.checkbox27);
        checkBoxes[27] = (CheckBox) findViewById(R.id.checkbox28);
        checkBoxes[28] = (CheckBox) findViewById(R.id.checkbox29);
        checkBoxes[29] = (CheckBox) findViewById(R.id.checkbox30);


        for(int i =0;i<checkBoxes.length;i++){
            checkBoxes[i].setOnCheckedChangeListener(this);
        }

        tileLayer = new ArcGISTiledMapServiceLayer(tileLayerURL);
        mMapView.addLayer(tileLayer);

        subjectLayer = new ArcGISDynamicMapServiceLayer(dynamicLayerURL);
        mMapView.addLayer(subjectLayer);
        mMapView.getLayer(0).setVisible(true);
        layerInfo = subjectLayer.getLayers();
        infoLayer = subjectLayer.getAllLayers();

        mPositionLayer = new GraphicsLayer();
        mMapView.addLayer(mPositionLayer);

        Envelope initEnvelope = new Envelope(13817105.78, 4211553.57, 14648129.15, 4578451.31);
        mMapView.setExtent(initEnvelope);


        pMarkerSymbol_img = getResources().getDrawable(R.drawable.here);
        pMarkerSymbol = new PictureMarkerSymbol(pMarkerSymbol_img);

        getMyLocation();



    }




    @Override
    protected void onDestroy(){      //액티비티가 종료될 때의 메서드

        super.onDestroy();
        Intent i = new Intent();
        setResult(3,i);
        Log.v("onDestroy :","case 3");

        finish();

    }




    private void getMyLocation() {
        GPSInfo gpsInfo = new GPSInfo(this);
        gpsInfo.getLocation();
        pointY = gpsInfo.getLatitude();
        pointX = gpsInfo.getLongitude();

        mPoint = (Point) GeometryEngine.project(pointX, pointY, SpatialReference.create(SpatialReference.WKID_WGS84_WEB_MERCATOR_AUXILIARY_SPHERE));

        gpsInfo.stopUsingGPS();

        setCurrentPosition(mPoint);
    }

    private void setCurrentPosition(Point mPt) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            //Fnc_showToast("권한없음");
        } else {
            Graphic graphic = new Graphic(mPt, pMarkerSymbol);
            mPositionLayer.removeAll();
            mPositionLayer.addGraphic(graphic);

            double minX = mPt.getX() - 1000;
            double minY = mPt.getY() - 1000;
            double maxX = mPt.getX() + 1000;
            double maxY = mPt.getY() + 1000;

            Envelope initEnvelope = new Envelope(minX, minY, maxX, maxY);
            mMapView.setExtent(initEnvelope);
        }

    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                // NavUtils.navigateUpFromSameTask(this);
                setResult(3);
                Log.v("onOptionsItemSelected :","case 3");

                finish();

                return true;

        }

        return super.onOptionsItemSelected(item);

    };



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.v("onCheckedChanged :","onCheckedChanged");
        layerInfo = subjectLayer.getLayers();
        layerInfo = subjectLayer.getAllLayers();

        switch (buttonView.getId()){
            case R.id.checkbox01 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(2).setVisible(true);
                    layerInfo[0].setVisible(true);
                }else {
                    //mMapView.getLayer(2).setVisible(false);
                    layerInfo[0].setVisible(false);
                }

                break;

            case R.id.checkbox02 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(3).setVisible(true);
                    layerInfo[1].setVisible(true);
                }else {
                    //mMapView.getLayer(3).setVisible(false);
                    layerInfo[1].setVisible(false);
                }

                break;

            case R.id.checkbox03 :
                if(isChecked) {
                    Log.v("onCheckedChanged :","isChecked");
                    //mMapView.getLayer(4).setVisible(true);
                    layerInfo[2].setVisible(true);
                }else {
                    //mMapView.getLayer(4).setVisible(false);
                    layerInfo[2].setVisible(false);
                }

                break;
            case R.id.checkbox04 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(5).setVisible(true);
                    layerInfo[3].setVisible(true);
                }else {
                    //mMapView.getLayer(5).setVisible(false);
                    layerInfo[3].setVisible(false);
                }

                break;
            case R.id.checkbox05 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(6).setVisible(true);
                    layerInfo[4].setVisible(true);
                }else {
                    //mMapView.getLayer(6).setVisible(false);
                    layerInfo[4].setVisible(false);
                }


                break;
            case R.id.checkbox06 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(7).setVisible(true);
                    layerInfo[5].setVisible(true);
                }else {
                    //mMapView.getLayer(7).setVisible(false);
                    layerInfo[5].setVisible(false);
                }

                break;
            case R.id.checkbox07 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(8).setVisible(true);
                    layerInfo[6].setVisible(true);
                }else {
                    //mMapView.getLayer(8).setVisible(false);
                    layerInfo[6].setVisible(false);
                }

                break;
            case R.id.checkbox08 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(9).setVisible(true);
                    layerInfo[7].setVisible(true);
                }else {
                    //mMapView.getLayer(9).setVisible(false);
                    layerInfo[7].setVisible(false);
                }

                break;
            case R.id.checkbox09 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(10).setVisible(true);
                    layerInfo[8].setVisible(true);
                }else {
                    //mMapView.getLayer(10).setVisible(false);
                    layerInfo[8].setVisible(false);
                }

                break;
            case R.id.checkbox10 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(11).setVisible(true);
                    layerInfo[9].setVisible(true);
                }else {
                    //mMapView.getLayer(11).setVisible(false);
                    layerInfo[9].setVisible(false);
                }

                break;
            case R.id.checkbox11:
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(22).setVisible(true);
                    layerInfo[10].setVisible(true);
                }else {
                    //mMapView.getLayer(22).setVisible(false);
                    layerInfo[10].setVisible(false);
                }

                break;
            case R.id.checkbox12 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(23).setVisible(true);
                    layerInfo[11].setVisible(true);
                }else {
                    //mMapView.getLayer(23).setVisible(false);
                    layerInfo[11].setVisible(false);
                }

                break;

            case R.id.checkbox13 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(24).setVisible(true);
                    layerInfo[12].setVisible(true);
                }else {
                    //mMapView.getLayer(24).setVisible(false);
                    layerInfo[12].setVisible(false);
                }

                break;
            case R.id.checkbox14 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(25).setVisible(true);
                    layerInfo[13].setVisible(true);
                }else {
                    //mMapView.getLayer(25).setVisible(false);
                    layerInfo[13].setVisible(false);
                }

                break;
            case R.id.checkbox15 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(26).setVisible(true);
                    layerInfo[14].setVisible(true);
                }else {
                    //mMapView.getLayer(26).setVisible(false);
                    layerInfo[14].setVisible(false);
                }

                break;
            case R.id.checkbox16 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(27).setVisible(true);
                    layerInfo[15].setVisible(true);
                }else {
                    //mMapView.getLayer(27).setVisible(false);
                    layerInfo[15].setVisible(false);
                }

                break;
            case R.id.checkbox17 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(28).setVisible(true);
                    layerInfo[16].setVisible(true);
                }else {
                    //mMapView.getLayer(28).setVisible(false);
                    layerInfo[16].setVisible(false);
                }

                break;
            case R.id.checkbox18 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(29).setVisible(true);
                    layerInfo[17].setVisible(true);
                }else {
                    //mMapView.getLayer(29).setVisible(false);
                    layerInfo[17].setVisible(false);
                }

                break;
            case R.id.checkbox19 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(31).setVisible(true);
                    layerInfo[18].setVisible(true);
                }else {
                    //mMapView.getLayer(31).setVisible(false);
                    layerInfo[18].setVisible(false);
                }

                break;

            case R.id.checkbox20 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(32).setVisible(true);
                    layerInfo[19].setVisible(true);
                }else {
                    //mMapView.getLayer(32).setVisible(false);
                    layerInfo[19].setVisible(false);
                }

                break;
            case R.id.checkbox21 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(33).setVisible(true);
                    layerInfo[20].setVisible(true);
                }else {
                    //mMapView.getLayer(33).setVisible(false);
                    layerInfo[20].setVisible(false);
                }

                break;
            case R.id.checkbox22 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(34).setVisible(true);
                    layerInfo[21].setVisible(true);
                }else {
                    //mMapView.getLayer(34).setVisible(false);
                    layerInfo[21].setVisible(false);
                }

                break;
            case R.id.checkbox23 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(35).setVisible(true);
                    layerInfo[22].setVisible(true);
                }else {
                    //mMapView.getLayer(35).setVisible(false);
                    layerInfo[22].setVisible(false);
                }

                break;
            case R.id.checkbox24:
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(36).setVisible(true);
                    layerInfo[23].setVisible(true);
                }else {
                    //mMapView.getLayer(36).setVisible(false);
                    layerInfo[23].setVisible(false);
                }

                break;
            case R.id.checkbox25 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(37).setVisible(true);
                    layerInfo[24].setVisible(true);
                }else {
                    //mMapView.getLayer(37).setVisible(false);
                    layerInfo[24].setVisible(false);
                }

                break;
            case R.id.checkbox26 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(38).setVisible(true);
                    layerInfo[25].setVisible(true);
                }else {
                    //mMapView.getLayer(38).setVisible(false);
                    layerInfo[25].setVisible(false);
                }

                break;
            case R.id.checkbox27 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(39).setVisible(true);
                    layerInfo[26].setVisible(true);
                }else {
                    //mMapView.getLayer(39).setVisible(false);
                    layerInfo[26].setVisible(false);
                }

                break;
            case R.id.checkbox28 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(24).setVisible(true);
                    layerInfo[27].setVisible(true);
                }else {
                    //mMapView.getLayer(24).setVisible(false);
                    layerInfo[27].setVisible(false);
                }

                break;
            case R.id.checkbox29 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(24).setVisible(true);
                    layerInfo[28].setVisible(true);
                }else {
                    //mMapView.getLayer(24).setVisible(false);
                    layerInfo[28].setVisible(false);
                }

                break;
            case R.id.checkbox30 :
                if(isChecked){
                    Log.v("onCheckedChanged :", "isChecked");
                    //mMapView.getLayer(24).setVisible(true);
                    layerInfo[29].setVisible(true);
                }else {
                    //mMapView.getLayer(24).setVisible(false);
                    layerInfo[29].setVisible(false);
                }

                break;




        }
            subjectLayer.refresh();

    }

    public void onClick(View view) {

        int num = 0;
        Intent intent = new Intent(this, ArcGisInfo.class);

        switch (view.getId()){

            case R.id.txtCov:

                num = 3;
                intent.putExtra("num",num);

                startActivity(intent);

                break;

            case R.id.txtEco:

                num = 2;
                intent.putExtra("num",num);

                startActivity(intent);

                break;

            case R.id.txtKor:

                num = 1;
                intent.putExtra("num",num);

                startActivity(intent);


                break;

        }
    }
}