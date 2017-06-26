package com.example.admin.mobilepublic_20160811.Search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-06-07.
 */
public class CompanySearch extends Activity {
    //private AQuery aq = new AQuery(this);
    private Toolbar toolbar2;
    TextView text;
    private AQuery aq ;
    // private   ArrayList<Info> input_info = new ArrayList<Info>();
    private ListView listview;

    private ArrayList<SearchInfo> searchResult = new ArrayList<SearchInfo>();
    private WIFIScan wifiScan;
    private String url;
    private Button close_btn;
    private TextView close_text;
    private LinearLayout search_result;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.companysearch);



        Intent intent = getIntent();
        String search = intent.getStringExtra("search");
        Log.d("search",search);

        // String url = "http://192.168.100.132:8080/mobile/search.do";

        wifiScan = new WIFIScan();
        url =  wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
        url = url + "search.do";
        Log.d("url : ",url);

        dialog = ProgressDialog.show(CompanySearch.this, "", "Loading", true);




        listview = (ListView) findViewById(R.id.company_search_list_view) ;
        listview.setOnItemClickListener(listener);

        close_btn = (Button)findViewById(R.id.companysearch_btn);
        close_text = (TextView)findViewById(R.id.companysearch_result);

        search_result = (LinearLayout) findViewById(R.id.search_layout);

        //close_btn.setVisibility(View.GONE);
        //close_text.setVisibility(View.GONE);
        search_result.setVisibility(View.GONE);


        if(search.length()==0){
            dialog.dismiss();

            Toast.makeText(this,"검색어를 다시 입력해주세요", Toast.LENGTH_SHORT).show();

            close_text.setVisibility(View.VISIBLE);
            close_text.bringToFront();
            close_btn.setVisibility(View.VISIBLE);
        }
        else{

            aq = new AQuery(this);
            HashMap<String, Object> map = new HashMap<>();
            map.put("search", search);
            aq.ajax(url, map, JSONObject.class, this, "jsonCallback");

        }







    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast.makeText(CompanySearch.this, searchResult.get(position).get_Company_Name()+"\n"+searchResult.get(position).get_address()+"\n" +searchResult.get(position).get_Apv_Reg_No() , Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("APV_REG_NO",searchResult.get(position).get_Apv_Reg_No());
            setResult(1, i);
            finish();
        }
    };

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.companysearch_btn:
                finish();
        }
    }

    private class MyAdapter extends ArrayAdapter<SearchInfo> {

        private ArrayList<SearchInfo> items;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<SearchInfo> items){
            super(context, textViewResourceId, items);

            this.items = items;
        }

        @Override
        public View getView(int position, View covertView, ViewGroup parent){

            View v = covertView;
            if(v==null){
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.info_view,null);

            }

            SearchInfo info = items.get(position);

            if(info != null){
                TextView textView1 = (TextView) v.findViewById(R.id.text_info);
                TextView textView2 = (TextView)v.findViewById(R.id.text_info2);
                if(textView1 !=null){
                    textView1.setText(info.get_Company_Name());
                }
                if(textView2!=null){
                    textView2.setText(info.get_address());

                }

                if(position %2 ==0){
                    v.setBackground(getResources().getDrawable(R.drawable.border_list));
                }
                else{
                    v.setBackground(getResources().getDrawable(R.drawable.border_list_odd));
                }





            }

            return v;
        }

    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {

        close_text.setVisibility(View.INVISIBLE);
        close_btn.setVisibility(View.INVISIBLE);
        search_result.setVisibility(View.VISIBLE);

       // Log.d("result", json.getJSONArray("result").toString());

        if(json.getJSONArray("result").length()==0||json.getJSONArray("result")==null){
            Toast.makeText(this,"검색결과가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            close_text.setVisibility(View.VISIBLE);
            close_text.bringToFront();
            close_btn.setVisibility(View.VISIBLE);
            //search_result.setVisibility(View.VISIBLE);

        }
        else if(json != null) {

            close_text.setVisibility(View.INVISIBLE);
            close_btn.setVisibility(View.INVISIBLE);
            search_result.setVisibility(View.VISIBLE);



            for(int i=0; i<json.getJSONArray("result").length(); i++) {

                SearchInfo temp = new SearchInfo(json.getJSONArray("result").getJSONObject(i).get("DISCHG_WRK").toString(),
                        json.getJSONArray("result").getJSONObject(i).get("WRKR_ADDR").toString(),
                        json.getJSONArray("result").getJSONObject(i).get("APV_REG_NO").toString());

                searchResult.add(temp);





            }
        }

        else{
            Log.d("Null","Null");
        }

        MyAdapter myAdapter = new MyAdapter(this,R.layout.info_view,searchResult);
        listview.setAdapter(myAdapter);

        dialog.dismiss();
    }


}