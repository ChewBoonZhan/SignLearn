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


import com.example.prototypeb.controller.loading_screen.Loading_screen;
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
    private Loading_screen loading_screen;
    private static boolean ready = false;
    private static boolean camera_permission_acquired = false;
    private Button allow_camera_permission;
    //private static Categories categories;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_elements();
        init_background_scenes();
        //categories = new Categories(this);
        loading_screen = new Loading_screen(main_layout,loading_text,loading_logo_image,this);
        set_context_activity_for_use();
        start_request_permission();
    }

    private void init_elements(){
        loading_logo_image = findViewById(R.id.beginning_loading_logo);
        loading_logo_image.setImageResource(R.drawable.logo);

        loading_text = findViewById(R.id.beginning_loading_text);

        nav_fragment = findViewById(R.id.nav_view);
        body_components = findViewById(R.id.nav_host_fragment);
        main_layout = findViewById(R.id.container);

        allow_camera_permission = findViewById(R.id.request_permission_button);

    }

    private void set_context_activity_for_use(){
        TranslatorFragment.setActivity_here(MainActivity.this);
        TranslatorFragment.setContext_here(MainActivity.this);
        GameFragment.setGame_context(MainActivity.this);
        GameFragment.setGame_activity(MainActivity.this);
        LessonFragment.setLesson_activity(MainActivity.this);
        LessonFragment.setLesson_context(MainActivity.this);

    }
    public void done_loading_screen(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                main_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                loading_logo_image.setVisibility(View.GONE);
                loading_text.setVisibility(View.GONE);

                nav_fragment.setVisibility(View.VISIBLE);
                body_components.setVisibility(View.VISIBLE);

                TranslatorFragment.set_layout_ready();
                ready = true;

            }
        });

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


    //permissions, as dealing with fragments is too complicated.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {


        init_allow_camera_button();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permission_not_granted(false);
            request_permission();
        } else {
            loading_screen.start_timer();
            camera_permission_acquired = true;

            permission_granted(true);

        }
    }



    private void init_allow_camera_button() {
        allow_camera_permission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                request_permission();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void request_permission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //user denied it before, therefore explain why we need it
            new AlertDialog.Builder(this).setTitle("Permission needed").setMessage("This device's camera is needed for the Sign Language translator.").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);

                    request_permission_thread();
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                    dialog.dismiss();
                    permission_not_granted(true);
                }
            }).create().show();
        } else {
            //request for permission like normal
            //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);
            request_permission_thread();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission_thread() {


        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission_granted(false);
            } else {
                permission_not_granted(true);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_granted(boolean permission_initially_granted) {
        if(!permission_initially_granted){
            Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            loading_screen.start_timer();
            TranslatorFragment.getCamera_handle().start_camera_met();

        }
        disable_allow_camera_button();
        //TranslatorFragment.all_important_buttons_status(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){
            Toast.makeText(this, "Camera Permission not Granted", Toast.LENGTH_SHORT).show();
        }

        enable_allow_camera_button();
        //TranslatorFragment.all_important_buttons_status(false);

    }
    private void disable_allow_camera_button() {
        allow_camera_permission.setVisibility(View.GONE);
        allow_camera_permission.setEnabled(false);

    }

    private void enable_allow_camera_button() {
        allow_camera_permission.setVisibility(View.VISIBLE);
        allow_camera_permission.setEnabled(true);
    }
    public static boolean getCamera_permission_acquired(){
        return camera_permission_acquired;
    }





}