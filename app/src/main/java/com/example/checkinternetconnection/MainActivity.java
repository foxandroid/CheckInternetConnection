package com.example.checkinternetconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isConnected = false;
    Button checkInternet;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInternet = findViewById(R.id.Check_Internet);
        checkInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConnected){

                    Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();

                }else{

                    Intent i = new Intent(MainActivity.this,notconnected.class);
                    startActivity(i);
                    finish();

                }

            }
        });

    }

    private void registerNetworkCallback(){


        try {

            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){

                @Override
                public void onAvailable(@NonNull Network network) {
                    isConnected = true;
                }

                @Override
                public void onLost(@NonNull Network network) {
                    isConnected = false;
                }
            });




        }catch (Exception e){

            isConnected = false;

        }

    }

    private void unregisterNetworkCallback(){

        connectivityManager.unregisterNetworkCallback(new ConnectivityManager.NetworkCallback());

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkCallback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkCallback();
    }
}