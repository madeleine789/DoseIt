package com.android_camp.doseit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android_camp.doseit.fragments.FragmentDummy;
import com.android_camp.doseit.fragments.FragmentParameters;
import com.android_camp.doseit.fragments.FragmentResult;
import com.android_camp.doseit.fragments.SearchbarFragment;

public class SwipeActivity extends FragmentActivity
        implements SearchbarFragment.CallbackFromSearchFragment
{

    private static final int NO_SWIPER_PAGES = 3;
    private Medicine mSelectedMed;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private double mResult;
    private FragmentResult frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        //initToolBar();
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onSelectedMedicine(Medicine m) {
        mSelectedMed = m;
        mResult = 10; //computeResult();
        // Create new fragment and transaction
        //Fragment f = getSupportFragmentManager().findFragmentById(R.id.result_fragment);
        // Fragment f = (FragmentResult) getSupportFragmentManager().findFragmentById(R.id.result_fragment);

            /*if(f == null)
                System.out.println("ERROR");
            ((FragmentResult)f).answer = 10;
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_search, f).commit();
            System.out.println("Suntem in Swipe" + mSelectedMed.name);*/

        setCurrentItem(2,true);
        frag.setResult(10);
        frag.setMedName(m.name);
        frag.setWarning(m.warningMessage);

        // current fragment

        //        ((FragmentResult) f).setResult(mResult);
    }

    public double computeResult() {
        double concentration = mSelectedMed.concentration;
        double dose = mSelectedMed.dose;
        double kidDose = mSelectedMed.kidDose;
        return 0;
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
                    fragment = new FragmentParameters();
                    break;
                case 1:
                    fragment = new SearchbarFragment();
                    break;
                case 2:
                    frag = new FragmentResult();
                    fragment = frag;
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

}
