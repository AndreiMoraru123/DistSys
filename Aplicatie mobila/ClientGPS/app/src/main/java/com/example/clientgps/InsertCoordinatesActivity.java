package com.example.clientgps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class InsertCoordinatesActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    //String ip="http://192.168.0.107:8082/"; // acasa
    String ip ="http://192.168.43.103:8082/"; // hot-spot
    String latitude;
    String longitude;
    String terminalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_coordinates);
    }

    private void getCoordinates() {
        //Get Location

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation(this);

        } else {


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

            while (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED))
            {}

            getLocation(this);
        }
    }

    private void getLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location =task.getResult();
                if(location!=null){

                    Geocoder geocoder=new Geocoder(context, Locale.getDefault());
                    RequestQueue requestQueue = Volley.newRequestQueue(context);

                    latitude= String.valueOf(location.getLatitude());
                    longitude= String.valueOf(location.getLongitude());

                    //Get userId....
                    SharedPreferences prefs;
                    prefs = context.getSharedPreferences("UserData",
                            Context.MODE_PRIVATE);
                    // Initialize the mEditor ready
                    //......

                    terminalId = String.valueOf(prefs.getInt("user_id",0));

                    String url=ip+"/position/createPositionForMobile?latitude="+ latitude+"&longitude="+longitude +"&terminalId="+ terminalId;

                    JsonObjectRequest objRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            response -> {

                            },
                            error -> {

                            });

                    requestQueue.add(objRequest);
                }
            }
        });
    }

    public void PlayButton(View view) {
        getCoordinates();
    }
}
