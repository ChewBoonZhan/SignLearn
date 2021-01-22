package com.example.prototypeb.ui.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

import com.example.prototypeb.controller.file_connections.Categories;
import com.example.prototypeb.controller.app_data.App_data;

public class GameFragment extends Fragment {

    private GameViewModel gameViewModel;
    private static Context game_context;
    private View game_root;
    private ArrayList <Button> category_buttons;
    private Categories categories;
    private App_data app_data;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GameViewModel.class);
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        game_root = root;
        categories = new Categories(game_context);
        app_data = new App_data();
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
        SharedPreferences sharedPreferences = categories.getSharedPref();
        String[] categories = app_data.getCategories();

        for(int i = 0;i<category_buttons.size();i++){
            String category = categories[i];
            boolean unlocked = sharedPreferences.getBoolean(category,false);
            if(unlocked){
                Button category_is_unlocked = category_buttons.get(i);
                category_is_unlocked.setBackgroundColor(Color.parseColor(app_data.getButton_default_color()));
                category_is_unlocked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        go_to_game_screen();


                    }
                });

            }
            else{
                //not unlocked
                Button category_not_unlocked = category_buttons.get(i);
                category_not_unlocked.setBackgroundColor(Color.parseColor(app_data.getButton_disabled_color()));
                category_not_unlocked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        generate_not_unlocked_toast();
                    }
                });
            }

        }

    }
    private void generate_not_unlocked_toast(){
        Toast toast = Toast.makeText(game_context, "⚠️Locked\n\nPlease purchase Lessons with coins to unlock the game for this category!", (int)10000);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.parseColor("#ffc3bf"), PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be editted
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#343A40"));
        text.setGravity(Gravity.CENTER);

        toast.show();
    }

    private void go_to_game_screen(){

    }
    public static void setGame_context(Context context){
        game_context = context;
    }
}