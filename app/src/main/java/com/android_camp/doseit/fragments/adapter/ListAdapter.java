package com.android_camp.doseit.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android_camp.doseit.Medicine;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Medicine> mOriginalList;
    private ArrayList<Medicine> mMedicineList;
    private Context mContext;
    private Filter mFilter;
    private boolean onTablet;

    public ListAdapter(Context ctx) {
        mFilter = new MyFilter();
        mContext = ctx;
    }

    public void reset() {
        mMedicineList = mOriginalList;
    }

    public void setMedicineList(ArrayList<Medicine> list) {
        if (mOriginalList == null) {
            mOriginalList = list;
        }
        mMedicineList = list;
        notifyDataSetChanged();
    }
    public ArrayList<Medicine> getMedicineList() {
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
        tv.setText(((Medicine)getItem(i)).name);
        tv.setPadding(10,10,10,10);
        if (onTablet) tv.setTextSize(30);
        else tv.setTextSize(15);
        return tv;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public Medicine getMedicine(String res) {

        for(int i = 0; i < mMedicineList.size(); i++){
            Medicine m = mMedicineList.get(i);
            if(res.equals(m.name.toLowerCase())){
                return m;
            }

        }
        return null;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            ArrayList<Medicine> newList = new ArrayList<>();
            for (Medicine med : mMedicineList) {
                String medicine = med.name;
                if (medicine.toLowerCase().contains(charSequence.subSequence(0, charSequence.length() - 1).toString().toLowerCase())) {
                    newList.add(med);

                }
            }
            
            result.values = newList;
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            setMedicineList((ArrayList<Medicine>)filterResults.values);

        }
    }

    public void setOnTablet(boolean onTablet) {
        this.onTablet = onTablet;
    }
}
