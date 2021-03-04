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

    private GameViewModel gameViewModel;
    private static Context game_context;

    private View game_root;
    private ArrayList <Button> category_buttons;
    private File_connections file_connections;
    private App_data app_data;
    private Game_classes_init game_classes_init;
    private Category_init category_init;
    public GameFragment(Context context){
        this.game_context = context;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        game_root = root;

        file_connections = new File_connections(game_context);
        app_data = new App_data();
        game_classes_init = new Game_classes_init(game_context);
        category_init = new Category_init(game_context,game_classes_init);
        init_category_buttons();
        init_category_button_according_to_unlocked();


        return root;
    }

   
    private void init_category_buttons(){
        category_buttons = new ArrayList<Button>();
        category_buttons.add(game_root.findViewById(R.id.game1_button_id));
        category_buttons.add(game_root.findViewById(R.id.game2_button_id));
        category_buttons.add(game_root.findViewById(R.id.game3_button_id));
        category_buttons.add(game_root.findViewById(R.id.game4_button_id));
        category_buttons.add(game_root.findViewById(R.id.game5_button_id));
    }

    private void init_category_button_according_to_unlocked(){
        category_init.init_category_button_according_to_unlocked(category_buttons);

    }





}