package com.example.admin.mobilepublic_20160811;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.mobilepublic_20160811.ArcGis.ArcgisActivity;
import com.example.admin.mobilepublic_20160811.CheckSpotChart.CheckSpot;
import com.example.admin.mobilepublic_20160811.CompanyInfo.InfoForm;
import com.example.admin.mobilepublic_20160811.Information.AppInfo;
import com.example.admin.mobilepublic_20160811.MakerInfo.AddMarker;
import com.example.admin.mobilepublic_20160811.ReportReturn.ReportForm;
import com.example.admin.mobilepublic_20160811.ReportReturn.ReportInfo;
import com.example.admin.mobilepublic_20160811.Search.CompanySearch;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import static java.lang.Double.valueOf;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MapView.POIItemEventListener,MapView.MapViewEventListener, View.OnTouchListener,MapView.CurrentLocationEventListener, View.OnClickListener,View.OnKeyListener {



    private String key = "a92026b5460d893aa5b703130f17b61e";

    private AQuery aq = new AQuery(this);
    private Toolbar toolbar;

    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;
    static String DEFAULT_BASEMAP_PATH = Environment.getExternalStorageDirectory().getPath();
    String localTileURL = DEFAULT_BASEMAP_PATH + "/Env_Data/OfflineMap/Base_20160526.tpk";
    String gdbPath1 = DEFAULT_BASEMAP_PATH + "/Env_Data/OfflineMap/combase.geodatabase";
    String gdbPath2 = DEFAULT_BASEMAP_PATH + "/Env_Data/OfflineMap/Subject.geodatabase";

    private MapView mapView;
    private ViewGroup mapViewContainer ;

    String temp1 ="temp1";
    String temp2 ="temp2";
    private AddMarker addmarker = new AddMarker();


    private int Number;

    private  MapPOIItem[] poiItemsMain;
    private  MapPOIItem[] poiItemsSpot;
    private  MapPOIItem[] poiItemsReport;
    private MapPOIItem[] poiItemwater;
    private MapPOIItem[] poiItemair;
    private MapPOIItem[] poiItemetc;
    private WIFIScan wifiScan;
    private String url;
    private int gpsNumber;
    private  GPSCheck gpscheck;

    private LocationManager locationManager;

    private boolean report_first = false;
    private ProgressDialog loading;
    private LinearLayout main_search;
    private LinearLayout main2;
    private TextView main_text;
    private EditText input_text;
    private Button main_search_btn;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private  ActionBarDrawerToggle toggle;
    private  NavigationView navigationView;
    private LinearLayout report_text;

    private LinearLayout report_water;
    private LinearLayout report_air;
    private LinearLayout report_etc;

    private String CheckType = null;
    public Typeface typeface;
    private ProgressDialog dialog ;

    private int call_dialog;


    private static final int REQUEST_CODE_LOCATION = 2;




    /**  메인 지도 함수 구현 및 버튼 이벤트 **/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity : ", "Start MainActivity!");

        permissionCheck();

        Intent get_intent = getIntent();
        String get_menu_num = get_intent.getStringExtra("menu_num");
        Log.d("menu_num",get_menu_num.toString());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b0d4")));



       // getSupportActionBar().setIcon(R.drawable.main_mini);
       // getSupportActionBar().setTitle("  배출업소 조회");

        //permissionCheck();



        fab  = (FloatingActionButton) findViewById(R.id.gpsbutton);

        fab.setOnClickListener(this);

       // fab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00c1de")));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




       // navigationView.getMenu().getItem(0).setChecked(true);


        gpscheck = new GPSCheck(this);


       // Number = 1;
        gpsNumber = 0;


        mapView = new MapView(this);
        mapView.setDaumMapApiKey(key);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setZoomLevel(3, true);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        main_search = (LinearLayout)findViewById(R.id.main_search);
        main2 = (LinearLayout)findViewById(R.id.main2);
        main_search_btn = (Button)findViewById(R.id.main_search_btn);
        input_text = (EditText)findViewById(R.id.input_text);
        report_text = (LinearLayout)findViewById(R.id.report_text);

        report_water = (LinearLayout)findViewById(R.id.report_water);
        CheckType = "WATER";
        report_air = (LinearLayout)findViewById(R.id.report_air);
        report_etc = (LinearLayout)findViewById(R.id.report_etc);

        report_text.setVisibility(View.GONE);


        main2.setVisibility(View.GONE);


        wifiScan = new WIFIScan();
        url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
        if(Number==1){
            url = url+"main.do";
        }
        Log.d("url : ",url);

        //GPS 버튼
       // nowGPS = (Button) findViewById(R.id.gpsbutton);
        //nowGPS.bringToFront();
        //nowGPS.setOnTouchListener(this);



        /**메인메뉴에서 받아온 인자로 함수 실행**/

        if(get_menu_num.contains("0")){
            //배출업소 조회
            onNavigationItemSelected( navigationView.getMenu().getItem(0));
            navigationView.setCheckedItem(R.id.main_button);
            Number = 1;
            //check_main_button();



        }else if(get_menu_num.contains("1")){
            //측정소 조회
            onNavigationItemSelected( navigationView.getMenu().getItem(1));
            navigationView.getMenu().getItem(1).setChecked(true);
            navigationView.setCheckedItem(R.id.checkspot_button);
            Number = 2;

           //check_checkspot_button();


            toolbar.bringToFront();
            getSupportActionBar().show();


        }
        else if(get_menu_num.contains("3")){
            //신고하기
            onNavigationItemSelected( navigationView.getMenu().getItem(3));
            //navigationView.getMenu().getItem(3).setChecked(true);
            navigationView.setCheckedItem(R.id.return_button);
            Number = 4;
            call_dialog = 0;


        }

        input_text.setOnKeyListener(this);



    }






    //23.0 퍼미션 권한 체크
    public void permissionCheck(){
        if((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)

                )

        {
            ActivityCompat.requestPermissions
                    (MainActivity.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }

    }





    //좌측 상단바 뒤로가기 버튼
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    //네비게이션 드로어 선택 이벤트
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main_button) {
            //배출업소 조회

            getSupportActionBar().setIcon(R.drawable.main_mini);
            getSupportActionBar().setTitle("  배출업소 조회");
            fab.setVisibility(View.VISIBLE);

            report_text.setVisibility(View.GONE);


            main_search.setVisibility(View.VISIBLE);
            main2.setVisibility(View.GONE);

            if(Number==2){
                poiItemsSpot = mapView.getPOIItems();
                mapView.removeAllPOIItems();
                mapView.removeAllCircles();
            }
            else if(Number==4){
                poiItemsReport = mapView.getPOIItems();
                mapView.removeAllPOIItems();
                mapView.removeAllCircles();
            }
            else if(Number==3){
                mapView.removeAllPOIItems();
            }
            else {
                mapView.removeAllPOIItems();
                mapView.removeAllCircles();
            }
            Number = 1;
            if(poiItemsMain!=null){
                mapView.addPOIItems(poiItemsMain);
            }

            if(gpsNumber!=0){
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                Number=1;
            }


        } else if (id == R.id.checkspot_button) {

            //측정소 조회


            getSupportActionBar().setIcon(R.drawable.checkspot_mini);
            getSupportActionBar().setTitle("  측정소 조회");




          //  fab.setVisibility(View.INVISIBLE);

            main_search.setVisibility(View.GONE);
            main2.setVisibility(View.GONE);

            report_text.setVisibility(View.GONE);



            if(Number==1){
                poiItemsMain = mapView.getPOIItems();
                mapView.removeAllPOIItems();
                mapView.removeAllCircles();
            }
            else if(Number==4){
               // poiItemsReport = mapView.getPOIItems();
                mapView.removeAllPOIItems();
                mapView.removeAllCircles();
            }

            mapView.removeAllPOIItems();
            mapView.removeAllCircles();
            Number = 2;



            if(poiItemsSpot!=null){
                mapView.addPOIItems(poiItemsSpot);
            } else{


                /*

                //String url = "http://192.168.100.132:8080/mobile/checkSpot.do";
                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "checkSpot.do";
                Log.d("url : ",url);
                aq = new AQuery(this);
                aq.ajax(url, JSONObject.class, this, "checkSpotCallBack");*/

            }

            if(gpsNumber!=0){
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                Number=2;
            }



            Number=2;

        } else if (id == R.id.statistical_button) {

            //환경지도



            report_text.setVisibility(View.GONE);
            fab.setVisibility(View.INVISIBLE);
            main_search.setVisibility(View.GONE);
            main2.setVisibility(View.GONE);

            mapView.removeAllPOIItems();
           // Log.d("통계지도", main.getBackground().toString());



            /*
            if(Number==1){
                navigationView.getMenu().getItem(0).setChecked(true);
            }
            if(Number==2){
                navigationView.getMenu().getItem(1).setChecked(true);
            }
            if(Number==4){
                navigationView.getMenu().getItem(3).setChecked(true);
            }
*/




            Intent intent2 = new Intent(MainActivity.this, ArcgisActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



            //startActivity(intent2);

            startActivityForResult(intent2, 3);







        } else if (id == R.id.return_button) {

            //신고하기

            getSupportActionBar().setIcon(R.drawable.report_mini);
            getSupportActionBar().setTitle("  신고하기");
            fab.setVisibility(View.VISIBLE);


            call_dialog = 0;
            report_text.setVisibility(View.VISIBLE);

            main_search.setVisibility(View.GONE);
            main2.setVisibility(View.VISIBLE);
            report_text.bringToFront();

            if(Number==1){
                poiItemsMain =  mapView.getPOIItems();
            }else if(Number==2){
                poiItemsSpot = mapView.getPOIItems();
            }
            Number = 4;

            String gpstype = gpscheck.getLocation();

            if(gpstype!="NOT"){

                //gps 버튼을 누르지 않아도 현위치 자동 추적

                Log.d("gps", "gps 정보 받아오기");
                gpstype = gpscheck.getLocation();

                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                Toast.makeText(this,"현위치를 찾는 중입니다..", Toast.LENGTH_SHORT).show();
                gpsNumber = 1;
               //fab.setBackgroundResource(R.drawable.placeholder);
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder));



            }
            else{
                Log.d("gps", "gps 연결 안됨");
                gpscheck.showSettingAlert();
            }

            mapView.removeAllPOIItems();// 지도 위 마커 모두 제거

            url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
            url = url + "mobileReport.do";
            Log.d("url : ",url);
            aq = new AQuery(this);
            HashMap<String, Object> map = new HashMap<>();
            map.put("RP_TYPE", CheckType);
            aq.ajax(url, map,JSONObject.class, this, "reportCallBack");


        }else if(id==R.id.info_button){

            //앱 이용안내
            Intent intentinfo = new Intent(this, AppInfo.class);
            intentinfo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentinfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intentinfo);
        }



        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //현위치 탐색, 자동 업데이트
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

        if(Number==1&&gpsNumber==1 && mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading){
            //배출업소 조회 gps 탐색
            //  Toast.makeText(this,"현위치 탐색 중", Toast.LENGTH_SHORT).show();
            Log.d("LocationUpdate : ","gps 탐색");
            String lat = String.valueOf(mapPoint.getMapPointGeoCoord().latitude);
            String lng = String.valueOf(mapPoint.getMapPointGeoCoord().longitude);
            Log.d("lat",lat);
            Log.d("lng",lng);
            url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
            url = url + "trackingMode.do";
            Log.d("url : ", url);
            HashMap<String, Object> map = new HashMap<>();
            map.put("GIS_POINT_X", lng);
            map.put("GIS_POINT_Y", lat);
            aq = new AQuery(this);
            aq.ajax(url, map, JSONObject.class, this, "jsonCallback");
            mapView.setCurrentLocationRadius(500);
        }
        else if(Number==4 && mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading){
            //신고하기 gps 탐색
            report_first = true;

            //  loading.dismiss();
            if( call_dialog == 0){
                showLocationDialog(this,mapPoint);
            }


        }
        else if (Number==2 &&  mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading){




            mapView.removeAllPOIItems();

            Log.d("LocationUpdate : ","gps 탐색");
            String lat = String.valueOf(mapPoint.getMapPointGeoCoord().latitude);
            String lng = String.valueOf(mapPoint.getMapPointGeoCoord().longitude);
            Log.d("lat",lat);
            Log.d("lng",lng);
            url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
            url = url + "trackingModeCheckSpot.do";
            Log.d("url : ", url);
            HashMap<String, Object> map = new HashMap<>();
            map.put("GIS_POINT_X", lng);
            map.put("GIS_POINT_Y", lat);
            aq = new AQuery(this);
            aq.ajax(url, map, JSONObject.class, this, "checkSpotCallBack");
            mapView.setCurrentLocationRadius(500);

        }

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        Toast.makeText(this,"현위치 갱신 작업 실패", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        Toast.makeText(this,"현위치 갱신 작업 실패", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

        if(gpsNumber==1&&gpscheck.getLocation()!="NOT"){
            Log.d("apViewInitialized : ","apViewInitialized");
            // Toast.makeText(this, "GPS 초기화", Toast.LENGTH_SHORT).show();
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            mapView.setCurrentLocationRadius(500);
        }
        else{
            Log.d("apViewInitialized : ","apViewInitialized");
            //Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("좌표", valueOf(mapPoint.getMapPointGeoCoord().latitude).toString() +" , "+ valueOf(mapPoint.getMapPointGeoCoord().longitude).toString() );
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        if(Number==4){

            //ReportDialog dialog1 = new ReportDialog(this);
            //com.example.administrator.map.ReportReturn.ReportDialog dialog = new com.example.administrator.map.ReportReturn.ReportDialog(this);
            //dialog.showLocationDialog(this, mapPoint);
            showLocationDialog(this,mapPoint);
        }
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        if(Number==1){


            MapPoint mapPoint = mapPOIItem.getMapPoint();
            //mapPoint.getMapPointGeoCoord();
            String lat = String.valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude);
            String lng = String.valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude);

            //Toast.makeText(this.getActivity(), lat +" , "+lng, Toast.LENGTH_SHORT).show();;
        }


    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

        if(Number ==1){
            Intent intent = new Intent(this, InfoForm.class);
            double lat = valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude);
            double lng = valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude);
            String APV_REG_NO = mapPOIItem.getUserObject().toString();

            Log.d("회사코드",APV_REG_NO);
            intent.putExtra("latitude",lat);
            intent.putExtra("longitude",lng);
            intent.putExtra("APV_REG_NO",APV_REG_NO);


            startActivity(intent);
        }
        else if(Number ==2){
            Intent intent = new Intent(this, CheckSpot.class);
            double lat = valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude);
            double lng = valueOf(mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude);
            String STATION_CD = mapPOIItem.getUserObject().toString();

            Log.d("회사코드",STATION_CD);
            intent.putExtra("latitude",lat);
            intent.putExtra("longitude",lng);
            intent.putExtra("STATION_CD",STATION_CD);


            startActivity(intent);

        }
        else if(Number ==4){
            Intent intent = new Intent(this, ReportInfo.class);
            String RP_IDX = mapPOIItem.getUserObject().toString();

            Log.d("신고번호", RP_IDX);

            intent.putExtra("RP_IDX",RP_IDX);


            startActivity(intent);

        }


    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    public void onClick(View view) {

        switch (view.getId()){


            case R.id.main_search_btn:

                String s = input_text.getText().toString();
                Log.v("input search : ",s);

                InputMethodManager mInputMethodManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(input_text.getWindowToken(), 0);




                Intent intent = new Intent(MainActivity.this, CompanySearch.class);
                intent.putExtra("search", s);

                startActivityForResult(intent, 0);

                break;

            case R.id.gpsbutton :


              //  Log.d("gps", main.getBackground().toString());

                if (gpsNumber == 1 && mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading) {
                   // fab.setBackgroundResource(R.drawable.placeholder2);
                    fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder2));
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

                    //  Toast.makeText(this,"GPS Turn OFF", Toast.LENGTH_SHORT).show();
                    Log.d("gps", "gps 끄기");
                    gpsNumber--;

                    Snackbar.make(view, "현위치 찾기 모드를 끕니다", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();


                }
                else{
                    String gpstype = gpscheck.getLocation();
                    if(gpstype!="NOT"){

                        Log.d("gps", "gps 정보 받아오기");
                        gpstype = gpscheck.getLocation();

                        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                       // fab.setBackgroundResource(R.drawable.placeholder);
                        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.placeholder));

                       // Toast.makeText(this,"현위치를 찾는 중입니다.", Toast.LENGTH_SHORT).show();
                        gpsNumber++;

                        Snackbar.make(view, "현위치 찾기 모드를 켭니다", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();

                    }
                    else{
                        Log.d("gps", "gps 연결 안됨");
                        gpscheck.showSettingAlert();
                    }
                }


                break;

            case R.id.report_water :
                report_water.setBackgroundResource(R.drawable.border_button);
                CheckType = "WATER";
                report_air.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                report_etc.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));

                if(CheckType=="WATER") {
                    poiItemwater = mapView.getPOIItems();
                }else if(CheckType=="AIR"){
                    poiItemair = mapView.getPOIItems();
                }else if(CheckType=="ETC"){
                    poiItemetc = mapView.getPOIItems();
                }


                mapView.removeAllPOIItems();// 지도 위 마커 모두 제거

                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "mobileReport.do";
                Log.d("url : ",url);
                aq = new AQuery(this);
                HashMap<String, Object> map = new HashMap<>();
                map.put("RP_TYPE", CheckType);
                aq.ajax(url, map,JSONObject.class, this, "reportCallBack");


                break;

            case R.id.report_air :

                report_air.setBackgroundResource(R.drawable.border_button);
                CheckType = "AIR";
                report_water.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                report_etc.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));

                if(CheckType=="WATER") {
                    poiItemwater = mapView.getPOIItems();
                }else if(CheckType=="AIR"){
                    poiItemair = mapView.getPOIItems();
                }else if(CheckType=="ETC"){
                    poiItemetc = mapView.getPOIItems();
                }


                mapView.removeAllPOIItems();// 지도 위 마커 모두 제거

                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "mobileReport.do";
                Log.d("url : ",url);
                aq = new AQuery(this);
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("RP_TYPE", CheckType);
                aq.ajax(url, map2,JSONObject.class, this, "reportCallBack");


                break;

            case R.id.report_etc :

                //Log.v("dfdf,","dfdfd");
                report_etc.setBackgroundResource(R.drawable.border_button);
                CheckType = "ETC";
                report_water.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                report_air.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));

                if(CheckType=="WATER") {
                    poiItemwater = mapView.getPOIItems();
                }else if(CheckType=="AIR"){
                    poiItemair = mapView.getPOIItems();
                }else if(CheckType=="ETC"){
                    poiItemetc = mapView.getPOIItems();
                }


                mapView.removeAllPOIItems();// 지도 위 마커 모두 제거

                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "mobileReport.do";
                Log.d("url : ",url);
                aq = new AQuery(this);
                HashMap<String, Object> map3 = new HashMap<>();
                map3.put("RP_TYPE", CheckType);
                aq.ajax(url, map3,JSONObject.class, this, "reportCallBack");

                break;



        }


    }

    private boolean checkGISMap() {
        File temp1 = new File(gdbPath1);
        File temp2 = new File(gdbPath2);
        File temp3 = new File(localTileURL);
        return false;
    }

    public void showLocationDialog(final Context context, final MapPoint mapPoint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("신고");
        //loading.dismiss();
        if(report_first==true){
            builder.setMessage("현 위치에서 신고하겠습니까? 다른 위치에서 신고하려면 해당 위치의 지도를 길게 누르세요");
                report_first = false;
                call_dialog++;
        }else{
            builder.setMessage("신고하려면 'OK', 취소하려면 'CANCEL'을 누르세요");
        }


        String positiveText = "OK";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        Intent intent = new Intent(MainActivity.this, ReportForm.class);


                        double lat = valueOf(mapPoint.getMapPointGeoCoord().latitude);
                        double lng = valueOf(mapPoint.getMapPointGeoCoord().longitude);

                        intent.putExtra("latitude",lat);
                        intent.putExtra("longitude", lng);
                         intent.putExtra("RP_TYPE",CheckType);


                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        //  context.startActivity(intent);
                        startActivityForResult(intent,0);



                    }
                });

        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic


                        // fab.setBackgroundResource(R.drawable.placeholder2);
                        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.placeholder2));
                        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);

                        //  Toast.makeText(this,"GPS Turn OFF", Toast.LENGTH_SHORT).show();
                        Log.d("gps", "gps 끄기");
                        gpsNumber--;

                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    ////////추가
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.v("requestcode :",Integer.toString(requestCode));

        switch (resultCode) {
            case 1:

                String result = data.getStringExtra("APV_REG_NO");
                Log.d("APV_REG_NO", result);

                dialog = ProgressDialog.show(MainActivity.this, "", "Loading", true);

                // String url = "http://192.168.100.132:8080/mobile/searchResult.do";
                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "searchResult.do";
                Log.d("url : ",url);
                HashMap<String, Object> map = new HashMap<>();
                map.put("APV_REG_NO", result);
                aq = new AQuery(this);
                aq.ajax(url, map, JSONObject.class, this, "companySearchResult");



                break;
            case 2:

                mapView.removeAllPOIItems();


                url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                url = url + "mobileReport.do";
                Log.d("url : ",url);
                aq = new AQuery(this);
                HashMap<String, Object> map4 = new HashMap<>();
                map4.put("RP_TYPE", CheckType);
                aq.ajax(url, map4,JSONObject.class, this, "reportCallBack");



                break;

            case 3:

                onNavigationItemSelected( navigationView.getMenu().getItem(0));
                navigationView.setCheckedItem(R.id.main_button);
                Number = 1;
                Log.v("OnActivityResult :","case 3");
                //check_main_button();
                break;


            default:

                break;




        }
    }


    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());

        if(json != null){
            //json.getJSONArray("air").length()

            for(int i =0; i<json.getJSONArray("air").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("air").getJSONObject(i).get("DISCHG_WRK").toString(),json.getJSONArray("air").getJSONObject(i).get("APV_REG_NO").toString(),
                        json.getJSONArray("air").getJSONObject(i).get("APV_REG_NO").toString().substring(8, 9),
                        json.getJSONArray("air").getJSONObject(i).get("JONG_GBN_C").toString()) );

            }

            //json.getJSONArray("water").length()
            for(int i =0; i<json.getJSONArray("water").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("water").getJSONObject(i).get("DISCHG_WRK").toString(),json.getJSONArray("water").getJSONObject(i).get("APV_REG_NO").toString(),
                        json.getJSONArray("water").getJSONObject(i).get("APV_REG_NO").toString().substring(8, 9),
                        json.getJSONArray("water").getJSONObject(i).get("JONG_GBN_C").toString()) );
            }



        }else{
            Log.d("Null","Null");
        }

        //mapView.fitMapViewAreaToShowAllPOIItems();// 지도 위 마커가 모두 나타나도록 지도 확대 레벨 자동 조정

    }



    public void companySearchResult(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());
        Log.d("result",json.getJSONArray("result").toString());
        Log.d("air",json.getJSONArray("air").toString());
        Log.d("water",json.getJSONArray("water").toString());
        Log.d("check",json.getJSONArray("result").getJSONObject(0).get("JONG_GBN_C").toString());
        Log.d("Type",json.getJSONArray("result").getJSONObject(0).get("APV_REG_NO").toString().substring(8, 9));


        if(json != null){


            mapView.setMapCenterPoint((MapPoint.mapPointWithGeoCoord(valueOf(json.getJSONArray("result").getJSONObject(0).get("GIS_POINT_Y").toString()).doubleValue(),valueOf(json.getJSONArray("result").getJSONObject(0).get("GIS_POINT_X").toString()).doubleValue())), true);

            mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("result").getJSONObject(0).get("GIS_POINT_Y").toString()).doubleValue(),
                    valueOf(json.getJSONArray("result").getJSONObject(0).get("GIS_POINT_X").toString()).doubleValue(),
                    json.getJSONArray("result").getJSONObject(0).get("DISCHG_WRK").toString(),json.getJSONArray("result").getJSONObject(0).get("APV_REG_NO").toString(),
                    json.getJSONArray("result").getJSONObject(0).get("APV_REG_NO").toString().substring(8, 9),
                    json.getJSONArray("result").getJSONObject(0).get("JONG_GBN_C").toString()) );


            poiItemsMain = mapView.getPOIItems();
            int temp=0;
            while(temp < poiItemsMain.length){

                if(poiItemsMain[temp].getUserObject().toString().equals(json.getJSONArray("result").getJSONObject(0).get("APV_REG_NO").toString())){
                    break;
                }
                temp++;
            }
            if(mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading)
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
            mapView.selectPOIItem(poiItemsMain[temp], true);
            mapView.removeAllCircles();
            MapCircle circle = new MapCircle(poiItemsMain[temp].getMapPoint(),1000,    Color.argb(128, 255, 0, 0),
                    Color.argb(128, 255, 255, 0)) ;
            mapView.addCircle(circle);


            for(int i =0; i<json.getJSONArray("air").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("air").getJSONObject(i).get("DISCHG_WRK").toString(),json.getJSONArray("air").getJSONObject(i).get("APV_REG_NO").toString(),
                        json.getJSONArray("air").getJSONObject(i).get("APV_REG_NO").toString().substring(8, 9),
                        json.getJSONArray("air").getJSONObject(i).get("JONG_GBN_C").toString()) );

            }


            //json.getJSONArray("water").length()
            for(int i =0; i<json.getJSONArray("water").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("water").getJSONObject(i).get("DISCHG_WRK").toString(),json.getJSONArray("water").getJSONObject(i).get("APV_REG_NO").toString(),
                        json.getJSONArray("water").getJSONObject(i).get("APV_REG_NO").toString().substring(8, 9),
                        json.getJSONArray("water").getJSONObject(i).get("JONG_GBN_C").toString()) );
            }

        }else{

        }

