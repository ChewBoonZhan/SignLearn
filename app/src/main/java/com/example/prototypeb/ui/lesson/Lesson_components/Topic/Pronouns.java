package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;

import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_screen.Pronouns.Me_screen_components;
import com.example.prototypeb.controller.lesson_screen.Pronouns.You_screen_components;

public class Pronouns extends AppCompatActivity implements Lesson_topics{
    private Context pronouns_context;
    private Button button;
    private Me_screen_components me_screen_components;
    private You_screen_components you_screen_components;

    public Pronouns(){

        me_screen_components = new Me_screen_components();
        you_screen_components = new You_screen_components();
    }
    public Pronouns(Context pronouns_context){
        this.pronouns_context = pronouns_context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronouns_menu);

        //telling the button what to do
        //I button
        button = (Button) findViewById(R.id.me_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                pronouns_context.startActivity(new Intent(pronouns_context, Lesson_screen.class)
                        .putExtra("screen_components", me_screen_components));
            }
        });
        //You button
        button = (Button) findViewById(R.id.you_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", you_screen_components));
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
            Intent intent = new Intent(v.getContext(), Pronouns.class);
            pronouns_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {



        }
    };
}