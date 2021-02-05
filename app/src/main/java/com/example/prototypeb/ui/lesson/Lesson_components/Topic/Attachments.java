package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.lesson_screen.Attachments.Dislike_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Iloveyou_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Like_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;


public class Attachments extends AppCompatActivity implements Lesson_topics{
    private Context attachments_context;
    private Button button;
    private Dislike_screen_components dislike_screen_components;
    private Iloveyou_screen_components iloveyou_screen_components;
    private Like_screen_components like_screen_components;


    public Attachments(){
        dislike_screen_components = new Dislike_screen_components();
        iloveyou_screen_components = new Iloveyou_screen_components();
        like_screen_components = new Like_screen_components();
    }
    public Attachments(Context attachments_context){
        this.attachments_context = attachments_context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attachments_menu);

        //telling the button what to do
        //Like button
        button = (Button) findViewById(R.id.like_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", like_screen_components));
            }
        });
        //Dislike button
        button = (Button) findViewById(R.id.dislike_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", dislike_screen_components));
            }
        });
        //ILoveYou button
        button = (Button) findViewById(R.id.iloveyou_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", iloveyou_screen_components));
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
            Intent intent = new Intent(v.getContext(), Attachments.class);
            attachments_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {



        }
    };
}