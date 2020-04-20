package com.example.assad.socialmediaappjava.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.assad.socialmediaappjava.Models.SearchModel;
import com.example.assad.socialmediaappjava.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.searchTalksRecyclerView> {
    private ArrayList<SearchModel> searchModels = new ArrayList<>();
    private Context context;
    private RequestQueue searchQueue;
    private JSONObject userNameDetails;
    private EditText editTextSearch;



    public SearchRecyclerViewAdapter(ArrayList<SearchModel> searchModels, Context context) {

        this.searchModels = searchModels;
        this.context = context;
        this.searchQueue = Volley.newRequestQueue(context);



    }



    @NonNull
    @Override
    public searchTalksRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_search, null);
        return new searchTalksRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchTalksRecyclerView holder, final int i) {

        holder.userName.setText(searchModels.get(i).getUsername());
    }

    @Override
    public int getItemCount() {
        return 0;
    }







    static class searchTalksRecyclerView extends RecyclerView.ViewHolder {


        private TextView userName;

        searchTalksRecyclerView(View itemView) {

            super(itemView);

            userName = itemView.findViewById(R.id.searchBar);
        }

    }




}
