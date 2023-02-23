package com.example.projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ClipData;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Weather extends AppCompatActivity {

    EditText et;
    TextView tv;
    String url ="https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey="18d00bf8d8ad769523635ae63ec12ec4";
    LocationManager manager;
    LocationListener locationListener;
    Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this :: openMainActivity);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.weather:
                Intent weather = new Intent(this,Weather.class);
                startActivity(weather);
                return true;
            case R.id.youtube:
                Intent youtube = new Intent(this,Youtube.class);
                startActivity(youtube);
                return true;
            case R.id.menu3:
                Intent menu3 = new Intent(this,List.class);
                startActivity(menu3);
                return true;
            case R.id.menu5:
                Intent menu5 = new Intent(this,Maps.class);
                startActivity(menu5);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void openMainActivity(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void getweather(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<Example> examplecall=myapi.getweather(et.getText().toString().trim(),apikey);
        examplecall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code()==404){
                    Toast.makeText(Weather.this, "Please Enter a valid City", Toast.LENGTH_SHORT).show();
                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(Weather.this, response.code(), Toast.LENGTH_SHORT).show();
                }
                Example mydata = response.body();
                Main main =mydata.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int)(temp-273.15);
                tv.setText(String.valueOf(temperature)+"C");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(Weather.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
