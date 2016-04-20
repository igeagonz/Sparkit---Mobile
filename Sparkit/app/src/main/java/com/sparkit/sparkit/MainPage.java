package com.sparkit.sparkit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nacho on 3/17/16.
 */
public class MainPage extends Activity {

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        String welcomeMessage = null;

        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                email = null;
                welcomeMessage = null;
            } else {
                email = extras.getString("email");
                welcomeMessage = extras.getString("welcomeMessage");
            }
        }
        else{
            email = (String)savedInstanceState.getSerializable("email");
            welcomeMessage = (String)savedInstanceState.getSerializable("welcomeMessage");
        }

        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setText(welcomeMessage);

    }

    public void onLogout(View view){
        startActivity(new Intent(MainPage.this, LoginActivity.class));
    }

    public void onPost(View view){
        startActivity(new Intent(MainPage.this, CreatePosting.class));
    }

    public void onSearch(View view){
        //Call BackgroundTaskSearch in order to fetch array from database and pre-populate the google maps fragment

        BackgroundTaskSearch backgroundTaskSearch = new BackgroundTaskSearch(this);
        backgroundTaskSearch.execute(this);

        //End BackgroundTaskSearch activity




        //startActivity(new Intent(MainPage.this, MapsActivity.class));
    }

    public void goToSearch(ArrayList<String> result){
        finish();
        Intent intent = new Intent(MainPage.this, MapsActivity.class);
        intent.putExtra("addressList", result);
        startActivity(intent);
    }

}
