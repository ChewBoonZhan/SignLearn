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
    //check if there is a custom toast being shown on the screen.
    // Default there is no toast is shown
    public static boolean toast_shown = false;

    // class that allows transition to a new screen
    private New_screen new_screen;

    // animation for the splash screen
    private Animation top_anim, bottom_anim;

    //Title text and description text for splash screen
    private TextView title_text, desc_text;

    //logo of the splash screen
    private ImageView logo;

    //duration of the splash screen
    private final int SPLASH_SCREEN_DURATION = 5000;

    //allows access to file connections to check user's information
    private File_connections file_connections;

    //determines the screen the user will go to based on the file's content
    private String class_to_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set such that the fragment takes up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set the splash screen to be displayed on the screen
        setContentView(R.layout.splash_screen);

        //create instance of new screen to allow transition to a new screen
        new_screen = new New_screen(0,this,this);

        //create a new instance of file connection to get file connection's data
        file_connections = new File_connections(this);

        //get the screen's elements
        get_screen_elements();

        // loads animations for the splash screen
        load_animations();

        //set the elements on the screen to have the animations as loaded
        set_elements_animation();

        //sets the splash screen timeout to go to a new screen
        set_splash_timeout();

    }

    /**
     * sets the timeout of the splash screen to go to a new screen
     */
    private void set_splash_timeout(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if((file_connections.get_user_name().equals(file_connections.getDefault_user_name())) &&(file_connections.get_user_icon() == file_connections.getDefault_user_icon_value())){
                    //if the user has not registered in the app yet

                    //lead the user to the registration fragment for the user to sign up
                    class_to_go = "com.example.prototypeb.ui.registration.RegistrationFragment";
                }
                else{
                    //user has registered in the app

                    //lead the user to the main screen of the app
                    class_to_go = "com.example.prototypeb.ui.home.HomeFragment";
                }

                //lead the user to a new screen based on the screen value set.
                new_screen.go_to_new_screen(class_to_go,true);
            }
        }, SPLASH_SCREEN_DURATION); //setting that the new screen will be entered after a timeout.


    }

    /**
     * loads animation from R.anim
     */
    private void load_animations(){
        //loads the top animation of the splash screen
        top_anim = AnimationUtils.loadAnimation(this,R.anim.splash_top_anim);

        //loads the bottom animation of the splash screen
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.splash_bottom_anim);
    }

    /**
     * gets the screen elements to be processed
     */
    private void get_screen_elements(){
        // finds the title text element of the splash screen
        title_text = findViewById(R.id.splash_title);

        //finds the description text of the element of the splash screen
        desc_text = findViewById(R.id.splash_desc);

        //finds the logo of the splash screen
        logo = findViewById(R.id.splash_logo);


    }

    /**
     * sets animation for elements on the splash screen
     */
    private void set_elements_animation(){

        //set such that logo has top animation of splash screen
        logo.setAnimation(top_anim);

        //set such that title and description has bottom animation of splash screen
        title_text.setAnimation(bottom_anim);
        desc_text.setAnimation(bottom_anim);
    }
}