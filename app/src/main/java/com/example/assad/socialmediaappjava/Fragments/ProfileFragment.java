package com.example.assad.socialmediaappjava.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Activities.LoginActivity;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private TextView emailID, fullName;
    private RequestQueue myQueue;
    private String usernameLoggedIn;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myQueue = Volley.newRequestQueue(this.getContext());


        /* Using SharedPreferences will store all the user details who is logged in, allowing the profile
         * page to recognised who is logged in and remain logged until the user has clicked to logout  */
        prefs = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);

        usernameLoggedIn = prefs.getString("username", null);

/*
        After declaring that we are going going to use TextViews, the TextView must be assigned to
        the text view fields that has been created in the layout files. In this case it is the
        emailID and fullName which are being assigned to the files which are located in the
        layout XML files created.
*/
        emailID = getView().findViewById(R.id.email);
        fullName = getView().findViewById(R.id.fullName);

        getUserProfileInformation();

        logoutButton = getView().findViewById(R.id.LogoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor = prefs.edit();
                editor.remove("username");
                editor.apply();

                Intent goToLoginPage = new Intent(getActivity(), LoginActivity.class);
                startActivity(goToLoginPage);
                getActivity().finish();

            }

        });
    }


    private void getUserProfileInformation() {

        /* Using BASE_NETWORK_ADDRESS which is using the current network address inputted within  the "Network" class
          * along with "userprofile?id=" which retrieves the user information and and ID of that user is given locating that user and
           * retrieves the user profile information through the API  MySQL database. usernameLoggedIn is using the SharedPreferences with that user logged in */

        String mJSONURL = NetworkConfiguration.BASE_NETWORK_ADDRESS + "userprofile?id=" + usernameLoggedIn;

        /* By using a JsonObjectRequest we are able to convert the data allowing the API to  */


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mJSONURL,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    fullName.setText(response.getJSONObject("userProfile").getString("firstName") + " " + response.getJSONObject("userProfile").getString("lastName"));
                    emailID.setText(response.getJSONObject("userProfile").getString("emailAddress"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        /* myQueue will perform the entire request for getUserProfileInformation which will connect to the API, myQueue will only be perform after getUserProfileInformation
           * has been executed and finally running the myQueue network request */
        myQueue.add(req);

    }

}