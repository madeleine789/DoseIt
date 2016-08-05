package com.android_camp.doseit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android_camp.doseit.Parameter;
import com.android_camp.doseit.R;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.view.View.OnFocusChangeListener;
import android.widget.Toast;

public class FragmentParameters extends Fragment  {

    RadioGroup gender;
    RadioGroup age;
    EditText height;
    EditText weight;
    public boolean itPressed = false;
    Parameter p = new Parameter();

    private callBack myListner;

    public interface callBack{
        public void getParameters(Parameter p, boolean t);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_parameters, container, false);
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();

        age = (RadioGroup) getActivity().findViewById(R.id.age_group);
        //Log.d("MSC",age==null?"null":"ago");
        if (age != null) {
            age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String age = "adult";
                    switch (checkedId) {
                        case R.id.adult:
                            age = "adult";
                            break;
                        case R.id.kid:
                            age = "kid";
                            break;
                    }
                    p.setAge(age);
                    Log.d("MSC","SetAge");
                    myListner.getParameters(p, true);
                }
            });
        }

        gender = (RadioGroup) getActivity().findViewById(R.id.gender_group);
        if (gender != null) {
            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String gender = "female";
                    switch (checkedId) {
                        case R.id.male:
                            gender = "male";
                            break;
                        case R.id.female:
                            gender = "female";
                            break;
                    }
                    p.setGender(gender);
                    myListner.getParameters(p, true);
                }
            });
        }


        height = (EditText) getActivity().findViewById(R.id.height);
        if (height != null) {
            height.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View textView, boolean hasFocus) {
                    if (!hasFocus) {
                        if (textView != null) {
                            String h = ((TextView)textView).getText().toString();
                            if (!h.isEmpty() && h.matches("[0-9.]*")) {
                                double height = Double.parseDouble(h);
                                if (height > 80 && height < 250) {
                                    p.setHeight(height);
                                    myListner.getParameters(p, true);
                                    return;
                                }
                            }
                            height.setText("");
                            Toast toast = Toast.makeText(getActivity(),"Height should be a real number between 80 and 250",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            });
            height.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    height.setText("");
                }
            });
        }

        weight = (EditText) getActivity().findViewById(R.id.weight);
        if (weight != null) {
            weight.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View textView, boolean hasFocus) {
                    if (!hasFocus) {
                        if (textView != null) {
                            String h = ((TextView)textView).getText().toString();
                            if (!h.isEmpty() && h.matches("[0-9.]*")) {
                                double weight = Double.parseDouble(h);
                                if (weight > 20 && weight < 250) {
                                    p.setWeight(weight);
                                    myListner.getParameters(p, true);
                                    return;
                                }
                            }
                            weight.setText("");
                            Toast toast = Toast.makeText(getActivity(),"Weight should be a real number between 20 and 250",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            });
            weight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    weight.setText("");
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(activity instanceof callBack){
            myListner = (callBack) activity;
        }

    }

}
