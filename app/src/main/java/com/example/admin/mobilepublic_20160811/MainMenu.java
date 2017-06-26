package com.example.admin.mobilepublic_20160811;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.admin.mobilepublic_20160811.ArcGis.ArcgisActivity;
import com.example.admin.mobilepublic_20160811.Information.AppInfo;

/**
 * Created by admin on 2016-08-19.
 */
public class MainMenu extends Activity {

    private Intent intent;
    private String temp;

    private static final int REQUEST_CODE_LOCATION = 2;

    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;




    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainmenu);

       mainmenu_permissionCheck();



        intent = new Intent(this, MainActivity.class);



    }

    public void mainmenu_permissionCheck(){

        /*

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Display UI and wait for user interaction
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION);
            }
        } else {
            // permission has been granted, continue as usual
            // Location myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        // Check permission for CAMERA
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ReportForm.REQUEST_CAMERA);

        } else {
            // permission has been granted, continue as usual

        }

        */

        if((ContextCompat.checkSelfPermission(MainMenu.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainMenu.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainMenu.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainMenu.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(MainMenu.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)

                )

        {
            ActivityCompat.requestPermissions
                    (MainMenu.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_PHONE_STATE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }






                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }






    public void onClick(View view) {
        switch (view.getId()){

            case R.id.first:
                temp = "0";
                intent.putExtra("menu_num",temp);

                startActivity(intent);


                break;

            case R.id.second:
                temp = "1";
                intent.putExtra("menu_num",temp);



                startActivity(intent);

                break;

            case R.id.third:

                Intent intent2 = new Intent(this, ArcgisActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent2);

                break;

            case R.id.fourth:

                temp = "3";
                intent.putExtra("menu_num",temp);

                startActivity(intent);

                break;

            case R.id.appinfo_menu:

                Intent intent3 = new Intent(this, AppInfo.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                startActivity(intent3);

                break;
        }



    }
}
