package com.android_camp.doseit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.android_camp.doseit.R;

/**
 * Created by demouser on 8/4/16.
 */
public class FragmentResult extends Fragment {
    private double answer = 0;
    private String name = "";
    private String warn = "";
    private TextView mResult, mMedicine, mWarning;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(container.getWindowToken(), 0);

        mResult = (TextView) view.findViewById(R.id.result_value);
        mMedicine = (TextView) view.findViewById(R.id.medicin_name);
        mWarning = (TextView) view.findViewById(R.id.warning);

        mResult.setText(String.valueOf(answer));
        mMedicine.setText(name);
        mWarning.setText(warn);
        return view;
    }

    public void setWarning(String warining) {
        this.warn = warining;
        if(mWarning != null)
        {
            mWarning.setText(String.valueOf(warn));
        }
    }

    public void setMedName(String name)
    {
        this.name = name;
        if(mMedicine != null)
        {
            mMedicine.setText(String.valueOf(this.name));
        }
    }

    public void setResult(double result) {
        System.out.println("here");
       answer = result;
        if(mResult != null)
        {
            mResult.setText(String.format("%.5g%n", result) + " mg");
        }
    }
}
