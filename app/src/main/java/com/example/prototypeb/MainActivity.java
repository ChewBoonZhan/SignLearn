package com.example.prototypeb;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
//import com.example.prototypeb.controller.file_connections.Categories;

public class MainActivity extends AppCompatActivity {
    private ImageView loading_logo_image;
    private TextView loading_text;
    private View body_components;
    private ChipNavigationBar nav_fragment;

    private Fragment fragment;
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

        nav_fragment.setItemSelected(R.id.navigation_translator,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new TranslatorFragment()).commit();

        nav_fragment.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.navigation_translator:
                        fragment = new TranslatorFragment();
                        break;
                    case R.id.navigation_lesson:
                        fragment = new LessonFragment();
                        break;
                    case R.id.navigation_game:
                        fragment = new GameFragment();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });
        /*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_translator, R.id.navigation_lesson, R.id.navigation_game)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(nav_fragment, navController);
        */
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