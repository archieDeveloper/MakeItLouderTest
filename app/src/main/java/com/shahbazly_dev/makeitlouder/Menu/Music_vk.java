package com.shahbazly_dev.makeitlouder.Menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.shahbazly_dev.makeitlouder.Adapters.MyPagerAdapter;
import com.shahbazly_dev.makeitlouder.R;

public class Music_vk extends Fragment {

    View view;
    ViewPager mViewPager;
    NavigationTabStrip navigationTabStrip;
    FragmentPagerAdapter adapterViewPager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.activity_vkmusic, container, false);

        //Инициализация элементов экрана
        navigationTabStrip = (NavigationTabStrip) view.findViewById(R.id.nts);
        mViewPager = (ViewPager) view.findViewById(R.id.vp);

        adapterViewPager = new MyPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapterViewPager);

        //Настройка TabBar
        navigationTabStrip.setViewPager(mViewPager, 1);
        navigationTabStrip.setTitleSize(50);
        navigationTabStrip.setTitles("I'll like it", "Playlist", "Popular");
        navigationTabStrip.setAnimationDuration(200);

        return view;
    }

}