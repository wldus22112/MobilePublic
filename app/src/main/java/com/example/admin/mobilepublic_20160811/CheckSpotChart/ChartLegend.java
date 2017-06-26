package com.example.admin.mobilepublic_20160811.CheckSpotChart;

/**
 * Created by admin on 2016-06-20.
 */


public class ChartLegend {
    private int Color;
    private String legendName;

    public ChartLegend(int _name, String _string){
        this.Color = _name;
        this.legendName = _string;
    }

    public int ge_tColor(){
        return Color;
    }

    public String get_legendName(){
        return legendName;
    }


}
