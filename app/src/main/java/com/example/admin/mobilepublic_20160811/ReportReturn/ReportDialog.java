package com.example.admin.mobilepublic_20160811.ReportReturn;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import net.daum.mf.map.api.MapPoint;

import static java.lang.Double.valueOf;

/**
 * Created by admin on 2016-06-07.
 */
public class ReportDialog  {

    private View view;
    private Context context;


    public ReportDialog(Context context){
        // Required empty public constructor

        this.context = context;


    }


     public void showLocationDialog(final Context context, final MapPoint mapPoint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("신고");
        builder.setMessage("신고하려면 'OK', 취소하려면 'CANCEL'을 누르세요");

        String positiveText = "OK";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        Intent intent = new Intent(context, ReportForm.class);


                        double lat = valueOf(mapPoint.getMapPointGeoCoord().latitude);
                        double lng = valueOf(mapPoint.getMapPointGeoCoord().longitude);

                        intent.putExtra("latitude",lat);
                        intent.putExtra("longitude",lng);


                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                        context.startActivity(intent);




                    }
                });

        String negativeText = "CANCEL";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }



}


