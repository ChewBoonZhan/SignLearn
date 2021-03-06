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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
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
    private static Activity game_activity;
    private View game_root;
    private ArrayList <Button> category_buttons;
    private File_connections file_connections;
    private App_data app_data;
    private Game_classes_init game_classes_init;
    private Category_init category_init;

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
        set_score_text();

        return root;
    }

    private void set_score_text(){
        TextView score_text = game_root.findViewById(R.id.score_text);
        score_text.setText("Score: " + file_connections.getScore());
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


    public static void setGame_context(Context context){
        game_context = context;
    }
    public static void setGame_activity(Activity activity){game_activity = activity;}

    public static Activity getGame_activity(){
        return game_activity;
    }
}