package com.abajlo.scorebatandroidprojekt_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private Button dohvatiUtakmice;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textView);
        dohvatiUtakmice =findViewById(R.id.button);

        dohvatiUtakmice.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ucitaj();
                counter++;
            }
        });
    }

    private void ucitaj() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.scorebat.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiSucelje apiSucelje = retrofit.create(ApiSucelje.class);
        Call<List<Odgovor>> call = apiSucelje.dohvatiUtakmice();
        call.enqueue(new Callback<List<Odgovor>>() {
            @Override
            public void onResponse(Call<List<Odgovor>> call, Response<List<Odgovor>> response) {

                if(!response.isSuccessful()){

                    textViewResult.setText("Code: " + response.code());
                    return ;
                }

                List<Odgovor> odgovori = response.body();

                textViewResult.setText("");

                    String content = "";
                    content += "Title: " + odgovori.get(counter).getTitle() + "\n";
                    content += "Copetition: " + odgovori.get(counter).getCompetition().getName()+ "\n";
                    content += odgovori.get(counter).getSide1().getName() + "  Vs   " + odgovori.get(counter).getSide2().getName() + "\n";
                    content += "Live stream link : " + odgovori.get(counter).getUrl();

                    textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<List<Odgovor>> call, Throwable t) {


            }
        });


    }
}
