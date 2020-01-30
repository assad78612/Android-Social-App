package com.example.assad.socialmediaappjava.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Models.EventModel;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;
import com.example.assad.socialmediaappjava.RecyclerAdapters.FeedRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FeedFragment extends Fragment {

    private ArrayList<EventModel> highlightsModels = new ArrayList<>();
    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private RequestQueue myQueue;
    private RecyclerView subRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myQueue = Volley.newRequestQueue(this.getContext());

        subRecyclerView = view.findViewById(R.id.subRecyclerView);

        makeVolleyRequestForMainFeed();

    }


    private void makeVolleyRequestForMainFeed() {
        String mJSONURL = NetworkConfiguration.BASE_NETWORK_ADDRESS + "events";

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
                                    JSONObject currentEvent = response.getJSONObject(i);

                                    /* Using the EventModel which has been created in package folder, the following event fields can be accessed
                                     * direct from the event model package Android then acknowledge's the event model package  */

                                    EventModel e = new EventModel(
                                            "https://fcbarcelona-static-files.s3.amazonaws.com/fcbarcelona/photo/2018/09/21/72049712-9e17-4317-9c56-50b2136b769d/39537642.jpg",
                                            currentEvent.getString("eventTitle"),
                                            currentEvent.getString("eventID"),
                                            currentEvent.getString("eventTime"),
                                            currentEvent.getString("eventAuthor"),
                                            currentEvent.getString("eventDescription"),
                                            false,
                                            false,
                                            currentEvent.getString("time")
                                    );
                                    eventModels.add(e);
                                }

                                initMainFeedRecyclerView();

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
        myQueue.add(jsonArrayRequest);
    }


    private void initMainFeedRecyclerView() {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        subRecyclerView.setLayoutManager(linearLayoutManager);
        subRecyclerView.setHasFixedSize(true);

        FeedRecyclerViewAdapter adapter = new FeedRecyclerViewAdapter(eventModels, this.getContext());
        subRecyclerView.setAdapter(adapter);

    }
}
