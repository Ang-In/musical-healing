package com.example.musicalhealing;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import com.example.musicalhealing.JamendoApi;
import com.example.musicalhealing.JamendoResp;
import com.example.musicalhealing.JamendoTrack;

public class PlaylistRepository {
    private final JamendoApi api;
    private final String clientId;

    public PlaylistRepository(@NonNull JamendoApi api, @NonNull String clientId) {
        this.api = api;
        this.clientId = clientId;
    }

    public List<MediaItem> fetchByMoodBlocking(String mood) throws IOException {
        String q = MoodUtils.expandQuery(mood);
        Response<JamendoResp<JamendoTrack>> resp = api.tracks(
                clientId,
                "json",
                q,
                "licenses+musicinfo",
                "mp32",          // or "mp31" for 96kbps
                30
        ).execute();

        List<MediaItem> out = new ArrayList<>();
        if (!resp.isSuccessful() || resp.body() == null || resp.body().results == null) {
            return out;
        }

        for (JamendoTrack t : resp.body().results) {
            if (t == null || t.audio == null || t.license_ccurl == null) continue;

            // Optional: skip NonCommercial if your app is commercial
            // if (t.license_ccurl.contains("by-nc")) continue;

            MediaMetadata metadata = new MediaMetadata.Builder()
                    .setTitle(t.name)
                    .setArtist(t.artist_name)
                    .setArtworkUri(t.image != null ? Uri.parse(t.image) : null)
                    .setExtras(new Bundle())
                    .build();

            metadata.extras.putString("licenseUrl", t.license_ccurl);

            MediaItem item = new MediaItem.Builder()
                    .setUri(t.audio)
                    .setMediaMetadata(metadata)
                    .build();

            out.add(item);
        }
        return out;
    }
}
