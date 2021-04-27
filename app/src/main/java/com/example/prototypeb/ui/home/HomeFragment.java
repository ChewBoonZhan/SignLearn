package com.example.prototypeb.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.prototypeb.ui.Show_Score;
import com.example.prototypeb.ui.game.GameFragment;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.translator.TranslatorFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class HomeFragment extends AppCompatActivity {
    // animated navigation
    private ChipNavigationBar nav_fragment;

    // initialize file connection
    private File_connections file_connections;

    // initialize textview for user's score and name
    private TextView points_text,user_name;

    // fragment to show to user
    private Fragment fragment;

    // user_logo to be displayed to user
    private ImageView user_main_logo;

    /**
     * Function called when all elements of the home screen has been initialized
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set user goes to main activity
        setContentView(R.layout.activity_main);

        // create new instance of file connection
        file_connections = new File_connections(HomeFragment.this);

        // initialize to get screen components
        init_elements();

        // initialize bottom navigation
        init_navigation_bottom();


        // initialize user name and icon
        init_user_name_and_icon();

        // initialize point to be shown to user
        init_points();
    }

    /**
     * Set the points to be displayed to user
     */
    private void init_points(){

        points_text.setText(((String)(file_connections.getScore() + "")));

        new Show_Score().update_points(file_connections);

    }

    /**
     * Initialize elements by getting them from screen to be processed
     */
    private void init_elements(){
        nav_fragment = findViewById(R.id.nav_view);
        points_text = findViewById(R.id.point_display);
        user_name = findViewById(R.id.user_name);
        user_main_logo = findViewById(R.id.user_main_logo);
        Show_Score.setPoints_text(points_text);

    }

    /**
     * Initialize and show user name and icon on screen
     */
    private void init_user_name_and_icon(){
        user_name.setText(file_connections.get_user_name());
        user_main_logo.setImageResource(file_connections.get_user_icon());
    }

    /**
     * Initializes navigation at the bottom of screen
     * Code adopted from Chip Navigation Bar: https://github.com/ismaeldivita/chip-navigation-bar
     */
    private void init_navigation_bottom(){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        nav_fragment.setItemSelected(R.id.navigation_lesson,true);
        new TranslatorFragment(HomeFragment.this,HomeFragment.this);
        fragment = new LessonFragment(HomeFragment.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

        // set what happends when element on navigation at bottom of menu is clicked
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
