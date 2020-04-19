package com.example.assad.socialmediaappjava.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Models.UserModel;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Integer.parseInt;

public class LoginActivity extends AppCompatActivity {

    private List<UserModel> listOfUsers = new ArrayList<>();
    private Button loginButton;
    private TextView registerLink;
    private JSONObject userDetails;
    private RequestQueue myQueue;
    private EditText userNameEditText, passwordEditText;
    private Context context;
    private JSONObject jsonObjectRequest;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this.getApplicationContext();
        myQueue = Volley.newRequestQueue(context);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.contains("username")) {

            Intent registerIntent = new Intent(LoginActivity.this, ParentTabNavigator.class);
            LoginActivity.this.startActivity(registerIntent);
        }


        userNameEditText = findViewById(R.id.usernameLogin);
        passwordEditText = findViewById(R.id.passwordLogin);

        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.bRegister1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, ParentTabNavigator.class);
                LoginActivity.this.startActivity(registerIntent);
                finish();


            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();


                makeRequest();
            }

            public void saveData() {

                editor.putString("username", userNameEditText.getText().toString());
                editor.apply();


            }
        });


    }

    private JSONObject getUserTypedInfo() {

        userDetails = new JSONObject();

        try {
            userDetails.put("username", userNameEditText.getText().toString());
            userDetails.put("pwd", passwordEditText.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return userDetails;

    }

    private void makeRequest() {

        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "login";

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_LONG).show();

                Intent userIntent = new Intent(context, ParentTabNavigator.class);
                startActivity(userIntent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);

                Toast.makeText(context, "Login Unsuccessful!", Toast.LENGTH_LONG).show();
            }
        });

        myQueue.add(loginRequest);


    }


}