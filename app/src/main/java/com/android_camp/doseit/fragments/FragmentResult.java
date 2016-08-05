package com.android_camp.doseit.fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android_camp.doseit.R;

public class FragmentResult extends Fragment {
    private double answer = 0;
    private String name = "";
    private String warn = "";
    private TextView mResult, mMedicine, mWarning;
    private boolean onTablet;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        Button b = (Button) view.findViewById(R.id.forTablet1);
        onTablet = (b != null);
        System.out.println(onTablet);

        mResult = (TextView) view.findViewById(R.id.result_value);
        mMedicine = (TextView) view.findViewById(R.id.medicin_name);
        mWarning = (TextView) view.findViewById(R.id.warning);

        mResult.setText(String.valueOf(answer));
        mMedicine.setText(name);
        mWarning.setText(warn);

        if(onTablet) {
            System.out.println("here");
            final ValueAnimator animator = ValueAnimator.ofFloat(10, 30);
            animator.setDuration(100);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mWarning.setTextSize((Float) valueAnimator.getAnimatedValue());
                }
            });


            mWarning.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            animator.start();
                            break;
                    }
                    return false;
                }
            });
        }
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

    public double getAnswer() {
        return answer;
    }

    public String getName() {
        return name;
    }

    public String getWarn() {
        return warn;
    }
}
