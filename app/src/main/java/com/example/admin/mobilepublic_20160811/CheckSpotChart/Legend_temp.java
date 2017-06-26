package com.example.admin.mobilepublic_20160811.CheckSpotChart;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2016-06-21.
 */
public class Legend_temp {

    public ArrayList<ChartLegend> callWater(){

        ArrayList<ChartLegend> input_info = new ArrayList<ChartLegend>();

        Log.d("callWater","callWater");


        ChartLegend wtr_p = new ChartLegend(Color.parseColor("#FF0000"),"용존총인(mg/L)");
        input_info.add(wtr_p);

        ChartLegend wtr_o2 = new ChartLegend(Color.parseColor("#FF5E00"),"용존산소(mg/L)");
        input_info.add(wtr_o2);

        ChartLegend wtr_n = new ChartLegend(Color.parseColor("#FFBB00"),"용존총질소(mg/L)");
        input_info.add(wtr_n);

        ChartLegend trichlro_et = new ChartLegend(Color.parseColor("#1DDB16"),"트리클로로에틸렌(mg/L)");
        input_info.add(trichlro_et);

        ChartLegend totla_p = new ChartLegend(Color.parseColor("#00D8FF"),"총인(mg/L)");
        input_info.add(totla_p);

        ChartLegend totla_n = new ChartLegend(Color.parseColor("#0054FF"),"총질소(mg/L)");
        input_info.add(totla_n);

        ChartLegend toc = new ChartLegend(Color.parseColor("#5F00FF"),"TOC(mg/L)");
        input_info.add(toc);

        ChartLegend tetrachlo_et = new ChartLegend(Color.parseColor("#FF00DD"),"테트라클로로에틸렌(mg/L)");
        input_info.add(tetrachlo_et);

        ChartLegend suspend_solid = new ChartLegend(Color.parseColor("#FF007F"),"부유물질(mg/L)");
        input_info.add(suspend_solid);

        ChartLegend surfactant = new ChartLegend(Color.parseColor("#000000"),"음이온계면활성제(mg/L)");
        input_info.add(surfactant);

        ChartLegend po4_p = new ChartLegend(Color.parseColor("#BDBDBD"),"인산염인(mg/L)");
        input_info.add(po4_p);

        ChartLegend phenol = new ChartLegend(Color.parseColor("#CC723D"),"페놀(mg/L)");
        input_info.add(phenol);

        ChartLegend pcb = new ChartLegend(Color.parseColor("#CCA63D"),"PCB(mg/L)");
        input_info.add(pcb);

        ChartLegend pb = new ChartLegend(Color.parseColor("#9FC93C"),"납(mg/L)");
        input_info.add(pb);

        ChartLegend organic_p = new ChartLegend(Color.parseColor("#47C83E"),"유기인(mg/L)");
        input_info.add(organic_p);

        ChartLegend no3_n = new ChartLegend(Color.parseColor("#3DB7CC"),"질산성질소(mg/L)");
        input_info.add(no3_n);

        ChartLegend nh3_n = new ChartLegend(Color.parseColor("#4374D9"),"암모니아성질소(mg/L)");
        input_info.add(nh3_n);

        ChartLegend hg = new ChartLegend(Color.parseColor("#4641D9"),"수은(mg/L)");
        input_info.add(hg);

        ChartLegend hexa_benzene = new ChartLegend(Color.parseColor("#8041D9"),"헥사클로로벤젠(mg/L)");
        input_info.add(hexa_benzene);

        ChartLegend formaldehyde = new ChartLegend(Color.parseColor("#D941C5"),"포름알데히드(mg/L)");
        input_info.add(formaldehyde);

        ChartLegend edc = new ChartLegend(Color.parseColor("#D9418C"),"디클로로에탄(mg/L)");
        input_info.add(edc);

        ChartLegend dioxane = new ChartLegend(Color.parseColor("#FAED7D"),"다이옥세인(mg/L)");
        input_info.add(dioxane);

        ChartLegend dichloro_mt = new ChartLegend(Color.parseColor("#FAE0D4"),"디클로로메탄(mg/L)");
        input_info.add(dichloro_mt);

        ChartLegend dehp = new ChartLegend(Color.parseColor("#E4F7BA"),"디에틸헥실프탈레이트(mg/L)");
        input_info.add(dehp);

        ChartLegend cyan = new ChartLegend(Color.parseColor("#CEFBC9"),"시안(mg/L)");
        input_info.add(cyan);

        ChartLegend cod = new ChartLegend(Color.parseColor("#D4F4FA"),"COD(mg/L)");
        input_info.add(cod);

        ChartLegend chrome = new ChartLegend(Color.parseColor("#D9E5FF"),"6가크롬(mg/L)");
        input_info.add(chrome);

        ChartLegend chlorophyll = new ChartLegend(Color.parseColor("#E8D9FF"),"클로로필(mg/L)");
        input_info.add(chlorophyll);

        ChartLegend cd = new ChartLegend(Color.parseColor("#FFA7A7"),"카드뮴(mg/L)");
        input_info.add(cd);

        ChartLegend ccl4 = new ChartLegend(Color.parseColor("#5D5D5D"),"사염화탄소(mg/L)");
        input_info.add(ccl4);

        ChartLegend bod = new ChartLegend(Color.parseColor("#22741C"),"BOD(mg/L)");
        input_info.add(bod);

        ChartLegend benzene = new ChartLegend(Color.parseColor("#005766"),"벤젠(mg/L)");
        input_info.add(benzene);

        ChartLegend as = new ChartLegend(Color.parseColor("#99004C"),"비소(mg/L)");
        input_info.add(as);

        ChartLegend antimon = new ChartLegend(Color.parseColor("#660058"),"안티몬(mg/L)");
        input_info.add(antimon);

        return input_info;
    }

    public ArrayList<ChartLegend> callAir(){

        ArrayList<ChartLegend> input_info = new ArrayList<ChartLegend>();

        Log.d("callAir","callAir");

        ChartLegend val_so2 = new ChartLegend(Color.RED,"아황산가스");
        input_info.add(val_so2);

        ChartLegend val_no2 = new ChartLegend(Color.YELLOW,"이산화질소");
        input_info.add(val_no2);

        ChartLegend val_o3 = new ChartLegend(Color.BLUE,"오존");
        input_info.add(val_o3);

        ChartLegend val_co = new ChartLegend(Color.GREEN,"일산화탄소");
        input_info.add(val_co);

        ChartLegend val_pm10 = new ChartLegend(Color.CYAN,"먼지10");
        input_info.add(val_pm10);

        ChartLegend val_pm25 = new ChartLegend(Color.DKGRAY,"먼지2");
        input_info.add(val_pm25);

        return input_info;

    }





}
