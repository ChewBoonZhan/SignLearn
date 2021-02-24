package com.example.prototypeb;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.file_connections.File_connections;

import com.example.prototypeb.ui.game.GameFragment;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.translator.TranslatorFragment;
//import com.example.prototypeb.controller.file_connections.Categories;

public class MainActivity extends AppCompatActivity {
    private ImageView loading_logo_image;
    private TextView loading_text;
    private View body_components;
    private BottomNavigationView nav_fragment;
    private ConstraintLayout main_layout;

    private static boolean ready = false;

    

    //private static Categories categories;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_elements();
        init_background_scenes();
        //categories = new Categories(this);

        set_context_activity_for_use();


    }

    private void init_elements(){


        nav_fragment = findViewById(R.id.nav_view);
        body_components = findViewById(R.id.nav_host_fragment);
        main_layout = findViewById(R.id.container);



    }

    private void set_context_activity_for_use(){
        TranslatorFragment.setActivity_here(MainActivity.this);
        TranslatorFragment.setContext_here(MainActivity.this);
        GameFragment.setGame_context(MainActivity.this);
        GameFragment.setGame_activity(MainActivity.this);
        LessonFragment.setLesson_activity(MainActivity.this);
        LessonFragment.setLesson_context(MainActivity.this);

    }


    private void init_background_scenes(){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_translator, R.id.navigation_lesson, R.id.navigation_game)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(nav_fragment, navController);

    }
    public static boolean get_first_loaded_or_not(){
        return ready;
    }
    /*
    public static Categories getCategories(){
        return categories;
    }
    */








}