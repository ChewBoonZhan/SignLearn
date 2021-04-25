 package com.example.prototypeb.ui.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.prototypeb.R;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.prototypeb.controller.category.Category_init;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;
import com.example.prototypeb.ui.game.Game_components.Game_alphabets;
import com.example.prototypeb.ui.game.Game_components.Game_attachments;
import com.example.prototypeb.ui.game.Game_components.Game_classes_init;
import com.example.prototypeb.ui.game.Game_components.Game_components;
import com.example.prototypeb.ui.game.Game_components.Game_numbers;
import com.example.prototypeb.ui.game.Game_components.Game_pronoun;

public class GameFragment extends Fragment {

    //View model for Game
    private GameViewModel gameViewModel;
    //All components in Game Fragment
    private static Context game_context;
    //Game fragment view
    private View game_root;
    //Game category buttons
    private ArrayList <Button> category_buttons;
    //Notifications on game category buttons
    private ArrayList <TextView> category_notifi;
    //Link file to save data
    private File_connections file_connections;
    //Linking app data
    private App_data app_data;
    //Different classes of game categories
    private Game_classes_init game_classes_init;
    //Initialize each game category
    private Category_init category_init;
    public GameFragment(Context context){
        this.game_context = context;
    }

    /**
     * Load game fragment components
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root -> game fragment view with all components
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        //assign game fragment view
        game_root = root;
        //initialize game category notifications
        init_notifi();
        //initialize game files to save data
        file_connections = new File_connections(game_context);
        app_data = new App_data();
        //initialize game classes and categories
        game_classes_init = new Game_classes_init(game_context);
        category_init = new Category_init(game_context,game_classes_init);
        //initialize game category buttons
        init_category_buttons();
        return root;
    }

    /**
     * Categories being unlocked
     */
    @Override
    public void onResume() {
        super.onResume();

        init_categories_according_to_unlocked();
    }

    /**
     * Initialize game category notifications
     */
    private void init_notifi(){
       category_notifi = new ArrayList <TextView>();
       category_notifi.add(game_root.findViewById(R.id.game_1_notifi));
       category_notifi.add(game_root.findViewById(R.id.game_2_notifi));
       category_notifi.add(game_root.findViewById(R.id.game_3_notifi));
       category_notifi.add(game_root.findViewById(R.id.game_4_notifi));
       category_notifi.add(game_root.findViewById(R.id.game_5_notifi));
   }

    /**
     * Initialize game category buttons
     */
    private void init_category_buttons(){
        category_buttons = new ArrayList<Button>();
        category_buttons.add(game_root.findViewById(R.id.game1_button_id));
        category_buttons.add(game_root.findViewById(R.id.game2_button_id));
        category_buttons.add(game_root.findViewById(R.id.game3_button_id));
        category_buttons.add(game_root.findViewById(R.id.game4_button_id));
        category_buttons.add(game_root.findViewById(R.id.game5_button_id));
    }

    /**
     * Initialize game category button once its been unlocked
     */
    private void init_categories_according_to_unlocked(){
        category_init.init_category_button_according_to_unlocked(category_buttons,category_notifi,true);
    }

    public static Context getGame_context(){
        return game_context;
    }







}