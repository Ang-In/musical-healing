package com.example.musicalhealing;

import com.squareup.moshi.Moshi;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import com.example.musicalhealing.JamendoApi;

public class ServiceLocator {
    public static final String JAMENDO_BASE = "https://api.jamendo.com/v3.0/";

    private static JamendoApi jamendoApi;

    public static JamendoApi jamendoApi() {
        if (jamendoApi == null) {
            Moshi moshi = new Moshi.Builder().build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JAMENDO_BASE)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();
            jamendoApi = retrofit.create(JamendoApi.class);
        }
        return jamendoApi;
    }
}
