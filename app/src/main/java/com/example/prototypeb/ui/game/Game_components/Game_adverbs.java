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
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.toast.Success_toast;
import com.example.prototypeb.ui.game.GameFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class Game_adverbs extends Game_screen implements  Game_components{
    private Context adverbs_context;
    private ArrayList <String> signLang = new ArrayList<String>();

    public Game_adverbs(){
        super();
    }

    public Game_adverbs(Context context){
        adverbs_context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_adverbs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.setContext(this);
        init_syallabus();
        init_game_elements();

    }

    private void init_syallabus(){


        //Initialize Question image via String
        signLang.add("yes");
        signLang.add("no");
        signLang.add("almost");
        signLang.add("later");

        super.setSignLang(signLang);

    }
    public View.OnClickListener get_unlocked_On_click(){

        return on_unlocked_click;
    }

    public View.OnClickListener get_locked_On_click(){
        return locked_On_click;
    }
    private View.OnClickListener on_unlocked_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Game_adverbs.class);
            adverbs_context.startActivity(intent);
        }
    };


}
