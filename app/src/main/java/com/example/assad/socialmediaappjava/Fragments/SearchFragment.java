package com.example.assad.socialmediaappjava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;
import static android.content.Context.MODE_PRIVATE;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SearchFragment extends Fragment {
    private EditText editUserInput;
    private RequestQueue searchQueue;
    private Button searchButton;
    private JSONObject searchedUsers;
    private Context context;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.search, container, false);
    }



    private JSONObject getSearchedUsers() {
        searchedUsers = new JSONObject();

        try {
            searchedUsers.put("Username", editUserInput.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return searchedUsers;
    }


}


//
//    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        searchQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
//        editUserInput = view.findViewById(R.id.Username_input);
//        searchButton = view.findViewById(R.id.Search_Button);
//
//
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchQueue();
//            }
//        });
//
//
//
//
//
//
//
//    }
