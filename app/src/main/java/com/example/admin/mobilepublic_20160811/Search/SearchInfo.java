package com.example.admin.mobilepublic_20160811.Search;

/**
 * Created by Administrator on 2016-06-07.
 */
/**
 * Created by admin on 2016-06-02.
 */
public class SearchInfo{
    private String info_name;
    private String info_address;
    private String apv_reg_no;

    public SearchInfo(String _name, String _string, String APV_REG_NO){
        this.info_name = _name;
        this.info_address = _string;
        this.apv_reg_no = APV_REG_NO;
        }

    public String get_Company_Name(){return info_name;}

    public String  get_address(){return info_address;}
    public String get_Apv_Reg_No(){return apv_reg_no;}

}