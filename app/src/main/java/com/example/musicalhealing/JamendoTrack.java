package com.example.musicalhealing;
import java.util.Map;

public class JamendoTrack {
    public String id;
    public String name;
    public String artist_name;
    // cover URL
    public String image;
    // streamable MP3 URL
    public String audio;
    public Boolean audiodownload_allowed;
    public String license_ccurl;
    public MusicInfo musicinfo;

    public static class MusicInfo {
        // tags is an object with genre/instrument keys, not an array
        public Map<String, Object> tags;
    }
}