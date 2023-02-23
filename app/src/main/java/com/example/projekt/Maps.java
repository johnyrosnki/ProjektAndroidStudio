package com.example.projekt;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Maps extends AppCompatActivity {
    EditText etSource,etDestination;
    Button btTrack;
    Button btn_back;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        etSource =findViewById(R.id.et_source);
        etDestination =findViewById(R.id.et_destination);
        btTrack =findViewById(R.id.bt_track);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this :: openMainActivity);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSource=etSource.getText().toString().trim();
                String sDestination=etDestination.getText().toString().trim();
                if(sSource.equals("")&& sDestination.equals("")) {
                    Toast.makeText(getApplicationContext()
                            ,"Enter both location",Toast.LENGTH_SHORT).show();
                }
                else{
                    DisplayTrack(sSource,sDestination);
                }
                }


        });
    }

    private void openMainActivity(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void DisplayTrack(String sSource, String sDestination) {
        try{
            Uri uri =Uri.parse("https://www.google.co.in/maps/dir/"+sSource+"/"+sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri =Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.map");
            Intent intent = new Intent (Intent.ACTION_VIEW);
            startActivity(intent);

        }
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
}
