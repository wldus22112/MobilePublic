package com.example.admin.mobilepublic_20160811.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.admin.mobilepublic_20160811.R;

/**
 * Created by admin on 2016-08-22.
 */
public class AppInfo extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appinfo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         getSupportActionBar().setIcon(R.drawable.info);
         getSupportActionBar().setTitle(" 앱 이용안내");




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



        switch (view.getId()){

            case R.id.appinfo_01:

                Intent intent = new Intent(this, InfoFirst.class);
                intent.putExtra("get_num",1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent);

                break;

            case R.id.appinfo_02:

                Intent intent2 = new Intent(this, InfoFirst.class);
                intent2.putExtra("get_num",2);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent2);

                break;

            case R.id.appinfo_03:

                Intent intent3 = new Intent(this, InfoFirst.class);
                intent3.putExtra("get_num",3);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent3);

                break;

            case R.id.appinfo_04:
                Intent intent4 = new Intent(this, InfoFirst.class);
                intent4.putExtra("get_num",4);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent4);


                break;


        }
    }
}


