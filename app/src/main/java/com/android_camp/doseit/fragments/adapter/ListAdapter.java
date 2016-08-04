package com.android_camp.doseit.fragments.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by demouser on 8/4/16.
 */
public class ListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<String> mMedicineList;
    private Context mContext;
    private Filter mFilter;

    public ListAdapter(Context ctx) {
        mMedicineList = new ArrayList<String>();
        mFilter = new MyFilter();
        mMedicineList.add("Rubbish #1");
        mMedicineList.add("Rubbiandsh #2");
        mMedicineList.add("Rubbish #3");
        mMedicineList.add("Rubbish #4");
        mMedicineList.add("Rubbish #5");
        mContext = ctx;
    }

    public void setMedicineList(ArrayList<String> list) {
        mMedicineList = list;
        notifyDataSetChanged();
    }

    public ArrayList<String> getMedicineList() {
        return mMedicineList;
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            ArrayList<String> newList = new ArrayList<>();
            for (String medicine : mMedicineList) {
                if (medicine.toLowerCase().contains(charSequence.subSequence(0, charSequence.length() - 1).toString().toLowerCase())) {
                    newList.add(medicine);
                }
            }
            
            result.values = newList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            setMedicineList((ArrayList<String>)filterResults.values);
        }
    }
}
