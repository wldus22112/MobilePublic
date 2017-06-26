package com.example.admin.mobilepublic_20160811.Information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.mobilepublic_20160811.R;

/**
 * Created by admin on 2016-08-24.
 */
public class PagerAdapter extends android.support.v4.view.PagerAdapter {



    Context mContext;
    LayoutInflater mLayoutInflater;
    int pagenum;

    public PagerAdapter(Context context, int num) {
        this.mContext = context;
        this.pagenum = num;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        int pagecount=0;

        if(pagenum==1){
            pagecount = mResources01.length;
        }else if(pagenum==2){
            pagecount = mResources02.length;
        }
        else if(pagenum==3){
            pagecount = mResources03.length;
        }
        else if(pagenum==4){
            pagecount = mResources04.length;
        }


        return pagecount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewpager_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewpager_imageview);

        if(pagenum==1){
            imageView.setImageResource(mResources01[position]);
        }
        else if(pagenum==2){
            imageView.setImageResource(mResources02[position]);
        }
        else if(pagenum==3){
            imageView.setImageResource(mResources03[position]);
        }
       else if(pagenum==4){
            imageView.setImageResource(mResources04[position]);
        }





        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    int[] mResources01 = {
          R.drawable.info_0101,
            R.drawable.info_0102,
            R.drawable.info_0103,
            R.drawable.info_0104
    };

    int[] mResources02 = {
            R.drawable.info_0201,
            R.drawable.info_0202

    };

    int[] mResources03 = {
            R.drawable.info_0301,
            R.drawable.info_0302
    };

    int[] mResources04 = {
            R.drawable.info_0401,
            R.drawable.info_0402,
            R.drawable.info_0403,
            R.drawable.info_0404
    };


}