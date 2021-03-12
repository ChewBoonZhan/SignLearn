package com.example.prototypeb.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.GameFragment;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.translator.TranslatorFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeFragment extends AppCompatActivity {

    private ChipNavigationBar nav_fragment;
    private File_connections file_connections;
    private TextView points_text,user_name;
    private Fragment fragment;
    private ImageView user_main_logo;

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
        init_user_name_and_icon();

    }
    private void init_points(){
        points_text.setText(((String)(file_connections.getScore() + "")));
    }
    private void init_elements(){
        nav_fragment = findViewById(R.id.nav_view);
        points_text = findViewById(R.id.point_display);
        user_name = findViewById(R.id.user_name);
        user_main_logo = findViewById(R.id.user_main_logo);

    }
    private void init_user_name_and_icon(){
        user_name.setText(file_connections.get_user_name());
        user_main_logo.setImageResource(file_connections.get_user_icon());
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
