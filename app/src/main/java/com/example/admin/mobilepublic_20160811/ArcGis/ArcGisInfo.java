package com.example.admin.mobilepublic_20160811.ArcGis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.mobilepublic_20160811.R;

/**
 * Created by admin on 2016-08-22.
 */
public class ArcGisInfo extends Activity{

    private int num = 0;

    private TextView textView01;
    private TextView textView02;
    private TextView textView03;
    private TextView textView04;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.arcgis_info);

        Intent intent = getIntent();
        num = intent.getIntExtra("num",0);

        textView01 = (TextView)findViewById(R.id.arcgis_info_title01);
        textView02 = (TextView)findViewById(R.id.arcgis_info_value01);
        textView03 = (TextView)findViewById(R.id.arcgis_info_title02);
        textView04 = (TextView)findViewById(R.id.arcgis_info_value02);


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.95); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 80%

        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        if(num==1){

            textView01.setText("│ 국토환경성평가지도란? │");
            textView02.setText("국토의 다양한 환경정보(65개 항목)를 종합적으로 평가하여 환경적 가치에 따라 전국을 5개 등급으로 구분하고 색채를 다르게 표시하여 누구나 알기 쉽게 작성한 지도이다.");

            textView03.setText("│ 국토환경성평가지도의 평가 항목? │");
            textView04.setText("국토환경성평가항목은 법령의 규정에 의한 보전용도지역 등의 법제적 평가 항목과 자연자산의 개념을 포함하는 환경·생태적 평가 항목으로 구분함 법제적 평가 항목은 자연환경, 수질환경, 기타 등 3개 부문 57개 항목으로 구성되어 있음.\n" +
                    "환경·생태적 평가 항목은 자연자원의 개념을 반영하여 다양성(종 다양성 등), 자연성(영급, 생태자연도 등), 희귀성(보호종 및 멸종위기종 분포도 등), 허약성(도로인접성, 시가지인접성 등), 안정성(경급 등), 연계성, 잠재적 가치 등 8개 항목으로 구성되어 있음.\n" +
                    "평가등급은 환경적 가치에 따라 1~5등급으로 구분되는데, 1~4등급은 상대적인 측면에서의 환경적 보전가치 정도를 평가한 것이고, 5등급은 기 개발지역에 해당함. 1등급에 가까울수록 상대적으로 환경적 가치가 높음.");

        }else if(num==2){

            textView01.setText("│ 생태자연도란? │");
            textView02.setText("생태·자연도는 산, 하천, 내륙습지, 호소, 농지, 도시 등에 대하여 자연환경을 생태적 가치, 자연성, 경관적 가치 등에 따라 등급화(1~3등급 및 별도관리지역)한 지도이다.");

            textView03.setText("│ 생태,자연도의 정의 │");
            textView04.setText("1등급\n 가. 야생동ㆍ식물보호법 제2조의 규정에 의한 멸종위기야생동ㆍ식물(이하 \"멸종위기야생동ㆍ식물\"이라 한다)의 주된 서식지ㆍ도래지 및 주요 생태축 또는 주요 생태통로가 되는 지역\n" +
                    "나. 생태계가 특히 우수하거나 경관이 특히 수려한 지역\n" +
                    "다. 생물의 지리적 분포한계에 위치하는 생태계 지역 또는 주요 식생의 유형을 대표하는 지역\n" +
                    "라. 생물다양성이 특히 풍부하고 보전가치가 큰 생물자원이 존재ㆍ분포하고 있는 지역\n" +
                    "마. 그 밖에 가목 내지 라목에 준하는 생태적 가치가 있는 지역으로서 대통령령이 정하는 기준에 해당하는 지역 자연원시림이나 이에 가까운 산림 또는 고산초원\n" +
                    "자연상태나 이에 가까운 하천, 호소, 강하구\n" +
                    "\n" +
                    " 2등급\n1등급에 준하는 지역으로서 장차 보전의 가치가 있는 지역 또는 1등급 권역의 외부지역으로서 1등급 권역의 보호를 위하여 필요한 지역" +
                    "3등급\n1등급 권역, 2등급 권역 및 별도관리지역으로 분류된 지역외의 지역으로서 개발 또는 이용의 대상이 되는 지역");

        }else if(num==3){

            textView01.setText("│ 토지피복지도? │");
            textView02.setText("주제도(Thematic Map)의 일종으로, 지구표면 지형지물의 형태를 일정한 과학적 기준에 따라 분류하여 동질의 특성을 지닌 구역을 Color Indexing한 후 지도의 형태로 표현한 공간정보DB를 말함\n1985년 유럽환경청(Europe Environment Agency, EEA)에서 추진된 - CORINE (Coordination of Information on the Environment)프로젝트에서 개념 정립\n" +
                    "\n" +
                    "* EU(舊 EC)에서 회원국들의 토지현황에 대한 방대한 정보를 종합적으로 수집·관리하기 위해 유럽환경청(EEA)에서 추진한 전 유럽 토지피복지도 구축사업 \n" +
                    "\n" +
                    "이를 기반으로 우리나라 실정에 맞는 분류기준을 확정하여 1998년 환경부에서 최초로 남한지역에 대한 대분류 토지피복지도 구축\n");

            textView03.setText("│ 토지피복 분류체계 │");
            textView04.setText("토지피복지도는 해상도에 따라 대분류(해상도30M급), 중분류(해상도5M급), 세분류(해상도1M급)의 3가지 위계를 가짐.\n" +
                    "대분류는 우리나라의 대표적인 7가지 토지피복으로 이루어져 있으며, 중분류 및 세분류는 대분류를 좀 더 세분하고 있음.\n");



        }



    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.arcgis_info_EGIS:

                Intent intent = new Intent(this, EGISWebView.class);

                int EGIS_num = 0;

                if(num==1){
                    EGIS_num = 1;
                    intent.putExtra("EGIS",EGIS_num);
                }else if(num==2){
                    EGIS_num = 2;
                    intent.putExtra("EGIS",EGIS_num);
                }else if(num==3){
                    EGIS_num = 3;
                    intent.putExtra("EGIS",EGIS_num);
                }


                startActivity(intent);

                break;

            case R.id.arcgis_info_close:
                finish();
                break;
        }
    }
}
