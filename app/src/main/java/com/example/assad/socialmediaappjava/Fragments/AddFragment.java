package com.example.assad.socialmediaappjava.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class AddFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    private EditText editTextEventTitle, editTextEventDescription, editTextDatePicker, editTextTimeofEvent;
    private Button buttonPost, buttonLike;
    private SharedPreferences prefs;
    private RequestQueue myQueue;
    private JSONObject userDetails;
    private JSONObject jsonObject;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getContext().getSharedPreferences("userInfo", MODE_PRIVATE);
        myQueue = Volley.newRequestQueue(getContext());


        editTextEventTitle = view.findViewById(R.id.addEventTitle);
        editTextEventDescription = view.findViewById(R.id.addEventDescription);
        buttonPost = view.findViewById(R.id.addPostButton);
        editTextDatePicker = view.findViewById(R.id.addEventDate);
        editTextTimeofEvent = view.findViewById(R.id.addEventTime);


        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyRequest();
            }
        });

        editTextDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Context context = view.getContext();

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.custom_calendar);

                final CalendarView calendarView = dialog.findViewById(R.id.calendarView);

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        //But then put the date inside the edit text
                        editTextDatePicker.setText(year + "/" + month + "/" + dayOfMonth);

                        //Close the calendar
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });

        editTextTimeofEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showTimePickerDialog();


            }
        });
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        editTextTimeofEvent.setText(hourOfDay + ":" + minute);
    }

    private String soemthign(){
        return "Assad";
    }
    private void showTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        dialog.show();
    }


    private JSONObject getUserTypedInfo() {
        userDetails = new JSONObject();

        try {
            userDetails.put("eventTitle", editTextEventTitle.getText().toString());
            userDetails.put("eventDescription", editTextEventDescription.getText().toString());
            userDetails.put("eventTime", editTextDatePicker.getText().toString());
            userDetails.put("eventAuthor", prefs.getString("username", null));
            userDetails.put("time", editTextTimeofEvent.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return userDetails;
    }


    private void volleyRequest() {


        String url = NetworkConfiguration.BASE_NETWORK_ADDRESS + "createEvents";


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, getUserTypedInfo(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Post Successful", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            //Error stuff picks up all 400 codes..../500's
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.print(error);
                Toast.makeText(getContext(), "Post Unsuccessful", Toast.LENGTH_SHORT).show();


            }
        });


        myQueue.add(postRequest);
    }
}