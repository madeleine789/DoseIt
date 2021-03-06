package com.android_camp.doseit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.android_camp.doseit.Parameter;
import com.android_camp.doseit.R;
import android.view.View.OnClickListener;
import android.widget.TextView;

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
        if (height != null)
            height.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    double height = (s.toString().isEmpty()) ? -1 : Double.parseDouble(s.toString());
                    p.setHeight(height);
                    myListner.getParameters(p, true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        weight = (EditText) getActivity().findViewById(R.id.weight);
        if (weight != null)
            weight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    double weight = (textView.getText().toString().isEmpty()) ? -1 :
                            Double.parseDouble(textView.getText().toString());
                    p.setWeight(weight);

                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_GO) {
                        itPressed = true;
                    }
                    myListner.getParameters(p, itPressed);
                    return true;
                }
            });
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        if(activity instanceof callBack){
            myListner = (callBack) activity;
        }

    }

}