//   mapView.fitMapViewAreaToShowAllPOIItems();// 지도 위 마커가 모두 나타나도록 지도 확대 레벨 자동 조정
        dialog.dismiss();

    }


    public void checkSpotCallBack(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());

        if(json != null){
            //json.getJSONArray("air").length()

            for(int i =0; i<json.getJSONArray("air").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("air").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("air").getJSONObject(i).get("STATION_NM").toString(),json.getJSONArray("air").getJSONObject(i).get("STATION_CD").toString(),
                        "air",
                        "checkspot") );

            }

            for(int i =0; i<json.getJSONArray("water").length(); i++)
            {

                if(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_Y").toString() != "null" &&json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_X").toString() != "null") {

                    mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                            valueOf(json.getJSONArray("water").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                            json.getJSONArray("water").getJSONObject(i).get("WT_POINT_NM").toString(), json.getJSONArray("water").getJSONObject(i).get("STATION_CD").toString(),
                            "water",
                            "checkspot"));
                }
            }


        }else{

        }

        mapView.fitMapViewAreaToShowAllPOIItems();// 지도 위 마커가 모두 나타나도록 지도 확대 레벨 자동 조정

    }


    public void reportCallBack(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());

        if(json != null){
            //json.getJSONArray("air").length()

            for(int i =0; i<json.getJSONArray("result").length(); i++)
            {

                mapView.addPOIItem(addmarker.getPoint(valueOf(json.getJSONArray("result").getJSONObject(i).get("GIS_POINT_Y").toString()).doubleValue(),
                        valueOf(json.getJSONArray("result").getJSONObject(i).get("GIS_POINT_X").toString()).doubleValue(),
                        json.getJSONArray("result").getJSONObject(i).get("RP_TITLE").toString(),json.getJSONArray("result").getJSONObject(i).get("RP_IDX").toString(),
                        "air",
                        "report") );

            }




        }else{

        }

       // mapView.fitMapViewAreaToShowAllPOIItems();// 지도 위 마커가 모두 나타나도록 지도 확대 레벨 자동 조정

    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(event.getAction()== KeyEvent.ACTION_DOWN&&keyCode==KeyEvent.KEYCODE_ENTER){

            String s = input_text.getText().toString();
            Log.v("input search : ",s);

            InputMethodManager mInputMethodManager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(input_text.getWindowToken(), 0);


            Intent intent = new Intent(MainActivity.this, CompanySearch.class);
            intent.putExtra("search", s);

            startActivityForResult(intent, 0);

        }

        return false;
    }
}
