package com.android_camp.doseit.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by demouser on 8/4/16.
 */
public class ListAdapter extends BaseAdapter {

    private ArrayList<String> mMedicineList;
    private Context mContext;

    public ListAdapter(Context ctx) {
        mMedicineList = new ArrayList<String>();
        mMedicineList.add("Rubbish #1");
        mMedicineList.add("Rubbish #2");
        mMedicineList.add("Rubbish #3");
        mMedicineList.add("Rubbish #4");
        mMedicineList.add("Rubbish #5");
        mContext = ctx;
    }

    public void setMedicineList(ArrayList<String> list) {
        mMedicineList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMedicineList.size();
    }

    @Override
    public Object getItem(int i) {
        return mMedicineList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mMedicineList.hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tv;
        if (view == null) {
            tv = new TextView(mContext);
        } else {
            tv = (TextView) view;
        }
        tv.setTextSize(30);
        tv.setText(getItem(i).toString());
        return tv;
    }
}
