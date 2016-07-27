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
import android.widget.Toast;

import com.ldoublem.loadingviewlib.LVCircularCD;
import com.shahbazly_dev.makeitlouder.Adapters.SongsAdapter;
import com.shahbazly_dev.makeitlouder.Classes.Song;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiAudio;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;


public class Popular_fragment extends Fragment {

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

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("Int", 0);
        title = getArguments().getString("Title");
    }

    public static Popular_fragment newInstance(int page, String title) {
        Popular_fragment popular_fragment = new Popular_fragment();
        Bundle args = new Bundle();
        args.putInt("Int", page);
        args.putString("Title", title);
        popular_fragment.setArguments(args);
        return popular_fragment;
    }

    private void prepareSongData() {
        mLVCircularCD.startAnim();
        final VKRequest songsRequest = VKApi.audio().getPopular();
        songsRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                mLVCircularCD.stopAnim();
                mLVCircularCD.setVisibility(View.INVISIBLE);

                for(int i = 0;i<((VKList<VKApiAudio>)response.parsedModel).size();i++){

                    vkApiAudio = ((VKList<VKApiAudio>)response.parsedModel).get(i);

                    String artist = vkApiAudio.artist;
                    String title = vkApiAudio.title;
                    String songUrl = vkApiAudio.url;

                    int songId = vkApiAudio.id;
                    int durationIdSec = vkApiAudio.duration;
                    long hours = (durationIdSec / 3600);
                    long minutes = ((durationIdSec % 3600) / 60);
                    long seconds = (durationIdSec % 60);
                    if(durationIdSec > 3600){
                        String duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                        Song song = new Song(songId,title,artist,durationIdSec,duration,songUrl);
                        songList.add(song);
                    }else {
                        String duration = String.format("%02d:%02d", minutes, seconds);

                        Song song = new Song(songId,title,artist,durationIdSec,duration,songUrl);
                        songList.add(song);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}



