package com.shahbazly_dev.makeitlouder.Classes;

import com.vk.sdk.api.model.VKApiAudio;

import java.util.Locale;

public class Song {

    private String title, artist, duration, songUrl;
    private int songId;
    private int durationInSec;
    private boolean isPlaying = false;

    public Song(VKApiAudio vkApiAudio) {
        this.songId = vkApiAudio.id;
        this.title = vkApiAudio.title;
        this.artist = vkApiAudio.artist;
        this.songUrl = vkApiAudio.url;
        this.durationInSec = vkApiAudio.duration;

        long hours = (this.durationInSec / 3600);
        long minutes = ((this.durationInSec % 3600) / 60);
        long seconds = (this.durationInSec % 60);

        this.duration = this.durationInSec > 3600
                ? String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds)
                : String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public Song(int songId, String title, String artist, int durationInSec, String duration, String songUrl) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.durationInSec = durationInSec;
        this.duration = duration;
        this.songUrl = songUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String url) {
        this.songUrl = url;
    }

    public int getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(int durationInSec) {
        this.durationInSec = durationInSec;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}

