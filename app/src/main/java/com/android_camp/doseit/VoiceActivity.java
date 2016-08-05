package com.android_camp.doseit;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android_camp.doseit.fragments.FragmentParameters;
import com.android_camp.doseit.fragments.FragmentResult;
import com.android_camp.doseit.fragments.FragmentSearchbar;


public class VoiceActivity extends BaseActivity implements FragmentParameters.Swipe, FragmentSearchbar.MedicineCallBack, FragmentParameters.ParametersCallBack {


    private SwipeActivity.ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private Parameter mParameter;
    private double dose;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i("VoiceActivity", "create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        initToolBar();

        mParameter = new Parameter();
        mViewPagerAdapter = new SwipeActivity.ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1) {
                    FragmentSearchbar fsb = (FragmentSearchbar) mViewPagerAdapter.getItem(1);
                    fsb.start();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void moveToNext(int currentScreen) {
        mViewPager.setCurrentItem(currentScreen, true);

    }

    @Override
    public void onSelectedMedicine(Medicine m) {
        dose = m.computeResult(mParameter.age, mParameter.height, mParameter.weight);
        mViewPagerAdapter.setResult(m, dose);
    }


    @Override
    public void setParameters(Parameter p) {
        mParameter.setAge(p.age);
        mParameter.setGender(p.gender);
        mParameter.setWeight(p.weight);
    }
}

