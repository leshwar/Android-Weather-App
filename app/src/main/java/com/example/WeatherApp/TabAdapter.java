package com.example.WeatherApp;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return DynamicFragment.addfrag(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

//    @Override
//    public int getItemPosition(Object object) {
//        System.out.println("In getItemPosition");
//        System.out.println(this.mNumOfTabs);
//
//        ArrayList<Fragment> mFragments = DynamicFragment.getFragmentsList();
//        Fragment mFragment = DynamicFragment.getFragment();
//        int index = mFragments.indexOf(mFragment);
//        System.out.println("Index");
//        System.out.println(index);
//        if(index < 0) {
//            return TabAdapter.POSITION_NONE;
//        }
//        else {
//            return TabAdapter.POSITION_UNCHANGED;
//        }
//
//    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        //System.out.println("In destroy");
//        super.destroyItem(container, position, object);
//        FragmentManager manager = ((Fragment) object).getFragmentManager();
//        FragmentTransaction trans = manager.beginTransaction();
//        trans.remove((Fragment) object);
//        trans.commit();
//    }

}
