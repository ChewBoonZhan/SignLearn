package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Lesson_components.Pronouns.Me;
import com.example.prototypeb.ui.lesson.Lesson_components.Pronouns.You;

public class Pronouns extends AppCompatActivity implements Lesson_topics{
    private Context pronouns_context;
    private Button button;
    public Pronouns(){}
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
                Intent intent = new Intent(getApplicationContext(), Me.class);
                startActivity(intent);
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
                Intent intent = new Intent(getApplicationContext(), You.class);
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