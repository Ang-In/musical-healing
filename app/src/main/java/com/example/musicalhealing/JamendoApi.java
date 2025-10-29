package com.example.musicalhealing;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;
public interface JamendoApi {
    @GET("tracks")
    Call<JamendoResp<JamendoTrack>> tracks(
            @Query("client_id") String clientId,
            @Query("format") String format,
            @Query("search") String search,
            @Query("include") String include,
            @Query("audioformat") String audioFormat,
            @Query("limit") Integer limit
    );
}