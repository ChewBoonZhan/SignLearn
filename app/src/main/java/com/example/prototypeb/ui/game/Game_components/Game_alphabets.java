package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;

import java.util.ArrayList;

public class Game_alphabets extends Game_screen {
    //Alphabets game context
    private Context alphabet_context;
    //put Alphabets sign languages as string in arrays
    private ArrayList <String> signLang = new ArrayList<String>();
    //declare app data index
    private int app_data_index = 1;
    public Game_alphabets(){
        super();
        init_syllabus(app_data_index);
    }
    /**
     * Initialize alphabets syllabus
     * @param context
     */
    public Game_alphabets(Context context){
        super();
        init_syllabus(app_data_index);
        alphabet_context = context;
    }

    /**
     * Method called upon entering Alphabets game
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Resume action
     */
    @Override
    protected void onResume() {
        super.onResume();
        super.setContext(this);

    }

    /**
     * method is called upon clicking category if category has been unlocked
     * @return on_unlocked_click
     */
    public View.OnClickListener get_unlocked_On_click(){

        return on_unlocked_click;
    }

    /**
     * method is called upon clicking category if category is still locked
     * @return locked_On_click
     */
    public View.OnClickListener get_locked_On_click(){
        return locked_On_click;
    }

    /**
     * Alphabets game activity displayed
     */
    private View.OnClickListener on_unlocked_click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Game_alphabets.class);
            alphabet_context.startActivity(intent);


        }
    };

}
