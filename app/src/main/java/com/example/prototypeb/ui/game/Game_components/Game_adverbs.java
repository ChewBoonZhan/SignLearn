package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.prototypeb.R;


public class Game_adverbs extends AppCompatActivity implements  Game_components{
    private Context adverbs_context;
    public Game_adverbs(){}
    public Game_adverbs(Context context){
        adverbs_context = context;
    }
    public View.OnClickListener getOn_click(){
        return on_click;
    }

    private View.OnClickListener on_click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Game_adverbs.class);
            adverbs_context.startActivity(intent);


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_adverbs);
    }
}