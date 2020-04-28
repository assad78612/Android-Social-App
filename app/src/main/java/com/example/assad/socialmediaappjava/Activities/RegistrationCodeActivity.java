package com.example.assad.socialmediaappjava.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegistrationCodeActivity extends AppCompatActivity {
    private RequestQueue myQueue;
    private Context context;
    private EditText tokenEditText;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_code);

        tokenEditText = findViewById(R.id.registrationCodeToken);
        Button submitButton = findViewById(R.id.registrationCodeSubmitBtn);

        myQueue = Volley.newRequestQueue(this);
        context = this.getApplicationContext();

        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
    }

    private JSONObject getCodeDetails() {

        JSONObject codeDetails = new JSONObject();

        String emailFromPreviousIntent = Objects.requireNonNull(getIntent().getExtras()).getString("emailAddress");

        try {
            codeDetails.put("email", emailFromPreviousIntent);
            codeDetails.put("token", tokenEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return codeDetails;

    }


    private void makeRequest() {

        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "checkEmailToken";
        JsonObjectRequest codeRequest = new JsonObjectRequest(Request.Method.POST, url, getCodeDetails(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Token Successful", Toast.LENGTH_LONG).show();


                registerUser();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.print(error);

                        Toast.makeText(context, "Token Incorrect", Toast.LENGTH_LONG).show();
                    }
                });

        myQueue.add(codeRequest);
    }


    private JSONObject getUserTypedInfo() {
        JSONObject userDetails = new JSONObject();

        try {
            userDetails.put("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
            userDetails.put("firstName", getIntent().getExtras().getString("firstName"));
            userDetails.put("lastName", getIntent().getExtras().getString("lastName"));
            userDetails.put("pwd", getIntent().getExtras().getString("pwd"));
            userDetails.put("emailAddress", getIntent().getExtras().getString("emailAddress"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userDetails;
    }

    private void registerUser() {
        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "register";

        JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                Intent userIntent = new Intent(RegistrationCodeActivity.this, ParentTabNavigator.class);

                /*This is where we set the Shared prefs which will allow Android to retrieve the username info w
                * from the database and send back to Android viewing the first name and last name on the profile fragment*/
                editor.putString("username", Objects.requireNonNull(getIntent().getExtras()).getString("username"));
                editor.commit();

                startActivity(userIntent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            //Error stuff picks up all 400 codes..../500's
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);
                Toast.makeText(getApplicationContext(), "Verification Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });

        myQueue.add(registerRequest);

    }

}