package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;
import com.example.prototypeb.ui.lesson.Lesson_components.Adverbs.No;
import com.example.prototypeb.ui.lesson.Lesson_components.Adverbs.Yes;

public class Adverbs extends AppCompatActivity implements Lesson_topics{
    private Context adverbs_context;
    private Button button;
    public Adverbs(){}
    public Adverbs(Context adverbs_context){
        this.adverbs_context = adverbs_context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adverbs_menu);

        //telling the button what to do
        //Yes button
        button = (Button) findViewById(R.id.yes_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Yes.class);
                startActivity(intent);
            }
        });
        //No button
        button = (Button) findViewById(R.id.no_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), No.class);
                startActivity(intent);
            }
        });




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
            Intent intent = new Intent(v.getContext(), Adverbs.class);
            adverbs_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {



        }
    };



}