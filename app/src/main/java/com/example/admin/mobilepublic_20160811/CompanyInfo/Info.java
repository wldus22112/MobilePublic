package com.example.admin.mobilepublic_20160811.CompanyInfo;

/**
 * Created by admin on 2016-06-02.
 */
public class Info{
    private String info_name;
    private String info_string;

    public Info(String _name, String _string){
        this.info_name = _name;
        this.info_string = _string;
    }

    public String getInfo_name(){
        return info_name;
    }

    public String getInfo_string(){
        return info_string;
    }


}
