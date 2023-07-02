package com.example.clientgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.clientgps.classes.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //String ip="http://192.168.0.107:8082/"; // acasa
    String ip ="http://192.168.43.103:8082/"; // hot-spot
    User user;
    ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void PlayButton(View view) {

        boolean error=false;

        TextView textViewUser= findViewById(R.id.username_id);
        String userName= textViewUser.getText().toString();

        TextView textViewPassword= findViewById(R.id.password_id);
        String password= textViewPassword.getText().toString();

        if(userName.length()>10){
            textViewUser.setError("Not feasible");
            error=true;
        }

        if(!error == true){

            //Verify Auth in server
            String completeLoginUrl=ip+"user/login?username="+userName+"&password="+password;
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest objRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    completeLoginUrl,
                    null,
                    response -> {

                        objectMapper = new ObjectMapper();
                        String userString = response.toString();

                        try {

                            //get User
                            user = objectMapper.readValue(userString, new TypeReference<User>(){});

                            //Storage userId....

                            SharedPreferences prefs;
                            prefs = this.getSharedPreferences("UserData",
                                    Context.MODE_PRIVATE);
                            // Initialize the mEditor ready
                            SharedPreferences.Editor mEditor = prefs.edit();

                            //......

                            mEditor.putInt("user_id",user.getId().intValue());
                            mEditor.commit();

                            startActivity(new Intent(this,InsertCoordinatesActivity.class));

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    },
                    error1 -> {

                    });

            requestQueue.add(objRequest);

        }
    }
}