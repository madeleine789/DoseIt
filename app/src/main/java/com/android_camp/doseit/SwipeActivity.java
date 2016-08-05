package com.android_camp.doseit;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.android_camp.doseit.fragments.FragmentDummy;
import com.android_camp.doseit.fragments.FragmentParameters;
import com.android_camp.doseit.fragments.FragmentResult;
import com.android_camp.doseit.fragments.FragmentSearchbar;

//<<<<<<< HEAD
//public class SwipeActivity extends BaseActivity implements FragmentParameters.callBack,
//        FragmentSearchbar.CallbackFromSearchFragment {
//=======

public class SwipeActivity extends BaseActivity implements FragmentParameters.ParametersCallBack,
        FragmentSearchbar.MedicineCallBack, FragmentParameters.callBack {

    private static final int NO_SWIPER_PAGES = 3;
    private Medicine mSelectedMed;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private double mResult;

    private Parameter mParameter;

    SwipeActivity() {
        mParameter = new Parameter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMainActivity = false;
        super.onCreate(savedInstanceState);
        Log.i("SwipeActivity", "create");

        setContentView(R.layout.activity_swipe);
        initToolBar();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
        //mParameter = new Parameter();

    }

    @Override
    public void onSelectedMedicine(Medicine m) {
        mSelectedMed = m;
        mResult = mSelectedMed.computeResult(mParameter.age, mParameter.height, mParameter.weight);
        mViewPagerAdapter.setResult(mSelectedMed, mResult);
        setCurrentItem(2,true);

    }

    public void setCurrentItem (int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        static FragmentResult mResultFrag = null;
        static FragmentParameters mParametersFrag = null;
        static FragmentSearchbar mSearchbarFrag = null;



        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.i("ViewPagerAdapter", "create");

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    if(mParametersFrag == null){
                        mParametersFrag = new FragmentParameters();
                    }
                    fragment = mParametersFrag;
                    break;
                case 1:
                    if(mSearchbarFrag == null) {
                        mSearchbarFrag = new FragmentSearchbar();
                    }
                    fragment = mSearchbarFrag;
                    break;
                case 2:
                    if(mResultFrag == null) {
                        mResultFrag = new FragmentResult();
                    }
                    fragment = mResultFrag;
                    break;
                default:
                    fragment = new FragmentDummy();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NO_SWIPER_PAGES;
        }

        public void setResult(Medicine m, double dose) {
            mResultFrag.setMedName(m.name);
            mResultFrag.setResult(dose);
            mResultFrag.setWarning(m.warningMessage);
        }
    }

    public void getParameters(Parameter p, boolean itPressed) {
        if (p != null) {
            mParameter = p;
            if (mParameter != null &&
                    !mParameter.gender.equals("value") &&
                    !mParameter.age.equals("value") &&
                    mParameter.height != -1 &&
                    mParameter.weight != -1) {
                setCurrentItem(1, true);
            }
        }
    }
    public void setParameters(Parameter p) {
        mParameter = p;
    }
}