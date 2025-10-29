package com.example.musicalhealing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.musicalhealing.R;
import com.example.musicalhealing.JamendoApi;
import com.example.musicalhealing.JamendoResp;
import com.example.musicalhealing.JamendoTrack;
import com.example.musicalhealing.ServiceLocator;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;

    // Jamendo API Client ID
    private static final String JAMENDO_CLIENT_ID = "3d47fdb9";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerView = findViewById(R.id.playerView);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        String mood = getIntentExtraOrDefault(getIntent(), "mood", "happy");
        fetchAndPlay(mood);
    }

    private void fetchAndPlay(String mood) {
        android.util.Log.d("PlayerActivity", "=== Starting fetchAndPlay ===");
        android.util.Log.d("PlayerActivity", "Mood: " + mood);
        android.util.Log.d("PlayerActivity", "Client ID: " + JAMENDO_CLIENT_ID);

        JamendoApi api = ServiceLocator.jamendoApi();
        String query = expandQuery(mood);

        android.util.Log.d("PlayerActivity", "Expanded query: " + query);

        Call<JamendoResp<JamendoTrack>> call = api.tracks(
                JAMENDO_CLIENT_ID,
                "json",
                query,
                "licenses+musicinfo",
                "mp32",     // MP3 VBR; use "mp31" for 96kbps if you prefer
                30
        );

        call.enqueue(new Callback<JamendoResp<JamendoTrack>>() {
            @Override
            public void onResponse(Call<JamendoResp<JamendoTrack>> call, Response<JamendoResp<JamendoTrack>> response) {
                android.util.Log.d("PlayerActivity", "=== API Response Received ===");
                android.util.Log.d("PlayerActivity", "Response code: " + response.code());
                android.util.Log.d("PlayerActivity", "Response successful: " + response.isSuccessful());
                android.util.Log.d("PlayerActivity", "Response body null: " + (response.body() == null));

                if (response.body() != null) {
                    android.util.Log.d("PlayerActivity", "Results null: " + (response.body().results == null));
                    if (response.body().results != null) {
                        android.util.Log.d("PlayerActivity", "Number of tracks: " + response.body().results.size());
                    }
                }

                if (!response.isSuccessful() || response.body() == null || response.body().results == null) {
                    String errorMsg = "No tracks found. Code: " + response.code();
                    android.util.Log.e("PlayerActivity", errorMsg);
                    Toast.makeText(PlayerActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }

                List<MediaItem> mediaItems = mapToMediaItems(response.body().results);

                if (mediaItems.isEmpty()) {
                    Toast.makeText(PlayerActivity.this, "No playable tracks for this mood", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                player.setMediaItems(mediaItems);
                player.prepare();
                player.play();
            }

            @Override
            public void onFailure(Call<JamendoResp<JamendoTrack>> call, Throwable t) {
                String errorMsg = "Network error: " + (t.getMessage() != null ? t.getMessage() : "Unknown error");
                android.util.Log.e("PlayerActivity", "API call failed", t);
                Toast.makeText(PlayerActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private List<MediaItem> mapToMediaItems(List<JamendoTrack> tracks) {
        List<MediaItem> out = new ArrayList<>();

        for (JamendoTrack t : tracks) {
            if (t == null || t.audio == null || t.license_ccurl == null) continue;

            // Optional: if your app is commercial, skip Non-Commercial licenses
            // if (t.license_ccurl.contains("by-nc")) continue;

            MediaMetadata metadata = new MediaMetadata.Builder()
                    .setTitle(safe(t.name))
                    .setArtist(safe(t.artist_name))
                    .setArtworkUri(t.image != null ? Uri.parse(t.image) : null)
                    .build();

            MediaItem item = new MediaItem.Builder()
                    .setUri(t.audio)
                    .setMediaMetadata(metadata)
                    .build();

            out.add(item);
        }

        return out;
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static String getIntentExtraOrDefault(Intent intent, String key, String def) {
        if (intent == null) return def;
        String v = intent.getStringExtra(key);
        return (v == null || v.trim().isEmpty()) ? def : v.trim();
    }

    // ---- Simple Java version of "expandQuery" (no Kotlin) ----
    private static String expandQuery(String mood) {
        if (mood == null) mood = "";
        String key = mood.toLowerCase(Locale.US).trim();

        List<String> extras;
        switch (key) {
            case "happy":
                extras = Arrays.asList("uplifting", "feel good", "upbeat", "cheerful");
                break;
            case "sad":
                extras = Arrays.asList("melancholic", "somber", "emotional");
                break;
            case "calm":
                extras = Arrays.asList("ambient", "chill", "relaxing", "lofi");
                break;
            case "angry":
                extras = Arrays.asList("intense", "heavy", "energetic");
                break;
            default:
                extras = new ArrayList<>();
        }

        // Take the mood + up to 2 extras for a concise search
        List<String> picks = new ArrayList<>();
        if (!mood.isEmpty()) picks.add(mood);
        for (String e : extras) {
            if (picks.size() >= 3) break;
            picks.add(e);
        }
        return String.join(" ", picks);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (playerView != null) playerView.setPlayer(null);
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
