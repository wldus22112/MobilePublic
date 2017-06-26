package com.example.admin.mobilepublic_20160811.CompanyInfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2016-06-01.
 */
public class InfoForm extends AppCompatActivity {

    //private AQuery aq = new AQuery(this);
    private Toolbar toolbar2;
    TextView text;
    private AQuery aq ;
    // private   ArrayList<Info> input_info = new ArrayList<Info>();

    private WIFIScan wifiScan;
    private String url;

    private TextView[] companyinfo_text;
    private LinearLayout[] companyinfo_list;
    private String APV_REG_NO;
    private TextView companyinfo_type;

    private LinearLayout type1;
    private LinearLayout type2;
    private LinearLayout type3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinfo);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        // toolbar.inflateMenu(R.menu.menu_main);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // setSupportActionBar(toolbar2);


        //title(제목) 지정
        // getSupportActionBar().setTitle("My title");

        Intent intent = getIntent();
        APV_REG_NO = intent.getStringExtra("APV_REG_NO");

        //  String url = "http://192.168.100.132:8080/mobile/companyinfo.do";

        wifiScan = new WIFIScan();
        url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
        url = url+"companyinfo.do";
        Log.d("url : ",url);

        aq = new AQuery(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("APV_REG_NO", APV_REG_NO);
        aq.ajax(url,map, JSONObject.class, this, "jsonCallback");



        double lng = (intent.getDoubleExtra("longitude",0));
        double lat = (intent.getDoubleExtra("latitude",0));

        String temp = lat +" , " + lng;


        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;
        //listview.setAdapter(adapter) ;





        companyinfo_list = new LinearLayout[7];
        companyinfo_text = new TextView[7];

        type1 = (LinearLayout)findViewById(R.id.info_type_water);
        type2 = (LinearLayout)findViewById(R.id.info_type_air);
        type3 = (LinearLayout)findViewById(R.id.info_type_etc);



        //Info info1 = new Info("주소","주소내용");
        // Info info2 = new Info("업종","주소내용");

        //input_info.add(info1);
        //  input_info.add(info2);

        // companyinfo_list[0] = (LinearLayout)findViewById(R.id.)

        //companyinfo_type = (TextView)findViewById(R.id.companyinfo_type);

        companyinfo_list[0] = (LinearLayout)findViewById(R.id.companyinfo_01);
        companyinfo_list[1] = (LinearLayout)findViewById(R.id.companyinfo_03);
        companyinfo_list[2] = (LinearLayout)findViewById(R.id.companyinfo_05);
        companyinfo_list[3] = (LinearLayout)findViewById(R.id.companyinfo_07);
        companyinfo_list[4] = (LinearLayout)findViewById(R.id.companyinfo_09);
        companyinfo_list[5] = (LinearLayout)findViewById(R.id.companyinfo_11);
        companyinfo_list[6] = (LinearLayout)findViewById(R.id.companyinfo_13);



        companyinfo_text[0] = (TextView)findViewById(R.id.companyinfo_02);
        companyinfo_text[1] = (TextView)findViewById(R.id.companyinfo_04);
        companyinfo_text[2] = (TextView)findViewById(R.id.companyinfo_06);
        companyinfo_text[3] = (TextView)findViewById(R.id.companyinfo_08);
        companyinfo_text[4] = (TextView)findViewById(R.id.companyinfo_10);
        companyinfo_text[5] = (TextView)findViewById(R.id.companyinfo_12);
        companyinfo_text[6] = (TextView)findViewById(R.id.companyinfo_14);


        for(int i =0;i<7;i++){
            companyinfo_list[i].setVisibility(View.GONE);
        }



    }


    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);

    };



    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {

        ArrayList<Info> input_info = new ArrayList<Info>();

        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());




        if(json != null) {

            type1.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
            type2.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
            type3.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));

            Log.v("NO",  APV_REG_NO.substring(8,9));


            if(  APV_REG_NO.substring(8,9).contains("1")){
               // 대기
               // companyinfo_type.setText("대기");
                type2.setBackgroundResource(R.drawable.border_button);

            }else if( APV_REG_NO.substring(8,9).contains("2")){
                type1.setBackgroundResource(R.drawable.border_button);
            } else {
              //  companyinfo_type.setText("폐수");
                type3.setBackgroundResource(R.drawable.border_button);
            }


            //사업장명, 주소, 업종, 종별(등급을 종별로 하래)
            Log.d("사업장명", json.getJSONArray("기본정보").getJSONObject(0).get("DISCHG_WRK").toString());

            // Info DISCHG_WRK = new Info("DISCHG_WRK", json.getJSONArray("기본정보").getJSONObject(0).get("DISCHG_WRK").toString());
            // input_info.add(DISCHG_WRK);
            String Company_Name = json.getJSONArray("기본정보").getJSONObject(0).get("DISCHG_WRK").toString();
            getSupportActionBar().setTitle(Company_Name);

            Log.d("주소", json.getJSONArray("기본정보").getJSONObject(0).get("WRKR_ADDR").toString());
            if(json.getJSONArray("기본정보").getJSONObject(0).get("WRKR_ADDR").toString()!="null"){
                //Info WRKR_ADDR = new Info("주소", json.getJSONArray("기본정보").getJSONObject(0).get("WRKR_ADDR").toString());
                //input_info.add(WRKR_ADDR);

                companyinfo_list[0].setVisibility(View.VISIBLE);
                companyinfo_text[0].setText( json.getJSONArray("기본정보").getJSONObject(0).get("WRKR_ADDR").toString());

            }else{
                companyinfo_list[0].setVisibility(View.GONE);
            }

            Log.d("업종", json.getJSONArray("기본정보").getJSONObject(0).get("COB_GBN_CO").toString());
            if(json.getJSONArray("기본정보").getJSONObject(0).get("COB_GBN_CO").toString()!="null"){
                // Info COB_GBN_CO = new Info("업종",json.getJSONArray("기본정보").getJSONObject(0).get("COB_GBN_CO").toString());
                //input_info.add(COB_GBN_CO);

                companyinfo_list[1].setVisibility(View.VISIBLE);
                companyinfo_text[1].setText( json.getJSONArray("기본정보").getJSONObject(0).get("COB_GBN_CO").toString());
            }else{
                companyinfo_list[1].setVisibility(View.GONE);
            }

            Log.d("종별", json.getJSONArray("기본정보").getJSONObject(0).get("JONG_GBN_C").toString());

            if(json.getJSONArray("기본정보").getJSONObject(0).get("JONG_GBN_C").toString()!="null" ){
                // Info JONG_GBN_C = new Info("종별", json.getJSONArray("기본정보").getJSONObject(0).get("JONG_GBN_C").toString());
                //input_info.add(JONG_GBN_C);

                companyinfo_list[2].setVisibility(View.VISIBLE);
                companyinfo_text[2].setText( json.getJSONArray("기본정보").getJSONObject(0).get("JONG_GBN_C").toString());
            }else{
                companyinfo_list[2].setVisibility(View.GONE);
            }

            //원료 및 사용량. 원료는 여러개 일 수 있음, null일수도있음
            if (json.getJSONArray("원료및사용량").length() != 0) {
                String temp1 = "";

                for (int i = 0; i < json.getJSONArray("원료및사용량").length(); i++) {

                    if(temp1.length()!=0){


                        if(json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString().contains("null") ){

                        }
                        else{

                            Log.d("원료명", json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_NM").toString());
                            temp1 = temp1 +", " +  json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_NM").toString() ;

                            if(json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString()!="null" ||json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString()!="0"){
                                Log.d("년평균사용량", json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString());
                                temp1 = temp1  + " (" +json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString();
                            }
                            if(json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString()!="null" ){
                                Log.d("단위", json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString());
                                temp1 = temp1   +json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString() +")";
                            }

                        }


                    }else{

                        if(json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString().contains("null") ){

                        }
                        else {


                            if(json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_NM").toString()!="null" ){
                                Log.d("원료명", json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_NM").toString());
                                temp1 = temp1 +  json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_NM").toString() ;
                            }
                            if(json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString()!="null" ||json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString()!="0"){
                                Log.d("년평균사용량", json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString());
                                temp1 = temp1 + " (" + json.getJSONArray("원료및사용량").getJSONObject(i).get("YEAR_AVG_USE_QUA").toString();
                            }
                            if(json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString()!="null" ){
                                Log.d("단위", json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString());
                                temp1 = temp1+ json.getJSONArray("원료및사용량").getJSONObject(i).get("RM_USE_QUA_UNT_CODE_NM").toString() +")";
                            }

                        }


                    }



                }
                // Info MATERIAL = new Info("원료및사용량",temp1);
                //input_info.add(MATERIAL);
                companyinfo_list[3].setVisibility(View.VISIBLE);

                companyinfo_text[3].setText( temp1);
            }else{
                companyinfo_list[3].setVisibility(View.GONE);
            }

            //용수사용량, 폐수발생량 관련 디비없음음
            //오염물질
            if (json.getJSONArray("오염물질").length() != 0) {
                String temp2 = "";

                for (int i = 0; i < json.getJSONArray("오염물질").length(); i++) {

                    if(json.getJSONArray("오염물질").getJSONObject(i).get("POLLT_MATRL_CODE_NM").toString()!="null" ){
                        Log.d("오염물질", json.getJSONArray("오염물질").getJSONObject(i).get("POLLT_MATRL_CODE_NM").toString());
                        temp2 = temp2 + json.getJSONArray("오염물질").getJSONObject(i).get("POLLT_MATRL_CODE_NM").toString()+", ";
                    }



                }
                // Info POLLUTANT =  new Info("오염물질", temp2);
                //input_info.add(POLLUTANT);

                companyinfo_list[4].setVisibility(View.VISIBLE);
                companyinfo_text[4].setText(temp2);
            }else{
                companyinfo_list[4].setVisibility(View.GONE);
            }

            //점검이력은 뭘띄울지 몰라서 일단 날짜만 받아옴
            if (json.getJSONArray("점검이력").length() != 0) {
                String temp3 ="";
                for (int i = 0; i < json.getJSONArray("점검이력").length(); i++) {
                    Log.d("점검이력", json.getJSONArray("점검이력").getJSONObject(i).get("REGI_DT").toString());
                    temp3 = temp3 + json.getJSONArray("점검이력").getJSONObject(i).get("REGI_DT").toString() +" \n";
                }
                // Info CHECK = new Info("점검이력",temp3);
                //input_info.add(CHECK);
                companyinfo_list[5].setVisibility(View.VISIBLE);

                companyinfo_text[5].setText(temp3);
            }else{
                companyinfo_list[5].setVisibility(View.GONE);
            }

            //행정처분 이력력 - 이것도 제대로된 디비가 없음
            if (json.getJSONArray("행정처분이력").length() != 0) {
                String temp4 = "";
                for (int i = 0; i < json.getJSONArray("행정처분이력").length(); i++) {
                    Log.d("위반내역", json.getJSONArray("행정처분이력").getJSONObject(i).get("VIOL_CONTEN ").toString());
                    temp4 = temp4 +  json.getJSONArray("행정처분이력").getJSONObject(i).get("VIOL_CONTEN ").toString() + ",";
                    Log.d("위반일", json.getJSONArray("행정처분이력").getJSONObject(i).get("VIOL_DATE").toString());
                    temp4 = temp4 +   json.getJSONArray("행정처분이력").getJSONObject(i).get("VIOL_DATE").toString() + ",";
                    Log.d("처분기간", json.getJSONArray("행정처분이력").getJSONObject(i).get("DISP_DATE").toString());
                    temp4 = temp4 + json.getJSONArray("행정처분이력").getJSONObject(i).get("DISP_DATE").toString() ;
                }

                Info RECORD = new Info("행정처분 이력력",temp4);
                input_info.add(RECORD);
                companyinfo_list[6].setVisibility(View.VISIBLE);

                companyinfo_text[6].setText(temp4);
            }else{
                companyinfo_list[6].setVisibility(View.GONE);
            }
        }

        else{
            Log.d("Null","Null");
        }

        // MyAdapter myAdapter = new MyAdapter(this,R.layout.info_view,input_info);
        //listview.setAdapter(myAdapter);


    }




}