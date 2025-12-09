package com.example.myapplication;
//tfhjk
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    List<Pytanie> pytania;
    private TextView textViewTresc;
    private RadioButton radioButtonOdpa, radioButtonOdpb, radioButtonOdpc;
    private Button buttonDalej;
    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textViewTresc = findViewById(R.id.textViewTresc);
        radioButtonOdpa = findViewById(R.id.radioButtonOdpa);
        radioButtonOdpb = findViewById(R.id.radioButtonOdpb);
        radioButtonOdpc = findViewById(R.id.radioButtonOdpc);
        buttonDalej = findViewById(R.id.buttonDalej);
        p = 0;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/wojtekk00/retrofit_pytania/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Pytanie>> call = jsonPlaceHolderApi.getPytania();
        call.enqueue(
                new Callback<List<Pytanie>>() {
                    @Override
                    public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(MainActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        p = 0;
                        pytania = response.body();
                        textViewTresc.setText(pytania.get(p).getTrescPytania());
                        radioButtonOdpa.setText(pytania.get(p).getOdpa());
                        radioButtonOdpb.setText(pytania.get(p).getOdpb());
                        radioButtonOdpc.setText(pytania.get(p).getOdpc());
                        buttonDalej.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                p += 1;
                                textViewTresc.setText(pytania.get(p).getTrescPytania());
                                radioButtonOdpa.setText(pytania.get(p).getOdpa());
                                radioButtonOdpb.setText(pytania.get(p).getOdpb());
                                radioButtonOdpc.setText(pytania.get(p).getOdpc());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Pytanie>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}