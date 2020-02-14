package com.abajlo.scorebatandroidprojekt_1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSucelje {

    @GET("video-api/v1/")
    Call<List<Odgovor>> dohvatiUtakmice();
}
