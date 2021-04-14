package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.toast.Success_toast;
import com.example.prototypeb.ui.game.GameFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class Game_adverbs extends Game_screen {
    //Adverbs game context
    private Context adverbs_context;
    //put Adverbs sign languages as string in arrays
    private ArrayList <String> signLang = new ArrayList<String>();
    //declare app data index
    private int app_data_index = 0;
    public Game_adverbs(){
        super();
        init_syllabus(app_data_index);
    }

    /**
     * Initialize adverbs syllabus
     * @param context
     */
    public Game_adverbs(Context context){
        super();
        init_syllabus(app_data_index);

        adverbs_context = context;
    }

    /**
     * Method called upon entering Adverbs game
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
     * Adverbs game activity displayed
     */
    private View.OnClickListener on_unlocked_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Game_adverbs.class);
            adverbs_context.startActivity(intent);
        }
    };




}
