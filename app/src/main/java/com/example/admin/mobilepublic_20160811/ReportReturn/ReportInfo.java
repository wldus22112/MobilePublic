package com.example.admin.mobilepublic_20160811.ReportReturn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016-06-20.
 */
public class ReportInfo extends AppCompatActivity {

    private TextView input1;
    private TextView input2;
    private  TextView input3;
    private TextView input4;
    private ImageView imgview;
    private WIFIScan wifiScan;
    private AQuery aq ;
    private String url;

    private ProgressDialog dialog;

    private LinearLayout tab1;
    private LinearLayout tab2;
    private LinearLayout tab3;
    private TextView state;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_info);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();


        String RP_IDX = intent.getStringExtra("RP_IDX");
        getSupportActionBar().setTitle("신고정보");



        input1 = (TextView)findViewById(R.id.report_info_title);
        input2 = (TextView)findViewById(R.id.report_info_date);
        input3 = (TextView)findViewById(R.id.report_info_addr);
        input4 = (TextView)findViewById(R.id.report_info_content);
        state = (TextView)findViewById(R.id.report_info_state);

        tab1 = (LinearLayout)findViewById(R.id.report_type_water);
        tab2 = (LinearLayout)findViewById(R.id.report_type_air);
        tab3 = (LinearLayout)findViewById(R.id.report_type_etc);


        //Button buttonCamera = (Button) findViewById(R.id.camera);
        //Button buttonGallery = (Button) findViewById(R.id.gallery);
        HashMap<String, Object> map = new HashMap<>();
        map.put("RP_IDX",RP_IDX);
        wifiScan = new WIFIScan();



        url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
        url = url + "mobileReportInfo.do";
        Log.d("주소",url.toString());

        dialog = ProgressDialog.show(ReportInfo.this, "",
                "로딩 중입니다. 잠시 기다려주세요", true);
        aq = new AQuery(this);
        aq.ajax(url, map, JSONObject.class, this, "jsonCallback");



        imgview = (ImageView) findViewById(R.id.report_info_Image);

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
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());

        if(json != null){

            String type = json.getJSONArray("result").getJSONObject(0).get("RP_TYPE").toString();
            Log.v("Type :",type);

            if(type.contains("WATER")){
                tab1.setBackgroundResource(R.drawable.border_button);
            }
            else if(type.contains("AIR")){
                tab2.setBackgroundResource(R.drawable.border_button);
            }
            else{
                tab3.setBackgroundResource(R.drawable.border_button);
            }

            input1.setText(json.getJSONArray("result").getJSONObject(0).get("RP_TITLE").toString());
            input2.setText( json.getJSONArray("result").getJSONObject(0).get("RP_DATE").toString());
            input3.setText( json.getJSONArray("result").getJSONObject(0).get("RP_ADDR").toString());
            input4.setText( json.getJSONArray("result").getJSONObject(0).get("RP_CONTENT").toString());

            if(json.getJSONArray("result").getJSONObject(0).get("RP_STATE").toString().contains("1")){
                state.setText("      접수중");
            }else if(json.getJSONArray("result").getJSONObject(0).get("RP_STATE").toString().contains("2")){
                state.setText("      점수 완료");
            }else if(json.getJSONArray("result").getJSONObject(0).get("RP_STATE").toString().contains("3")){
                state.setText("      처리중");
            }else if(json.getJSONArray("result").getJSONObject(0).get("RP_STATE").toString().contains("4")){
                state.setText("      처리완료");
            }else{
                state.setText("      접수중");
            }


            String serverUrl;
            String imgName;
            String imgUrl;
            serverUrl =   wifiScan.checkWIFI(wifiScan.getWIFISSID(this)).substring(0,28)+"upload/report/";
            imgName = json.getJSONArray("result").getJSONObject(0).get("RP_PHOTOPATH").toString();

           imgUrl = serverUrl + imgName;
           Log.d("이미지경로", imgUrl);

           // aq.id(R.id.report_info_Image).image(imgUrl);

            imgview.setScaleType(ImageView.ScaleType.FIT_CENTER);

            Glide.with(this).load(imgUrl).into(new GlideDrawableImageViewTarget(imgview));






        }else{
            Log.d("Null","Null");
        }
        dialog.dismiss();

    }

}
