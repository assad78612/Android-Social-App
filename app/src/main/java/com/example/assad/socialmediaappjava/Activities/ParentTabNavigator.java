package com.example.assad.socialmediaappjava.Activities;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.assad.socialmediaappjava.Fragments.AddFragment;
import com.example.assad.socialmediaappjava.Fragments.FeedFragment;
import com.example.assad.socialmediaappjava.Fragments.ProfileFragment;
import com.example.assad.socialmediaappjava.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentTabNavigator extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {

                        case R.id.navigation_home:
                            selectedFragment = new FeedFragment();
                            break;

                        case R.id.navigation_add:
                            selectedFragment = new AddFragment();
                            break;


                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;

                }

            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabs);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedFragment()).commit();
    }

}



