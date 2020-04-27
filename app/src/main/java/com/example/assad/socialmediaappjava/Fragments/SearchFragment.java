package com.example.assad.socialmediaappjava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Models.SearchModel;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;
import com.example.assad.socialmediaappjava.RecyclerAdapters.SearchRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SearchFragment extends Fragment {
    private EditText editUserInput;
    private RequestQueue searchQueue;
    private JSONObject searchedUsers;
    private Context context;
    private RecyclerView searchTalksRecyclerView;
    private ArrayList<SearchModel> searchModels = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        editUserInput = view.findViewById(R.id.searchField);
        Button buttonSearch = view.findViewById(R.id.searchButton);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                makeVolleyRequest();

            }
        });

        searchTalksRecyclerView = view.findViewById(R.id.searchTalksRecyclerView);
//        makeVolleyRequestForSearchFeed();

        editUserInput.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchModels.clear();
                makeVolleyRequestForSearchFeed(editUserInput.getText().toString());
                System.out.println(editUserInput.getText().toString());
//                makeVolleyRequest(searchField.getText().toString());
            }
        });

    }


    private void makeVolleyRequestForSearchFeed(String usernameTyped) {
        String mJSONURL = NetworkConfiguration.BASE_NETWORK_ADDRESS + "users?username=" + usernameTyped;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        // Process the JSON
                        if (response.length() > 0) {
                            try {
                                // Loop through the array elements
                                for (int i = 0; i < response.length(); i++) {
                                    // Get current json object
                                    JSONObject searchResults = response.getJSONObject(i);

                                    /* Using the EventModel which has been created in package folder, the following event fields can be accessed
                                     * direct from the event model package Android then acknowledge's the event model package  */

                                    SearchModel s = new SearchModel(searchResults.getString("username"));
                                    searchModels.add(s);

                                    System.out.println(s);
                                }

                                searchRecycleView();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        System.out.print(error);
                    }
                }
        );

        //After creating the JSON Array object, this function will run the whole operation.
        searchQueue.add(jsonArrayRequest);
    }

//    private void makeVolleyRequest() {
//
//        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "Users";
//
//        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, searchModels(), new Response.Listener<JSONObject>() {
//
//            public void onResponse(JSONObject response) {
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                System.out.println(error);
//
//            }
//        });
//
//        searchQueue.add(postRequest);
//
//
//    }



    private void searchRecycleView() {


        // MAKE UR RECYCLER VIEW
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        searchTalksRecyclerView.setLayoutManager(linearLayoutManager);
        searchTalksRecyclerView.setHasFixedSize(true);

        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(searchModels, this.getContext());
        searchTalksRecyclerView.setAdapter(adapter);

    }


}
