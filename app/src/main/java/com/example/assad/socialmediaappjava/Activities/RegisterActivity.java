package com.example.assad.socialmediaappjava.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {


    private JSONObject userDetails;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText emailAddressEditText;
    private RequestQueue myQueue;
    private Button registerButton;
    //This function will only allow the user to register unless all fields have been completed.
    private TextWatcher checkBoxes = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = userNameEditText.getText().toString().trim();
            String firstNameInput = firstNameEditText.getText().toString().trim();
            String lastNameInput = lastNameEditText.getText().toString().trim();
            String passwordInput = passwordEditText.getText().toString().trim();
            String emailAddressInput = emailAddressEditText.getText().toString().trim();

            registerButton.setEnabled(!usernameInput.isEmpty() && !firstNameInput.isEmpty() && !lastNameInput.isEmpty() && !passwordInput.isEmpty() && !emailAddressInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firstNameEditText = findViewById(R.id.registerFirstName);
        lastNameEditText = findViewById(R.id.registerLastName);
        userNameEditText = findViewById(R.id.registerUsername);
        passwordEditText = findViewById(R.id.registerPassword);
        emailAddressEditText = findViewById(R.id.registerEmailAddress);
        registerButton = findViewById(R.id.registerButton);

        //Check Boxes to ensure that user fills out all text fields
        firstNameEditText.addTextChangedListener(checkBoxes);
        lastNameEditText.addTextChangedListener(checkBoxes);
        userNameEditText.addTextChangedListener(checkBoxes);
        passwordEditText.addTextChangedListener(checkBoxes);
        emailAddressEditText.addTextChangedListener(checkBoxes);


        myQueue = Volley.newRequestQueue(this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeRequest();

            }
        });


  /*  onclick listener which will listen for a click on the sign up button
       when you click the button its going to run a method which gets all the user info */

    }

    private JSONObject getUserTypedInfo() {
        userDetails = new JSONObject();

        try {
            userDetails.put("emailAddress", emailAddressEditText.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return userDetails;
    }

    private void makeRequest() {

        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "generateEmailToken";

        JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "A code has been to your email, please verify", Toast.LENGTH_LONG).show();

                Intent userIntent = new Intent(RegisterActivity.this, RegistrationCodeActivity.class);
                userIntent.putExtra("username", userNameEditText.getText().toString());
                userIntent.putExtra("firstName", firstNameEditText.getText().toString());
                userIntent.putExtra("lastName", lastNameEditText.getText().toString());
                userIntent.putExtra("pwd", passwordEditText.getText().toString());
                userIntent.putExtra("emailAddress", emailAddressEditText.getText().toString());

                startActivity(userIntent);
                finish();


                Log.d("REGISTERRESPONSE", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            //Error stuff picks up all 400 codes..../500's
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);
                Toast.makeText(getApplicationContext(), "Register Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });

        myQueue.add(registerRequest);

    }
}