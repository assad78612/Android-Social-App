package com.example.assad.socialmediaappjava.RecyclerAdapters;

/* Feed RecyclerView    */


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.assad.socialmediaappjava.Models.EventModel;
import com.example.assad.socialmediaappjava.Network.NetworkConfiguration;
import com.example.assad.socialmediaappjava.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.FeedViewHolder> {

    private static final String TAG = "HighlightsRecyclerViewAdapter";
    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private Context context;
    private SharedPreferences prefs;
    private RequestQueue myQueue;

    public FeedRecyclerViewAdapter(ArrayList<EventModel> eventModels, Context context) {

        this.eventModels = eventModels;
        this.context = context;
        this.myQueue = Volley.newRequestQueue(context);
        this.prefs = context.getSharedPreferences("userInfo", MODE_PRIVATE);


    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_feed, null);
        return new FeedViewHolder(view);
    }

    // On Bind allows to inject the data in
    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder holder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.eventTitle.setText(eventModels.get(i).getEventTitle());
        holder.eventTime.setText(eventModels.get(i).getEventTime());
        holder.eventDescription.setText(eventModels.get(i).getEventDescription());
        holder.eventAuthor.setText(eventModels.get(i).getEventAuthor());
        holder.timeofEvent.setText(eventModels.get(i).getTimeofEvent());


        if (eventModels.get(i).isHasBeenJoined()) {
            holder.joinEventBtn.setText("Leave Event");
        } else {
            holder.joinEventBtn.setText("Join Event");
        }

        holder.joinEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eventModels.get(i).isHasBeenJoined()) {
                    //leave it
                    makeVolleyRequestToLeaveEvent(prefs.getString("username", null), eventModels.get(i).getEventID());
                    eventModels.get(i).setHasBeenJoined(false);
                    holder.joinEventBtn.setText("Join Event");
                } else {
                    //join it
                    makeVolleyRequestToJoinEvent(prefs.getString("username", null), eventModels.get(i).getEventID());
                    eventModels.get(i).setHasBeenJoined(true);
                    holder.joinEventBtn.setText("Leave Event");

                }
            }
        });

    }

    private JSONObject getUserTypedInfo(String username, String eventID) {
        JSONObject eventPostDetails = new JSONObject();

        try {
            eventPostDetails.put("username", username);
            eventPostDetails.put("eventID", eventID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eventPostDetails;
    }


    private void makeVolleyRequestToJoinEvent(String username, String eventID) {


        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "joinEvent";


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(username, eventID), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Join Successful", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            //Error stuff picks up all 400 codes..../500's
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);
                Toast.makeText(context, "Join Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });


        myQueue.add(postRequest);
    }

    private void makeVolleyRequestToLeaveEvent(String username, String eventID) {


        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "leaveEvent";


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(username, eventID), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Leave Successful", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            //Error stuff picks up all 400 codes..../500's
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);
                Toast.makeText(context, "Join Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        });


        myQueue.add(postRequest);
    }


    @Override
    public int getItemCount() {
        return eventModels.size();

    }

    /*
        From the onCreateViewHolder, anything created in that layout file should be referenced in the FeedViewHolder class below.

        Old one public static class FeedViewHolder extends RecyclerView.ViewHolder

        Old one public FeedViewHolder(@NonNull View itemView) 


    */
    static class FeedViewHolder extends RecyclerView.ViewHolder {

        private TextView eventTitle, eventTime, eventDescription, eventAuthor, likeButton, timeofEvent;
        private Button joinEventBtn;

        FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            eventAuthor = itemView.findViewById(R.id.eventAuthor);
            joinEventBtn = itemView.findViewById(R.id.jointEventBtn);
            timeofEvent = itemView.findViewById(R.id.timeofEvent);


        }
    }
}
