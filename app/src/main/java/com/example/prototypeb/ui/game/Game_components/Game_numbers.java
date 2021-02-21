package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.prototypeb.R;

public class Game_numbers extends AppCompatActivity implements Game_components{
    private Context numbers_context;
    public Game_numbers(){}
    public Game_numbers(Context context){
        numbers_context = context;
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
            Intent intent = new Intent(v.getContext(), Game_numbers.class);
            numbers_context.startActivity(intent);


        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_numbers);
    }
}
