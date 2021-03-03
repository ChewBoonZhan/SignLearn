package com.example.prototypeb.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.GameFragment;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.translator.TranslatorFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeFragment extends AppCompatActivity {
    private ImageView loading_logo_image;
    private TextView loading_text;
    private View body_components;
    private ChipNavigationBar nav_fragment;
    private File_connections file_connections;
    private TextView points_text;
    private Fragment fragment;


    //private static Categories categories;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file_connections = new File_connections(HomeFragment.this);
        init_elements();
        init_background_scenes();
        init_points();
        //categories = new Categories(this);
    }
    private void init_points(){
        points_text.setText(file_connections.getScore() + "");
    }
    private void init_elements(){
        nav_fragment = findViewById(R.id.nav_view);
        body_components = findViewById(R.id.nav_host_fragment);
        points_text = findViewById(R.id.point_display);

    }



    private void init_background_scenes(){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        nav_fragment.setItemSelected(R.id.navigation_lesson,true);
        new TranslatorFragment(HomeFragment.this,HomeFragment.this);
        fragment = new LessonFragment(HomeFragment.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
        nav_fragment.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.navigation_translator:

                        fragment = new TranslatorFragment(HomeFragment.this,HomeFragment.this);
                        break;
                    case R.id.navigation_lesson:
                        fragment = new LessonFragment(HomeFragment.this);
                        break;
                    case R.id.navigation_game:
                        fragment = new GameFragment(HomeFragment.this);
                        break;

                }

                if(fragment !=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

                }
            }
        });

    }
}
