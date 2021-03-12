package com.example.prototypeb;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;


import com.example.prototypeb.controller.new_screen.New_screen;
import com.example.prototypeb.ui.home.HomeFragment;
import com.example.prototypeb.ui.registration.RegistrationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private New_screen new_screen;
    private Animation top_anim, bottom_anim;
    private TextView title_text, desc_text;
    private ImageView logo;
    private final int SPLASH_SCREEN_DURATION = 5000;
    private File_connections file_connections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        new_screen = new New_screen(SPLASH_SCREEN_DURATION,this,this);
        file_connections = new File_connections(this);
        get_screen_elements();
        load_animations();
        set_elements_animation();

        set_splash_timeout();
    }
    private void set_splash_timeout(){
        String class_to_go;
        if((file_connections.get_user_name().equals(file_connections.getDefault_user_name())) &&(file_connections.get_user_icon() == file_connections.getDefault_user_icon_value())){
            class_to_go = "com.example.prototypeb.ui.registration.RegistrationFragment";
        }
        else{
            class_to_go = "com.example.prototypeb.ui.home.HomeFragment";
        }
        new_screen.go_to_new_screen(class_to_go,true);

    }
    private void load_animations(){
        top_anim = AnimationUtils.loadAnimation(this,R.anim.splash_top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.splash_bottom_anim);
    }
    private void get_screen_elements(){
        title_text = findViewById(R.id.splash_title);
        desc_text = findViewById(R.id.splash_desc);
        logo = findViewById(R.id.splash_logo);


    }
    private void set_elements_animation(){
        logo.setAnimation(top_anim);
        title_text.setAnimation(bottom_anim);
        desc_text.setAnimation(bottom_anim);
    }
}