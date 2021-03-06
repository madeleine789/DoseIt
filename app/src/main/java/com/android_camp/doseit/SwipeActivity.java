package com.android_camp.doseit;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.android_camp.doseit.fragments.FragmentDummy;
import com.android_camp.doseit.fragments.FragmentParameters;
import com.android_camp.doseit.fragments.FragmentResult;
import com.android_camp.doseit.fragments.FragmentSearchbar;

public class SwipeActivity extends BaseActivity implements FragmentParameters.callBack,
        FragmentSearchbar.CallbackFromSearchFragment {
    private static final int NO_SWIPER_PAGES = 3;
    private Medicine mSelectedMed;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private double mResult;
    private FragmentResult mResultFrag;
    private FragmentParameters mParametersFrag;
    private Parameter mParameter;

    SwipeActivity() {
        mParameter = new Parameter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMainActivity = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        initToolBar();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }
    @Override
    public void onSelectedMedicine(Medicine m) {
        mSelectedMed = m;
        mResult = mSelectedMed.computeResult(mParameter.age, mParameter.height, mParameter.weight);
        setCurrentItem(2,true);
        mResultFrag.setResult(mResult);
        mResultFrag.setMedName(mSelectedMed.name);
        mResultFrag.setWarning(mSelectedMed.warningMessage);
    }
    public void setCurrentItem (int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(item, smoothScroll);
    }
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    mParametersFrag = new FragmentParameters();
                    fragment = mParametersFrag;
                    break;
                case 1:
                    fragment = new FragmentSearchbar();
                    break;
                case 2:
                    mResultFrag = new FragmentResult();
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
    }
    @Override
    public void getParameters(Parameter p, boolean itPressed) {
        if( p != null) {
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
}