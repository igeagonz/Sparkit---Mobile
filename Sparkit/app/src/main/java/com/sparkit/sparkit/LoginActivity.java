package com.sparkit.sparkit;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.*;
import android.content.*;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    EditText ET_email, ET_password;
    String email, password, welcomeMessage;
    ArrayList<String> reserve_results = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_email = (EditText)findViewById(R.id.email);
        ET_password = (EditText)findViewById(R.id.password);
    }

    public void userSignup(View view){
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void userLogin(View view){
        //Logging in Logic
        email = ET_email.getText().toString();
        password = ET_password.getText().toString();
        //String method = "login";

        //Error checking for valid email and password
        if(!validateEmail(email)){
            ET_email.setError("Invalid Email");
            ET_email.requestFocus();
        }
        else if (!validatePassword(password)) {
            ET_password.setError("Password must be greater than 6 characters.");
            ET_password.requestFocus();
        }
        else {
            BackgroundTaskLogin backgroundTaskLogin = new BackgroundTaskLogin(this);
            backgroundTaskLogin.execute(this);
        }
    }


    //Return true if password is valid and false if password is invalid
    protected boolean validatePassword(String password) {
        if(password!=null && password.length()>6) {
            return true;
        } else {
            return false;
        }
    }

    //Return true if email is valid and false if email is invalid
    protected boolean validateEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public void populateList(String result){

        welcomeMessage = result;

        BackgroundTaskListView backgroundTaskListView = new BackgroundTaskListView(this);
        backgroundTaskListView.execute(this);

    }

    public void restartLogin() {
        recreate();
        //finish();
        //startActivity(new Intent(this, LoginActivity.class));
    }

    public void goToMain(ArrayList<String> result) {


        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("email", email);
        intent.putExtra("welcomeMessage", welcomeMessage);
        intent.putExtra("addressList", reserve_results);
        intent.putExtra("postList", result);
        startActivity(intent);
        finish();

    }

    public void goToPostUpdate(ArrayList<String> result) {
        reserve_results = result;

        BackgroundTaskPostListView backgroundTaskPostListView = new BackgroundTaskPostListView(this);
        backgroundTaskPostListView.execute(this);

    }
}

