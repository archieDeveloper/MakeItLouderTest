package com.shahbazly_dev.makeitlouder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldoublem.loadingviewlib.LVCircularCD;
import com.shahbazly_dev.makeitlouder.Classes.Song;
import com.shahbazly_dev.makeitlouder.Adapters.SongsAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;


import java.util.ArrayList;
import java.util.List;


public class Playlist_fragment extends Fragment {

    protected List<Song> songList = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected SongsAdapter mAdapter;
    protected View view;
    protected LVCircularCD mLVCircularCD;
    protected VKApiAudio vkApiAudio;

    private String title;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.musiclist, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLVCircularCD = (LVCircularCD) view.findViewById(R.id.lv_circularCD);


        mAdapter = new SongsAdapter(songList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareSongData();
/*
        recyclerView.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getActivity(),
                recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Song song = songList.get(position);
                Toast.makeText(getActivity(),song.getArtist() + " - " + song.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Song song = songList.get(position);
                Toast.makeText(getActivity(), song.getSongUrl(),
                        Toast.LENGTH_SHORT).show();
            }
        }));
        */

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("Int", 0);
        title = getArguments().getString("Title");
    }

    public static Playlist_fragment newInstance(int page, String title) {
        Playlist_fragment playlist_fragment = new Playlist_fragment();
        Bundle args = new Bundle();
        args.putInt("Int", page);
        args.putString("Title", title);
        playlist_fragment.setArguments(args);
        return playlist_fragment;
    }

    private void prepareSongData() {
        mLVCircularCD.startAnim();
        final VKRequest songsRequest = VKApi.audio().get();
        songsRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                mLVCircularCD.stopAnim();
                mLVCircularCD.setVisibility(View.INVISIBLE);
                VKList<VKApiAudio> vkList = (VKList<VKApiAudio>)response.parsedModel;
                for(int i = 0; i < vkList.size(); i++) {
                    vkApiAudio = vkList.get(i);
                    Song song = new Song(vkApiAudio);
                    songList.add(song);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}



