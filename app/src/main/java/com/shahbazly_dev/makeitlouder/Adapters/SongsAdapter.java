package com.shahbazly_dev.makeitlouder.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shahbazly_dev.makeitlouder.Classes.Song;
import com.shahbazly_dev.makeitlouder.R;

import java.util.List;

import co.mobiwise.library.ProgressLayout;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    private List<Song> songsList;
    private Song currentSong;
    private int currentDuration = 0;
    private boolean isPlaying = false;
    private static final int SECOND_MS = 1000;
    private Context context;

    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override public void run() {
            currentDuration += 1;
            mHandler.postDelayed(mRunnable, SECOND_MS);
        }
    };

    public SongsAdapter(List<Song> songsList) {
        this.songsList = songsList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Song song = songsList.get(position);

        holder.title.setText(song.getTitle());
        holder.artist.setText(song.getArtist());
        holder.duration.setText(song.getDuration());
        holder.progressLayout.setMaxProgress(song.getDurationInSec());
        holder.playStopView.setBackgroundResource(R.drawable.play);

        if (currentSong != null && currentSong == song) {
            holder.playStopView.setBackgroundResource(
                    isPlaying ? R.drawable.pause : R.drawable.play);
            holder.progressLayout.setCurrentProgress(currentDuration);
            if (isPlaying) holder.progressLayout.start();
        } else {
            holder.progressLayout.cancel();
        }

        holder.playStopView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                if (song != currentSong) {
                    currentSong = song;
                    mHandler.removeCallbacks(mRunnable);
                    currentDuration = 0;
                }

                if (!holder.progressLayout.isPlaying()) {
                    isPlaying = true;
                    holder.progressLayout.start();
                    mHandler.postDelayed(mRunnable, 0);
                    holder.playStopView.setBackgroundResource(R.drawable.pause);
                    notifyDataSetChanged();
                } else {
                    isPlaying = false;
                    holder.progressLayout.stop();
                    mHandler.removeCallbacks(mRunnable);
                    holder.playStopView.setBackgroundResource(R.drawable.play);
                    notifyDataSetChanged();
                }
            }
        });
        holder.download_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,song.getSongUrl(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.progressLayout.setProgressLayoutListener(new ProgressLayout.ProgressLayoutListener() {
            @Override public void onProgressCompleted() {
                holder.playStopView.setBackgroundResource(R.drawable.play);
            }

            @Override public void onProgressChanged(int seconds) {
                holder.duration.setText(calculateSongDuration(seconds));
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, artist, duration;
        public ProgressLayout progressLayout;
        public ImageView playStopView;
        public ImageView download_img;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            artist = (TextView) view.findViewById(R.id.artist);
            duration = (TextView) view.findViewById(R.id.duration);
            progressLayout = (ProgressLayout)view.findViewById(R.id.progLay);
            playStopView = (ImageView)view.findViewById(R.id.imageviewAction);
            download_img = (ImageView)view.findViewById(R.id.img_dovnload);
        }
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    private String calculateSongDuration(int seconds) {
        return new StringBuilder(String.valueOf(seconds / 60))
                .append(":")
                .append(String.valueOf(seconds % 60))
                .toString();
    }
}