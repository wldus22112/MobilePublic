package com.example.admin.mobilepublic_20160811.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.admin.mobilepublic_20160811.R;

/**
 * Created by admin on 2016-08-22.
 */
public class InfoFirst extends AppCompatActivity{

    public ViewPager viewPager;

    public PagerAdapter pagerAdapter;
    public int get_num;

    public TextView info_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_first);

        Intent get_intent = getIntent();
        get_num = get_intent.getIntExtra("get_num",0);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setIcon(R.drawable.info_mini);
       // getSupportActionBar().setTitle("앱 이용안내");



        info_title = (TextView)findViewById(R.id.info_title);



       viewPager = (ViewPager) findViewById(R.id.viewpager);



        if(get_num==1){

            getSupportActionBar().setTitle("배출업소 조회 이용안내");
            info_title.setText("│ 내 주변 배출업소 조회 │");
            pagerAdapter = new PagerAdapter(this,1);

        } else if(get_num==2){

            getSupportActionBar().setTitle("측정소 조회 이용안내");
            info_title.setText("│ 내 주변 측정소 조회 │");
            pagerAdapter = new PagerAdapter(this,2);

        }else if(get_num==3){

            getSupportActionBar().setTitle("환경지도 이용안내");
            info_title.setText("│ 환경지도 │");
            pagerAdapter = new PagerAdapter(this,3);

        }else if(get_num==4){

            getSupportActionBar().setTitle("신고하기 이용안내");
            info_title.setText("│ 내 위치 신고하기 │");
            pagerAdapter = new PagerAdapter(this,4);

        }

        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            public void onPageSelected(int position) {
                // TODO Auto-generated method stub

            }

        });



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



    public void onClick(View view) {

        int now_view =  viewPager.getCurrentItem();

        switch (view.getId()){



            case R.id.info_left:

                viewPager.setCurrentItem(now_view - 1);
                
                break;
            case R.id.info_right:

                viewPager.setCurrentItem(now_view + 1);
                break;
        }

    }
}
