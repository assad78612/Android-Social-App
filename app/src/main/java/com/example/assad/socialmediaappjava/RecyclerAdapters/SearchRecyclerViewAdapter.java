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
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private ArrayList<SearchModel> searchModelList;
    private Context context;

    public SearchRecyclerViewAdapter(ArrayList<SearchModel> searchModelList, Context context) {
        this.searchModelList = searchModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 1. Set ur layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SearchModel searchModel = this.searchModelList.get(position);
        // 3. Inflate them...put in ur information
        holder.username.setText(searchModel.getUsername());

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    // This ViewHolder class references the layout file
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            // 2. Reference ur views
            username = itemView.findViewById(R.id.userResult);

        }
    }

}