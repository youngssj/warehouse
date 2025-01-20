package com.victor.base.base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class VPFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> mListFragments = null;

    public VPFragmentAdapter(FragmentActivity fm, List<Fragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (mListFragments != null && position > -1 && position < mListFragments.size()) {
            return mListFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }
}