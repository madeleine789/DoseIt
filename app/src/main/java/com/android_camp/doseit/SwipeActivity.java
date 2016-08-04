package com.android_camp.doseit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.android_camp.doseit.fragments.FragmentDummy;
import com.android_camp.doseit.fragments.FragmentMedsList;
import com.android_camp.doseit.fragments.FragmentParameters;
import com.android_camp.doseit.fragments.FragmentResult;
import com.android_camp.doseit.fragments.SearchbarFragment;

public class SwipeActivity extends BaseActivity implements FragmentParameters.callBack{

    private static final int NO_SWIPER_PAGES = 3;

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private Parameter mParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        initToolBar();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
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
                    fragment = new FragmentParameters();
                    break;
                case 1:
                    fragment = new SearchbarFragment();
                    break;
                case 2:
                    fragment = new FragmentResult();
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
    public void getParameters(Parameter p) {
        mParameter = p;
    }

}
