package com.example.admin.mobilepublic_20160811.CheckSpotChart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016-06-10.
 */
public class CheckSpot  extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    //private AQuery aq = new AQuery(this);
    private Toolbar toolbar2;

    private AQuery aq ;
    // private   ArrayList<Info> input_info = new ArrayList<Info>();
    private ListView listview;
    private WIFIScan wifiScan;
    private String url = null;
    private LineChart lineChart;
    private ArrayList<String> legendList;
    private Legend legend;
    private TextView text;
    private Legend_temp legend_temp;
    private ListView list123;
    private ProgressDialog dialog;
    private CheckBox[] checkbox_air;
    private CheckBox[] checkbox_wtr;


    private LineDataSet dataset;
    private  LineDataSet dataset2;
    private LineDataSet dataset3;
    private LineDataSet dataset4;
    private  LineDataSet dataset5 ;
    private   LineDataSet dataset6 ;

    private LineDataSet[] airDataset;
    private LineDataSet[] wtrDataset;

    private    ArrayList<LineDataSet> linelist;
    private  ArrayList<LineDataSet> linelist_delete;

    private    ArrayList<LineDataSet> linelist_wtr;
    private  ArrayList<LineDataSet> linelist_delete_wtr;

    private LineData lineData_result;

    private   String[] date;
    private      ArrayList<String> date_wtr;
    private String data_Type = null;

    private LinearLayout air_check;
    private LinearLayout wtr_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_spot_graph);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Toast.makeText(this,"잠시 기다려주세요", Toast.LENGTH_SHORT).show();



        Intent intent = getIntent();
        String STATION_CD = intent.getStringExtra("STATION_CD");
        Log.d("STATION_CD", STATION_CD);


        wifiScan = new WIFIScan();
        url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
        url = url+"checkSpotGraphInfo.do";
        Log.d("url : ",url);


        //String url = "http://192.168.100.132:8080/mobile/checkSpotGraphInfo.do";
        dialog = ProgressDialog.show(CheckSpot.this, "",
                "로딩 중입니다. 잠시 기다려주세요", true);
        aq = new AQuery(this);
        HashMap<String, Object> map = new HashMap<>();
        map.put("STATION_CD", STATION_CD);

        aq.ajax(url, map, JSONObject.class, this, "jsonCallback");


        double lng = (intent.getDoubleExtra("longitude",0));
        double lat = (intent.getDoubleExtra("latitude",0));
        String temp = lat +" , " + lng;

        lineChart = (LineChart) findViewById(R.id.chart);

        ;


        //legendList = new ArrayList<String>();

        //ArrayAdapter<String> Adapter;
        // Adapter = new ArrayAdapter<String>(this,R.layout.chart_legend_text,legendList);

        //list123 = (ListView)findViewById(R.id.list123);
        //  list.setAdapter(Adapter);



        //  text = (TextView)findViewById(R.id.textView);

        //list123.setOnItemClickListener(listener);

        air_check = (LinearLayout)findViewById(R.id.checkspot_air);
        wtr_check = (LinearLayout)findViewById(R.id.checkspot_wtr);

        air_check.setVisibility(View.GONE);
        wtr_check.setVisibility(View.GONE);

        checkbox_air = new CheckBox[6];
        checkbox_air[0] = (CheckBox)findViewById(R.id.check_air01);
        checkbox_air[1] = (CheckBox)findViewById(R.id.check_air02);
        checkbox_air[2] = (CheckBox)findViewById(R.id.check_air03);
        checkbox_air[3] = (CheckBox)findViewById(R.id.check_air04);
        checkbox_air[4] = (CheckBox)findViewById(R.id.check_air05);
        checkbox_air[5] = (CheckBox)findViewById(R.id.check_air06);

        checkbox_wtr = new CheckBox[34];

        checkbox_wtr[0] = (CheckBox)findViewById(R.id.check_wtr01);
        checkbox_wtr[1] = (CheckBox)findViewById(R.id.check_wtr02);
        checkbox_wtr[2] = (CheckBox)findViewById(R.id.check_wtr03);
        checkbox_wtr[3] = (CheckBox)findViewById(R.id.check_wtr04);
        checkbox_wtr[4] = (CheckBox)findViewById(R.id.check_wtr05);
        checkbox_wtr[5] = (CheckBox)findViewById(R.id.check_wtr06);
        checkbox_wtr[6] = (CheckBox)findViewById(R.id.check_wtr07);
        checkbox_wtr[7] = (CheckBox)findViewById(R.id.check_wtr08);
        checkbox_wtr[8] = (CheckBox)findViewById(R.id.check_wtr09);
        checkbox_wtr[9] = (CheckBox)findViewById(R.id.check_wtr10);
        checkbox_wtr[10] = (CheckBox)findViewById(R.id.check_wtr11);
        checkbox_wtr[11] = (CheckBox)findViewById(R.id.check_wtr12);
        checkbox_wtr[12] = (CheckBox)findViewById(R.id.check_wtr13);
        checkbox_wtr[13] = (CheckBox)findViewById(R.id.check_wtr14);
        checkbox_wtr[14] = (CheckBox)findViewById(R.id.check_wtr15);
        checkbox_wtr[15] = (CheckBox)findViewById(R.id.check_wtr16);
        checkbox_wtr[16] = (CheckBox)findViewById(R.id.check_wtr17);
        checkbox_wtr[17] = (CheckBox)findViewById(R.id.check_wtr18);
        checkbox_wtr[18] = (CheckBox)findViewById(R.id.check_wtr19);
        checkbox_wtr[19] = (CheckBox)findViewById(R.id.check_wtr20);
        checkbox_wtr[20] = (CheckBox)findViewById(R.id.check_wtr21);
        checkbox_wtr[21] = (CheckBox)findViewById(R.id.check_wtr22);
        checkbox_wtr[22] = (CheckBox)findViewById(R.id.check_wtr23);
        checkbox_wtr[23] = (CheckBox)findViewById(R.id.check_wtr24);
        checkbox_wtr[24] = (CheckBox)findViewById(R.id.check_wtr25);
        checkbox_wtr[25] = (CheckBox)findViewById(R.id.check_wtr26);
        checkbox_wtr[26] = (CheckBox)findViewById(R.id.check_wtr27);
        checkbox_wtr[27] = (CheckBox)findViewById(R.id.check_wtr28);
        checkbox_wtr[28] = (CheckBox)findViewById(R.id.check_wtr29);
        checkbox_wtr[29] = (CheckBox)findViewById(R.id.check_wtr30);
        checkbox_wtr[30] = (CheckBox)findViewById(R.id.check_wtr31);
        checkbox_wtr[31] = (CheckBox)findViewById(R.id.check_wtr32);
        checkbox_wtr[32] = (CheckBox)findViewById(R.id.check_wtr33);
        checkbox_wtr[33] = (CheckBox)findViewById(R.id.check_wtr34);



        for(int i =0;i<checkbox_air.length;i++){
            checkbox_air[i].setOnCheckedChangeListener(this);
        }

        for(int i =0;i<checkbox_wtr.length;i++){
            checkbox_wtr[i].setOnCheckedChangeListener(this);
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


    private void addChartDataSet( LineDataSet input,String type){



        if(type=="Wtr"){
            linelist_wtr.add(input);

            for(int i =0;i<linelist_wtr.size();i++){
                linelist_wtr.get(i).setDrawCircles(false);
            }

            lineChart.setData(new LineData(date_wtr,linelist_wtr));
            lineChart.invalidate();
        }else{
            linelist.add(input);
            lineChart.setData(new LineData(date,linelist));
            lineChart.invalidate();
        }

    }

    private void removeChartDataSet( LineDataSet output,String type){

        if(type=="Wtr"){

            linelist_delete_wtr.add(output);
            linelist_wtr.removeAll(linelist_delete_wtr);

            for(int i =0;i<linelist_wtr.size();i++){
                linelist_wtr.get(i).setDrawCircles(false);
            }


            lineChart.setData(new LineData(date_wtr, linelist_wtr));
            lineChart.invalidate();




        }else{
            linelist_delete.add(output);
            linelist.removeAll(linelist_delete);
            lineChart.setData(new LineData(date, linelist));
            lineChart.invalidate();
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        linelist_delete = new ArrayList<LineDataSet>();
        linelist_delete_wtr = new ArrayList<LineDataSet>();



        switch (buttonView.getId()) {
            case R.id.check_air01:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( airDataset[0],"air");
                } else {
                    removeChartDataSet( airDataset[0],"air");
                }
                break;

            case R.id.check_air02:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( airDataset[1],"air");
                } else {
                    removeChartDataSet( airDataset[1],"air");
                }
                break;

            case R.id.check_air03:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( airDataset[2],"air");
                } else {
                    removeChartDataSet( airDataset[2],"air");
                }
                break;

            case R.id.check_air04:
                if (isChecked) {
                    addChartDataSet( airDataset[3],"air");
                } else {
                    removeChartDataSet( airDataset[3],"air");
                }
                break;
            case R.id.check_air05:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( airDataset[4],"air");
                } else {
                    removeChartDataSet( airDataset[4],"air");
                }
                break;


            case R.id.check_wtr01:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet(  wtrDataset[0],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[0],"Wtr");
                }
                break;

            case R.id.check_wtr02:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[1],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[1],"Wtr");
                }
                break;
            case R.id.check_wtr03:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[2],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[2],"Wtr");
                }
                break;
            case R.id.check_wtr04:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[3],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[3],"Wtr");
                }
                break;
            case R.id.check_wtr05:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[4],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[4],"Wtr");
                }
                break;
            case R.id.check_wtr06:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[5],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[5],"Wtr");
                }
                break;
            case R.id.check_wtr07:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[6],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[6],"Wtr");
                }
                break;
            case R.id.check_wtr08:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[7],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[7],"Wtr");
                }
                break;
            case R.id.check_wtr09:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[8],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[8],"Wtr");
                }
                break;
            case R.id.check_wtr10:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[9],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[9],"Wtr");
                }
                break;
            case R.id.check_wtr11:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[10],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[10],"Wtr");
                }
                break;
            case R.id.check_wtr12:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[11],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[11],"Wtr");
                }
                break;
            case R.id.check_wtr13:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[12],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[12],"Wtr");
                }
                break;
            case R.id.check_wtr14:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[13],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[13],"Wtr");
                }
                break;
            case R.id.check_wtr15:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[14],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[14],"Wtr");
                }
                break;
            case R.id.check_wtr16:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[15],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[15],"Wtr");
                }
                break;
            case R.id.check_wtr17:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[16],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[16],"Wtr");
                }
                break;
            case R.id.check_wtr18:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[17],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[17],"Wtr");
                }
                break;
            case R.id.check_wtr19:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[18],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[18],"Wtr");
                }
                break;
            case R.id.check_wtr20:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[19],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[19],"Wtr");
                }
                break;
            case R.id.check_wtr21:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[20],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[20],"Wtr");
                }
                break;
            case R.id.check_wtr22:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[21],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[21],"Wtr");
                }
                break;
            case R.id.check_wtr23:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[22],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[22],"Wtr");
                }
                break;
            case R.id.check_wtr24:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[23],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[23],"Wtr");
                }
                break;
            case R.id.check_wtr25:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[24],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[24],"Wtr");
                }
                break;
            case R.id.check_wtr26:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[25],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[25],"Wtr");
                }
                break;
            case R.id.check_wtr27:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[26],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[26],"Wtr");
                }
                break;
            case R.id.check_wtr28:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[27],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[27],"Wtr");
                }
                break;
            case R.id.check_wtr29:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[28],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[28],"Wtr");
                }
                break;
            case R.id.check_wtr30:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[29],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[29],"Wtr");
                }
                break;
            case R.id.check_wtr31:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[30],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[30],"Wtr");
                }
                break;
            case R.id.check_wtr32:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[31],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[31],"Wtr");
                }
                break;
            case R.id.check_wtr33:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[32],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[32],"Wtr");
                }
                break;
            case R.id.check_wtr34:
                if (isChecked) {
                    Log.v("onCheckedChanged :", "isChecked");
                    addChartDataSet( wtrDataset[33],"Wtr");
                } else {
                    removeChartDataSet( wtrDataset[33],"Wtr");
                }
                break;


        }

    }


    private class MyAdapter extends ArrayAdapter<ChartLegend> {

        private ArrayList<ChartLegend> items;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<ChartLegend> items){
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View covertView, ViewGroup parent){

            View v = covertView;
            if(v==null){
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.chart_legend_text,null);

            }

            ChartLegend info = items.get(position);

            if(info != null){
                TextView textView1 = (TextView) v.findViewById(R.id.legend);
                TextView textView2 = (TextView)v.findViewById(R.id.legend2);
                if(textView1 !=null){
                    textView1.setText("■");
                    textView1.setTextColor(info.ge_tColor());
                }
                if(textView2!=null){
                    textView2.setText(info.get_legendName());

                }
            }

            return v;
        }

    }


    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {



        //ListView의 아이템 중 하나가 클릭될 때 호출되는 메소드

        //첫번째 파라미터 : 클릭된 아이템을 보여주고 있는 AdapterView 객체(여기서는 ListView객체)

        //두번째 파라미터 : 클릭된 아이템 뷰

        //세번째 파라미터 : 클릭된 아이템의 위치(ListView이 첫번째 아이템(가장위쪽)부터 차례대로 0,1,2,3.....)

        //네번재 파리미터 : 클릭된 아이템의 아이디(특별한 설정이 없다면 세번째 파라이터인 position과 같은 값)

        @Override

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // TODO Auto-generated method stub



            //클릭된 아이템의 위치를 이용하여 데이터인 문자열을 Toast로 출력



        }

    };


    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException,InvocationTargetException {

        ArrayList<ChartLegend> input_info = new ArrayList<ChartLegend>();
        Log.d("url : ", url);
        Log.d("json", json.toString());
        Log.d("status", status.toString());
        if(json != null) {

            Log.d("wtr", json.toString().substring(2, 5));
            //    Log.d("AIR", json.getJSONArray("Air").toString());
            //수질이면
            if(json.toString().substring(2, 5).equals("Air")){

                Log.d("AIR","AIR");
                data_Type = "AIR";
                air_check.setVisibility(View.VISIBLE);

                ArrayList<Entry> val_so2 = new ArrayList<>();
                ArrayList<Entry> val_no2 = new ArrayList<>();
                ArrayList<Entry> val_o3 = new ArrayList<>();
                ArrayList<Entry> val_co = new ArrayList<>();
                ArrayList<Entry> val_pm10 = new ArrayList<>();
                ArrayList<Entry> val_pm25 = new ArrayList<>();
                date = new String[json.getJSONArray("Air").length()];
                String year = "";
                String month = "";

                for(int i=0; i<json.getJSONArray("Air").length(); i++){
                    year = json.getJSONArray("Air").getJSONObject(i).get("MS_YEAR").toString();
                    month = json.getJSONArray("Air").getJSONObject(i).get("MS_MONTH").toString();
                    date[i] = year + "."+month;

                    if ((json.getJSONArray("Air").getJSONObject(i).get("VAL_SO2").toString())!="null") {
                        val_so2.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_SO2").toString()),i));
                    }
                    if ((json.getJSONArray("Air").getJSONObject(i).get("VAL_NO2").toString())!="null") {
                        val_no2.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_NO2").toString()),i));
                    }
                    if (json.getJSONArray("Air").getJSONObject(i).get("VAL_O3").toString()!="null") {
                        val_o3.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_O3").toString()),i));
                    }
                    if ((json.getJSONArray("Air").getJSONObject(i).get("VAL_CO").toString())!="null") {
                        val_co.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_CO").toString()),i));
                    }
                    if ((json.getJSONArray("Air").getJSONObject(i).get("VAL_PM10").toString())!="null") {
                        val_pm10.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_PM10").toString()),i));
                    }
                    if ((json.getJSONArray("Air").getJSONObject(i).get("VAL_PM25").toString())!="null") {
                        val_pm25.add(new Entry(Float.parseFloat(json.getJSONArray("Air").getJSONObject(i).get("VAL_PM25").toString()),i));
                    }
                }



                /*
                dataset = new LineDataSet(val_so2, "아황산가스");
                dataset2 = new LineDataSet(val_no2, "이산화질소");
                dataset3 = new LineDataSet(val_o3, "오존");
                dataset4 = new LineDataSet(val_co, "일산화탄소");
                dataset5 = new LineDataSet(val_pm10, "먼지10");
                dataset6 = new LineDataSet(val_pm25, "먼지2.5");
*/
                airDataset = new LineDataSet[6];



                airDataset[0] = new LineDataSet(val_so2, "아황산가스");
                airDataset[1] = new LineDataSet(val_no2, "이산화질소");
                airDataset[2] = new LineDataSet(val_o3, "오존");
                airDataset[3] = new LineDataSet(val_co, "일산화탄소");
                airDataset[4] = new LineDataSet(val_pm10, "먼지10");
                airDataset[5] = new LineDataSet(val_pm25, "먼지2.5");

                linelist = new ArrayList<LineDataSet>();

                airDataset[0].setColor(Color.parseColor("#FF0000"));
                airDataset[1].setColor(Color.parseColor("#FF5E00"));
                airDataset[2].setColor(Color.parseColor("#FFBB00"));
                airDataset[3].setColor(Color.parseColor("#1DDB16"));
                airDataset[4].setColor(Color.parseColor("#00D8FF"));
                airDataset[5].setColor(Color.parseColor("#00D8FF"));

                //linelist.add(dataset);
                //linelist.add(dataset2);
                //linelist.add(dataset3);
                //linelist.add(dataset4);
                //linelist.add(dataset5);
                //linelist.add(dataset6);



                legend = lineChart.getLegend();

                legend.setEnabled(false);




                Log.d("callAir","callAir");

                /*

                ChartLegend legendval_so2 = new ChartLegend(Color.RED,"아황산가스");
                input_info.add(legendval_so2);

                ChartLegend legendval_so2val_no2 = new ChartLegend(Color.YELLOW,"이산화질소");
                input_info.add(legendval_so2val_no2);

                ChartLegend legendval_so2val_o3 = new ChartLegend(Color.BLUE,"오존");
                input_info.add(legendval_so2val_o3);

                ChartLegend legendval_so2val_co = new ChartLegend(Color.GREEN,"일산화탄소");
                input_info.add(legendval_so2val_co);

                ChartLegend legendval_so2val_pm10 = new ChartLegend(Color.CYAN,"먼지10");
                input_info.add(legendval_so2val_pm10);

                ChartLegend legendval_so2val_pm25 = new ChartLegend(Color.DKGRAY,"먼지2");
                input_info.add(legendval_so2val_pm25);


*/
                //MyAdapter myAdapter = new MyAdapter(this,R.layout.chart_legend_text,input_info);
                //list123.setAdapter(myAdapter);



                lineChart.setData(new LineData(date, linelist));
                lineChart.animateY(2000);



            }
            else{

                Log.d("Wtr","Wtr");

                data_Type = "Wtr";
                wtr_check.setVisibility(View.VISIBLE);

                ArrayList<Entry> wtr_p = new ArrayList<>();
                ArrayList<Entry> wtr_o2 = new ArrayList<>();
                ArrayList<Entry> wtr_n = new ArrayList<>();
                ArrayList<Entry> trichlro_et = new ArrayList<>();
                ArrayList<Entry> totla_p = new ArrayList<>();
                ArrayList<Entry> totla_n = new ArrayList<>();
                ArrayList<Entry> toc = new ArrayList<>();
                ArrayList<Entry> tetrachlo_et = new ArrayList<>();
                ArrayList<Entry> suspend_solid = new ArrayList<>();
                ArrayList<Entry> surfactant = new ArrayList<>();
                ArrayList<Entry> po4_p = new ArrayList<>();
                ArrayList<Entry> phenol = new ArrayList<>();
                ArrayList<Entry> pcb = new ArrayList<>();
                ArrayList<Entry> pb = new ArrayList<>();
                ArrayList<Entry> organic_p = new ArrayList<>();
                ArrayList<Entry> no3_n = new ArrayList<>();
                ArrayList<Entry> nh3_n = new ArrayList<>();
                ArrayList<Entry> hg = new ArrayList<>();
                ArrayList<Entry> hexa_benzene = new ArrayList<>();
                ArrayList<Entry> formaldehyde = new ArrayList<>();
                ArrayList<Entry> edc = new ArrayList<>();
                ArrayList<Entry> dioxane = new ArrayList<>();
                ArrayList<Entry> dichloro_mt = new ArrayList<>();
                ArrayList<Entry> dehp = new ArrayList<>();
                ArrayList<Entry> cyan = new ArrayList<>();
                ArrayList<Entry> cod = new ArrayList<>();
                ArrayList<Entry> chrome = new ArrayList<>();
                ArrayList<Entry> chlorophyll = new ArrayList<>();
                ArrayList<Entry> cd = new ArrayList<>();
                ArrayList<Entry> ccl4 = new ArrayList<>();
                ArrayList<Entry> bod = new ArrayList<>();
                ArrayList<Entry> benzene = new ArrayList<>();
                ArrayList<Entry> as = new ArrayList<>();
                ArrayList<Entry> antimon = new ArrayList<>();


               date_wtr = new ArrayList<>();

                // String[] date = new String[json.getJSONArray("Wtr").length()];
                String year = "";
                String month = "";


                JSONArray jsonArr = new JSONArray();
                jsonArr = json.getJSONArray("Wtr");
                JSONArray sortedJsonArray = new JSONArray();

                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for (int i = 0; i < jsonArr.length(); i++) {
                    jsonValues.add(jsonArr.getJSONObject(i));
                }
                Collections.sort( jsonValues, new Comparator<JSONObject>() {
                    //You can change "Name" with "ID" if you want to sort by ID
                    private static final String KEY_NAME = "MEASURE_DATE";

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try {
                            valA = (String) a.get(KEY_NAME);
                            valB = (String) b.get(KEY_NAME);
                        }
                        catch (JSONException e) {
                            //do something
                        }

                        return valA.compareTo(valB);
                        //if you want to change the sort order, simply use the following:
                        //return -valA.compareTo(valB);
                    }
                });

                for (int i = 0; i < jsonArr.length(); i++) {
                    sortedJsonArray.put(jsonValues.get(i));
                    //Log.v("sort",jsonValues.get(i).toString());
                }


                for(int i=0; i<sortedJsonArray.length(); i++){


                    //  date[i] =json.getJSONArray("Wtr").getJSONObject(i).get("MEASURE_DATE").toString();
                    date_wtr.add(sortedJsonArray.getJSONObject(i).get("MEASURE_DATE").toString());
                    // Log.v("MEASURE_DATE :",sortedJsonArray.getJSONObject(i).get("MEASURE_DATE").toString());



                    if ((sortedJsonArray.getJSONObject(i).get("WTR_P").toString())!="null") {
                        wtr_p.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("WTR_P").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("WTR_O2").toString()!="null"){
                        wtr_o2.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("WTR_O2").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("WTR_N").toString())!="null") {
                        wtr_n.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("WTR_N").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("TRICHLRO_ET").toString()!="null"){
                        trichlro_et.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("TRICHLRO_ET").toString()),i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("TOTAL_P").toString())!="null") {
                        totla_p.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("TOTAL_P").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("TOTAL_N").toString()!="null"){
                        totla_n.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("TOTAL_N").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("TOC").toString())!="null") {
                        toc.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("TOC").toString()),i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("TETRACHLO_ET").toString()!="null"){
                        tetrachlo_et.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("TETRACHLO_ET").toString()),i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("SUSPEND_SOLID").toString())!="null") {
                        suspend_solid.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("SUSPEND_SOLID").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("SURFACTANT").toString()!="null"){
                        surfactant.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("SURFACTANT").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("PO4_P").toString())!="null") {
                        po4_p.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("PO4_P").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("PHENOL").toString()!="null"){
                        phenol.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("PHENOL").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("PCB").toString())!="null") {
                        pcb.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("PCB").toString()),i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("PB").toString()!="null"){
                        pb.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("PB").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("ORGANIC_P").toString())!="null") {
                        organic_p.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("ORGANIC_P").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("NO3_N").toString()!="null"){
                        no3_n.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("NO3_N").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("NH3_N").toString())!="null") {
                        nh3_n.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("NH3_N").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("HG").toString()!="null"){
                        hg.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("HG").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("HEXA_BENZENE").toString())!="null") {
                        hexa_benzene.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("HEXA_BENZENE").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("FORMALDEHYDE").toString()!="null"){
                        formaldehyde.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("FORMALDEHYDE").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("EDC").toString())!="null") {
                        edc.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("EDC").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("DIOXANE").toString()!="null"){
                        dioxane.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("DIOXANE").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("DICHLORO_MT").toString())!="null") {
                        dichloro_mt.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("DICHLORO_MT").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("DEHP").toString()!="null"){
                        dehp.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("DEHP").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("CYAN").toString()!="null"){
                        cyan.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("CYAN").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("COD").toString())!="null") {
                        cod.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("COD").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("CHROME").toString()!="null"){
                        chrome.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("CHROME").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("CHLOROPHYLL").toString())!="null") {
                        chlorophyll.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("CHLOROPHYLL").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("CD").toString()!="null"){
                        cd.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("CD").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("CCL4").toString()!="null"){
                        ccl4.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("CCL4").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("BOD").toString()!="null"){
                        bod.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("BOD").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("BENZENE").toString())!="null") {
                        benzene.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("BENZENE").toString()), i));
                    }
                    if(sortedJsonArray.getJSONObject(i).get("AS").toString()!="null"){
                        as.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("AS").toString()), i));
                    }
                    if ((sortedJsonArray.getJSONObject(i).get("ANTIMON").toString())!="null") {
                        antimon.add(new Entry(Float.parseFloat(sortedJsonArray.getJSONObject(i).get("ANTIMON").toString()), i));
                    }


                }


                wtrDataset = new LineDataSet[34];

                wtrDataset[0]  = new LineDataSet(wtr_p, "용존총인(mg/L)");
                wtrDataset[1]  = new LineDataSet(wtr_o2, "용존산소(mg/L)");
                wtrDataset[2] = new LineDataSet(wtr_n, "용존총질소(mg/L)");
                wtrDataset[3] = new LineDataSet(trichlro_et, "트리클로로에틸렌(mg/L)");
                wtrDataset[4]  = new LineDataSet(totla_p, "총인(mg/L)");
                wtrDataset[5]  = new LineDataSet(totla_n, "총질소(mg/L)");
                wtrDataset[6]  = new LineDataSet(toc, "TOC(mg/L)");
                wtrDataset[7]  = new LineDataSet(tetrachlo_et, "테트라클로로에틸렌(mg/L)");
                wtrDataset[8] = new LineDataSet(suspend_solid, "부유물질(mg/L)");
                wtrDataset[9]  = new LineDataSet(surfactant, "음이온계면활성제(mg/L)");
                wtrDataset[10]  = new LineDataSet(po4_p, "인산염인(mg/L)");
                wtrDataset[11]  = new LineDataSet(phenol, "페놀");
                wtrDataset[12]  = new LineDataSet(pcb, "PCB(mg/L)");
                wtrDataset[13]  = new LineDataSet(pb, "납(mg/L)");
                wtrDataset[14]  = new LineDataSet(organic_p, "유기인(mg/L)");
                wtrDataset[15] = new LineDataSet(no3_n, "질산성질소(mg/L)");
                wtrDataset[16]  = new LineDataSet(nh3_n, "암모니아성질소(mg/L)");
                wtrDataset[17]  = new LineDataSet(hg, "수은(mg/L)");
                wtrDataset[18]  = new LineDataSet(hexa_benzene, "헥사클로로벤젠(mg/L)");
                wtrDataset[19]  = new LineDataSet(formaldehyde, "포름알데히드(mg/L)");
                wtrDataset[20]  = new LineDataSet(edc, "디클로로에탄(mg/L)");
                wtrDataset[21] = new LineDataSet(dioxane, "다이옥세인(mg/L)");
                wtrDataset[22]  = new LineDataSet(dichloro_mt, "디클로로메탄(mg/L)");
                wtrDataset[23]  = new LineDataSet(dehp, "디에틸헥실프탈레이트(mg/L)");
                wtrDataset[24] = new LineDataSet(cyan, "시안(mg/L)");
                wtrDataset[25] = new LineDataSet(cod, "COD(mg/L)");
                wtrDataset[26]  = new LineDataSet(chrome, "6가크롬(mg/L)");
                wtrDataset[27]  = new LineDataSet(chlorophyll, "클로로필(mg/L)");
                wtrDataset[28]  = new LineDataSet(cd, "카드뮴(mg/L)");
                wtrDataset[29] = new LineDataSet(ccl4, "사염화탄소");
                wtrDataset[30]  = new LineDataSet(bod, "BOD(mg/L)");
                wtrDataset[31]  = new LineDataSet(benzene, "벤젠(mg/L)");
                wtrDataset[32]  = new LineDataSet(as, "비소(mg/L)");
                wtrDataset[33]  = new LineDataSet(antimon, "안티몬(mg/L)");







                linelist_wtr = new ArrayList<LineDataSet>();





               // ArrayList<LineDataSet> linelist = new ArrayList<LineDataSet>();


                wtrDataset[0].setColor(Color.parseColor("#FF0000"));
                wtrDataset[1].setColor(Color.parseColor("#FF5E00"));
                wtrDataset[2].setColor(Color.parseColor("#FFBB00"));
                wtrDataset[3].setColor(Color.parseColor("#1DDB16"));
                wtrDataset[4].setColor(Color.parseColor("#00D8FF"));
                wtrDataset[5].setColor(Color.parseColor("#0054FF"));
                wtrDataset[6].setColor(Color.parseColor("#5F00FF"));
                wtrDataset[7].setColor(Color.parseColor("#FF00DD"));
                wtrDataset[8].setColor(Color.parseColor("#FF007F"));
                wtrDataset[9].setColor(Color.parseColor("#000000"));
                wtrDataset[10].setColor(Color.parseColor("#BDBDBD"));
                wtrDataset[11].setColor(Color.parseColor("#CC723D"));
                wtrDataset[12].setColor(Color.parseColor("#CCA63D"));
                wtrDataset[13].setColor(Color.parseColor("#9FC93C"));
                wtrDataset[14].setColor(Color.parseColor("#47C83E"));
                wtrDataset[15].setColor(Color.parseColor("#3DB7CC"));
                wtrDataset[16].setColor(Color.parseColor("#4374D9"));
                wtrDataset[17].setColor(Color.parseColor("#4641D9"));
                wtrDataset[18].setColor(Color.parseColor("#8041D9"));
                wtrDataset[19].setColor(Color.parseColor("#D941C5"));
                wtrDataset[20].setColor(Color.parseColor("#D9418C"));
                wtrDataset[21].setColor(Color.parseColor("#FAED7D"));
                wtrDataset[22].setColor(Color.parseColor("#FAE0D4"));
                wtrDataset[23].setColor(Color.parseColor("#E4F7BA"));
                wtrDataset[24].setColor(Color.parseColor("#CEFBC9"));
                wtrDataset[25].setColor(Color.parseColor("#D4F4FA"));
                wtrDataset[26].setColor(Color.parseColor("#D9E5FF"));
                wtrDataset[27].setColor(Color.parseColor("#E8D9FF"));
                wtrDataset[28].setColor(Color.parseColor("#FFA7A7"));
                wtrDataset[29].setColor(Color.parseColor("#5D5D5D"));
                wtrDataset[30].setColor(Color.parseColor("#005766"));
                wtrDataset[31].setColor(Color.parseColor("#22741C"));
                wtrDataset[32].setColor(Color.parseColor("#99004C"));
                wtrDataset[33].setColor(Color.parseColor("#660058"));

