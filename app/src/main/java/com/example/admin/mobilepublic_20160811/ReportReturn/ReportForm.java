package com.example.admin.mobilepublic_20160811.ReportReturn;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.admin.mobilepublic_20160811.R;
import com.example.admin.mobilepublic_20160811.WIFIScan;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by admin on 2016-06-07.
 */
public class ReportForm extends AppCompatActivity implements OnClickListener{


    TextView text;
    private AQuery aq ;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private ImageView imgview;
    //public Uri mImageCaptureUri = null;
    private  Uri mImageCaptureUri=null;
    private Uri imgUri;
    private EditText input1;
    private EditText input2;
    private EditText input3;
    private EditText input4;
    private  String serverUrlString;
    private double lat;
    private double lng ;
    private LinearLayout button_report;
    private String realPath = null;
    private WIFIScan wifiScan;

    private String type_check = null;

    private LinearLayout type_water;
    private LinearLayout type_air;

    private TextView ph_num;
    private LinearLayout type_etc;

    private Button imageview;

    public static final int REQUEST_CAMERA = 1;

    private ImageView temp;

    int MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_form);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setElevation(0);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00b0d4")));

        Intent intent = getIntent();

        lat = intent.getDoubleExtra("latitude",0);
        lng = intent.getDoubleExtra("longitude",0);
        type_check = intent.getStringExtra("RP_TYPE");

        System.out.println("lat"+lat);
        System.out.println("lng" + lng);

        getSupportActionBar().setTitle("신고정보");



        input1 = (EditText)findViewById(R.id.text_report2);
        input2 = (EditText)findViewById(R.id.text_report4);
        input3 = (EditText)findViewById(R.id.text_report6);
        input4 = (EditText)findViewById(R.id.text_report8);

        type_water = (LinearLayout)findViewById(R.id.type_water);
        type_air  = (LinearLayout)findViewById(R.id.typet_air);
        type_etc = (LinearLayout)findViewById(R.id.type_etc);

        ph_num = (TextView)findViewById(R.id.text_repor_ph);


        type_water.setBackgroundResource(R.drawable.border_button);

        //Button buttonCamera = (Button) findViewById(R.id.camera);
        //Button buttonGallery = (Button) findViewById(R.id.gallery);



       // imgview = (ImageView) findViewById(R.id.report_Image);
        imageview = (Button) findViewById(R.id.report_Image);
        button_report = (LinearLayout)findViewById(R.id.button_report);

        wifiScan = new WIFIScan();

        Date now = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //System.out.println(format.format(now));
        input2.setText(format.format(now));

       // temp = (ImageView)findViewById(R.id.temp_camera);
        permissionCheck();

        String phone = null;

        TelephonyManager mgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        phone = mgr.getLine1Number();
        Log.v("phone_num",phone);

        ph_num.setText(phone);

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

            case R.id.type_water :
                type_check = "WATER";

                type_water.setBackgroundResource(R.drawable.border_button);
                type_air.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                type_etc.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));

                break;

            case R.id.typet_air :
                type_check = "AIR";

                type_air.setBackgroundResource(R.drawable.border_button);
                type_water.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                type_etc.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                break;
            case R.id.type_etc :
                type_check = "ETC";

                type_etc.setBackgroundResource(R.drawable.border_button);
                type_water.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                type_air.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00668a")));
                break;


            case R.id.report_Image:

                showLocationDialog();
                break;
            case R.id.button_report:

                AlertDialog.Builder dialog = new AlertDialog.Builder(ReportForm.this);
                AlertDialog.Builder ad;
                if(input1.getText().toString().length()<=0){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("제목을 입력해주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else if(input2.getText().toString().length()<=0){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("신고 일자를 입력해주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else if(input3.getText().toString().length()<=0){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("신고 장소 입력해주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else if(input4.getText().toString().length()<=0){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("신고 내용을 입력해주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else if(realPath == null){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("사진을 올려주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else if (type_check==null){
                    ad=new AlertDialog.Builder(this);
                    ad.setTitle("알림").setMessage("상단의 신고타입을 체크해 주세요.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
                else {
                    serverUrlString = wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                    serverUrlString = serverUrlString + "reportSavePhoto.do";
                    Log.d("서버주소", serverUrlString.toString());
                    File file = new File(realPath);

                    String camImagePath = file.getParent() + "/CameraImage/";
                    Log.e("Cam Path : ", camImagePath);

                    FileUpload(realPath.toString());
                    String fileName = file.getName();
                    Log.d("filename", fileName);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("RP_PHOTOPATH", fileName);
                    map.put("RP_TITLE", input1.getText());
                    map.put("RP_DATE", input2.getText());
                    map.put("RP_ADDR", input3.getText());
                    map.put("RP_CONTENT", input4.getText());
                    map.put("RP_PHONE_NUM",ph_num.getText());
                    map.put("RP_STATE",1);
                    map.put("GIS_POINT_X", lng);
                    map.put("GIS_POINT_Y", lat);
                    map.put("RP_TYPE",type_check );
                    String url;
                    url = wifiScan.checkWIFI(wifiScan.getWIFISSID(this));
                    url = url + "reportSave.do";
                    Log.d("오오", url.toString());
                    aq = new AQuery(this);
                    aq.ajax(url, map, JSONObject.class, this, "jsonCallback");
                    Intent i = new Intent();
                    setResult(2, i);

                    finish();

                }
                break;

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == PICK_FROM_CAMERA) {


            Bitmap selPhoto=null;
            imgUri= mImageCaptureUri;
            try{
                realPath= getCameraPath(imgUri);


                try {
                    AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(imgUri, "r");
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 4;
                    selPhoto = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, opt);
                    //      selPhoto = Images.Media.getBitmap( getContentResolver(), imgUri );

                } catch (OutOfMemoryError e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


             //  Drawable drawable = new BitmapDrawable(getResources(), selPhoto);
               // imgview.setBackgroundDrawable(drawable);
                //temp.setBackgroundDrawable(drawable);

            }
            catch(NullPointerException e){
                requestCode =PICK_FROM_GALLERY;
            }



        }
        if (requestCode == PICK_FROM_GALLERY) {

            try {

                imgUri= data.getData();

                realPath = getGalleryPath(imgUri);

                Bitmap selPhoto = MediaStore.Images.Media.getBitmap( getContentResolver(), imgUri );
            //    Drawable drawable = new BitmapDrawable(getResources(), selPhoto);
            //    imgview.setBackgroundDrawable(drawable);
               // temp.setBackgroundDrawable(drawable);



            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    public  void cameraCall(){


        permissionCheck();


        // 카메라 호출
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        try {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            startActivityForResult(intent,PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
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



    public  void galleryCall(){

        permissionCheck();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        try {
            startActivityForResult(intent, PICK_FROM_GALLERY);

        } catch (ActivityNotFoundException e) {
            // Do nothing for now
            e.printStackTrace();
        }
    }


    public void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진첨부");
        builder.setMessage("갤러리/카메라 선택");


        final String positiveText = "카메라";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        cameraCall();
                    }
                });

        String negativeText = "갤러리";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        galleryCall();
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }
    private String filename = "";
    public String FileUpload(String filePath){
        final String filesToUploadString = filePath;
        final String[] filesToUploadArray = filesToUploadString.split(",");

        new Thread(){
            @Override
            public void run() {

                try{
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(serverUrlString);
                    Log.d("주소주소",serverUrlString);
                    httpPost.setHeader("Connection", "Keep-Alive");
                    httpPost.setHeader("Accept-Charset", "UTF-8");
                    httpPost.setHeader("ENCTYPE", "multipart/form-data");

                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
                    multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                    File file = new File(filesToUploadArray[0]);
                    filename = file.getName();

                    multipartEntityBuilder.addPart("pic", new FileBody(file));

                    HttpEntity httpEntity = multipartEntityBuilder.build();

                    httpPost.setEntity(httpEntity);

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity1 = httpResponse.getEntity();

                } catch (UnsupportedEncodingException e) {
                } catch (ClientProtocolException e1) {
                } catch (IOException e1) {
                } catch (ParseException e) {
                }
            }
        }.start();

        return filename;
    }

    public String getGalleryPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    public String getCameraPath(Uri uri) {
        String result = uri.toString();

        return result.substring(7);
    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {
        Log.d("url : ", url);

        Log.d("status", status.toString());

        if(json != null){



        }else{
            Log.d("Null","Null");
        }


    }

    public void permissionCheck(){

        // Check permission for CAMERA
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ReportForm.REQUEST_CAMERA);

        } else {
            // permission has been granted, continue as usual

        }

        if((ContextCompat.checkSelfPermission(ReportForm.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(ReportForm.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))

        {
            ActivityCompat.requestPermissions
                    (ReportForm.this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },MY_PERMISSIONS_REQUEST_READ_AND_WRITE_EXTERNAL_STORAGE);
        }


    }


}








