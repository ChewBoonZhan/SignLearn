package com.example.prototypeb;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prototypeb.controller.translator.Translator;
import com.example.prototypeb.ui.game.GameFragment;
import com.example.prototypeb.ui.translator.TranslatorFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_translator, R.id.navigation_lesson, R.id.navigation_game)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        set_context_activity_translator();

    }

    private void set_context_activity_translator(){
        TranslatorFragment.setActivity_here(MainActivity.this);
        TranslatorFragment.setContext_here(MainActivity.this);
        GameFragment.setGame_context(MainActivity.this);
        GameFragment.setGame_activity(MainActivity.this);

    }






}