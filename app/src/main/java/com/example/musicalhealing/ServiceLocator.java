package com.example.musicalhealing;

import com.squareup.moshi.Moshi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import com.example.musicalhealing.JamendoApi;

public class ServiceLocator {
    public static final String JAMENDO_BASE = "https://api.jamendo.com/v3.0/";

    private static JamendoApi jamendoApi;

    public static JamendoApi jamendoApi() {
        if (jamendoApi == null) {
            // Add logging interceptor to see API requests/responses
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message ->
                android.util.Log.d("API", message)
            );
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Moshi moshi = new Moshi.Builder().build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JAMENDO_BASE)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();
            jamendoApi = retrofit.create(JamendoApi.class);
        }
        return jamendoApi;
    }
}
