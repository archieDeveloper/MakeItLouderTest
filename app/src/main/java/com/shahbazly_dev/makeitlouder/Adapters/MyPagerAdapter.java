package com.shahbazly_dev.makeitlouder.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shahbazly_dev.makeitlouder.Fragment_welcome;
import com.shahbazly_dev.makeitlouder.Playlist_fragment;
import com.shahbazly_dev.makeitlouder.Popular_fragment;
import com.shahbazly_dev.makeitlouder.Recomended_fragment;
import com.shahbazly_dev.makeitlouder.SongListFragment;


public  class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Возвращает число страниц
    @Override
    public int getCount() {
        return 3;
    }

    // Возвращает фрагмент для отображения
    @Override
    public Fragment getItem(int position) {
        return SongListFragment.newInstance(position, "Page " + (position+1));
    }

}
