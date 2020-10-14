package com.jay.viewpagerdemo2.adapter;

import android.content.Context;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> mFragmentList;
    public List<String> mFragmentListTitle;

    public TabPagerAdapter(Context context, FragmentManager fragmentManager, List<Fragment> viewPagerFragments, List<String> fragmentListTitle) {
        super(fragmentManager);
        this.mFragmentList = viewPagerFragments;
        this.mFragmentListTitle = fragmentListTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentListTitle.get(position);
    }

    /**
     * Delete a page at a position
     *
     * @param position item position
     */
    public void deletePage(int position) {
        try {
            // Remove the corresponding item in the data set
            mFragmentList.remove(position);
            // Notify the adapter that the data set is changed
            notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    /**
     * @param fragment Fragment object to be added
     * @param title    title of the fragment
     */
    public void addPage(Fragment fragment, String title) {
        if (!mFragmentList.contains(fragment)) {
            mFragmentList.add(fragment);
            mFragmentListTitle.add(title);
        }
        // Notify the adapter that the data set is changed
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        int index = mFragmentList.indexOf(object);

        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