/*
                linelist.add(dataset);
                linelist.add(dataset2);
                linelist.add(dataset3);
                linelist.add(dataset4);
                linelist.add(dataset5);
                linelist.add(dataset6);
                linelist.add(dataset7);
                linelist.add(dataset8);
                linelist.add(dataset9);
                linelist.add(dataset10);
                linelist.add(dataset11);
                linelist.add(dataset12);
                linelist.add(dataset13);
                linelist.add(dataset14);
                linelist.add(dataset15);
                linelist.add(dataset16);
                linelist.add(dataset17);
                linelist.add(dataset18);
                linelist.add(dataset19);
                linelist.add(dataset20);
                linelist.add(dataset21);
                linelist.add(dataset22);
                linelist.add(dataset23);
                linelist.add(dataset24);
                linelist.add(dataset25);
                linelist.add(dataset26);
                linelist.add(dataset27);
                linelist.add(dataset28);
                linelist.add(dataset29);
                linelist.add(dataset30);
                linelist.add(dataset31);
                linelist.add(dataset32);
                linelist.add(dataset33);
                linelist.add(dataset34);

*/



               /* for(int i =0;i<linelist.size();i++){
                    linelist.get(i).setDrawCircles(false);
                }
*/
                Log.d("callWater","callWater");


                /*

                ChartLegend legendwtr_p = new ChartLegend(Color.parseColor("#FF0000"),"용존총인(mg/L)");
                input_info.add(legendwtr_p);

                ChartLegend legendwtr_o2 = new ChartLegend(Color.parseColor("#FF5E00"),"용존산소(mg/L)");
                input_info.add(legendwtr_o2);

                ChartLegend legendwtr_n = new ChartLegend(Color.parseColor("#FFBB00"),"용존총질소(mg/L)");
                input_info.add(legendwtr_n);

                ChartLegend legendtrichlro_et = new ChartLegend(Color.parseColor("#1DDB16"),"트리클로로에틸렌(mg/L)");
                input_info.add(legendtrichlro_et);

                ChartLegend legendtotla_p = new ChartLegend(Color.parseColor("#00D8FF"),"총인(mg/L)");
                input_info.add(legendtotla_p);

                ChartLegend legendtotla_n = new ChartLegend(Color.parseColor("#0054FF"),"총질소(mg/L)");
                input_info.add(legendtotla_n);

                ChartLegend legendtoc = new ChartLegend(Color.parseColor("#5F00FF"),"TOC(mg/L)");
                input_info.add(legendtoc);

                ChartLegend legendtetrachlo_et = new ChartLegend(Color.parseColor("#FF00DD"),"테트라클로로에틸렌(mg/L)");
                input_info.add(legendtetrachlo_et);

                ChartLegend legendsuspend_solid = new ChartLegend(Color.parseColor("#FF007F"),"부유물질(mg/L)");
                input_info.add(legendsuspend_solid);

                ChartLegend legendsurfactant = new ChartLegend(Color.parseColor("#000000"),"음이온계면활성제(mg/L)");
                input_info.add(legendsurfactant);

                ChartLegend legendpo4_p = new ChartLegend(Color.parseColor("#BDBDBD"),"인산염인(mg/L)");
                input_info.add(legendpo4_p);

                ChartLegend legendphenol = new ChartLegend(Color.parseColor("#CC723D"),"페놀(mg/L)");
                input_info.add(legendphenol);

                ChartLegend legendpcb = new ChartLegend(Color.parseColor("#CCA63D"),"PCB(mg/L)");
                input_info.add(legendpcb);

                ChartLegend legendpb = new ChartLegend(Color.parseColor("#9FC93C"),"납(mg/L)");
                input_info.add(legendpb);

                ChartLegend legendorganic_p = new ChartLegend(Color.parseColor("#47C83E"),"유기인(mg/L)");
                input_info.add(legendorganic_p);

                ChartLegend legendno3_n = new ChartLegend(Color.parseColor("#3DB7CC"),"질산성질소(mg/L)");
                input_info.add(legendno3_n);

                ChartLegend legendnh3_n = new ChartLegend(Color.parseColor("#4374D9"),"암모니아성질소(mg/L)");
                input_info.add(legendnh3_n);

                ChartLegend legendhg = new ChartLegend(Color.parseColor("#4641D9"),"수은(mg/L)");
                input_info.add(legendhg);

                ChartLegend legendhexa_benzene = new ChartLegend(Color.parseColor("#8041D9"),"헥사클로로벤젠(mg/L)");
                input_info.add(legendhexa_benzene);

                ChartLegend legendformaldehyde = new ChartLegend(Color.parseColor("#D941C5"),"포름알데히드(mg/L)");
                input_info.add(legendformaldehyde);

                ChartLegend legendedc = new ChartLegend(Color.parseColor("#D9418C"),"디클로로에탄(mg/L)");
                input_info.add(legendedc);

                ChartLegend legenddioxane = new ChartLegend(Color.parseColor("#FAED7D"),"다이옥세인(mg/L)");
                input_info.add(legenddioxane);

                ChartLegend legenddichloro_mt = new ChartLegend(Color.parseColor("#FAE0D4"),"디클로로메탄(mg/L)");
                input_info.add(legenddichloro_mt);

                ChartLegend legenddehp = new ChartLegend(Color.parseColor("#E4F7BA"),"디에틸헥실프탈레이트(mg/L)");
                input_info.add(legenddehp);

                ChartLegend legendcyan = new ChartLegend(Color.parseColor("#CEFBC9"),"시안(mg/L)");
                input_info.add(legendcyan);

                ChartLegend legendcod = new ChartLegend(Color.parseColor("#D4F4FA"),"COD(mg/L)");
                input_info.add(legendcod);

                ChartLegend legendchrome = new ChartLegend(Color.parseColor("#D9E5FF"),"6가크롬(mg/L)");
                input_info.add(legendchrome);

                ChartLegend legendchlorophyll = new ChartLegend(Color.parseColor("#E8D9FF"),"클로로필(mg/L)");
                input_info.add(legendchlorophyll);

                ChartLegend legendcd = new ChartLegend(Color.parseColor("#FFA7A7"),"카드뮴(mg/L)");
                input_info.add(legendcd);

                ChartLegend legendccl4 = new ChartLegend(Color.parseColor("#5D5D5D"),"사염화탄소(mg/L)");
                input_info.add(legendccl4);

                ChartLegend legendbod = new ChartLegend(Color.parseColor("#22741C"),"BOD(mg/L)");
                input_info.add(legendbod);

                ChartLegend legendbenzene = new ChartLegend(Color.parseColor("#005766"),"벤젠(mg/L)");
                input_info.add(legendbenzene);

                ChartLegend legendas = new ChartLegend(Color.parseColor("#99004C"),"비소(mg/L)");
                input_info.add(legendas);

                ChartLegend legendantimon = new ChartLegend(Color.parseColor("#660058"),"안티몬(mg/L)");
                input_info.add(legendantimon);




*/

                // input_info = legend_temp.callWater();

                legend = lineChart.getLegend();
                legend.setEnabled(false);

                lineChart.setData(new LineData(date_wtr, linelist_wtr));
                lineChart.animateY(2000);





                //MyAdapter myAdapter = new MyAdapter(this,R.layout.chart_legend_text,input_info);
                //list123.setAdapter(myAdapter);

            }


        }

        else{

            Log.d("Null","Null");
        }


        dialog.dismiss();


    }







}