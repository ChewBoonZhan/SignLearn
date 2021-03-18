package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.prototypeb.R;

import java.util.ArrayList;

public class Game_numbers extends Game_screen {
    private Context numbers_context;
    private ArrayList <String> signLang = new ArrayList<String>();
    public Game_numbers(){
        super();
        init_syallabus();
    }
    public Game_numbers(Context context){
        super();
        numbers_context = context;
        init_syallabus();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.setContext(this);



    }
    private void init_syallabus(){


        //Initialize Question image via String
        signLang.add("one");
        signLang.add("two");
        signLang.add("three");
        signLang.add("four");

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
            Intent intent = new Intent(v.getContext(), Game_numbers.class);
            numbers_context.startActivity(intent);


        }
    };

}
