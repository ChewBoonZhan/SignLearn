package com.example.prototypeb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prototypeb.ui.translator.TranslatorFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.prototypeb.R.id;
import static com.example.prototypeb.R.layout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        BottomNavigationView navView = findViewById(id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                id.navigation_translator, id.navigation_lesson, id.navigation_game)
                .build();
        NavController navController = Navigation.findNavController(this, id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        set_context_activity_translator();

    }


    private void set_context_activity_translator(){
        TranslatorFragment.activity_here = MainActivity.this;
        TranslatorFragment.context_here = MainActivity.this;
    }






}