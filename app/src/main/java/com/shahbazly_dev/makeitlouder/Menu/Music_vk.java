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
import com.shahbazly_dev.makeitlouder.Adapters.PagerAdapter;
import com.shahbazly_dev.makeitlouder.R;

import co.mobiwise.library.ProgressLayout;

public class Music_vk extends Fragment {

    View view;
    ViewPager mViewPager;
    NavigationTabStrip navigationTabStrip;
    FragmentPagerAdapter adapterViewPager;
    public ProgressLayout progressLayout;

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
        progressLayout = (ProgressLayout)view.findViewById(R.id.progLay);

        //Настройка Адаптера
        adapterViewPager = new PagerAdapter(getFragmentManager());
        mViewPager.setAdapter(adapterViewPager);

        //Настройка TabBar'а
        navigationTabStrip.setViewPager(mViewPager, 1);
        navigationTabStrip.setTitleSize(50);
        navigationTabStrip.setTitles("Suggestions", "Playlist", "Popular");
        navigationTabStrip.setAnimationDuration(200);
        navigationTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                //
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });

        return view;
    }

}