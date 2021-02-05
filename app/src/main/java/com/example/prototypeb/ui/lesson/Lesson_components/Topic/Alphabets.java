package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.lesson_screen.Alphabets.A_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.B_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.C_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.Y_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;


public class Alphabets extends AppCompatActivity implements  Lesson_topics{
    private Context alphabets_context;
    private Button button;
    private A_screen_components a_screen_components;
    private B_screen_components b_screen_components;
    private C_screen_components c_screen_components;
    private Y_screen_components y_screen_components;
    public Alphabets(){
        a_screen_components = new A_screen_components();
        b_screen_components = new B_screen_components();
        c_screen_components = new C_screen_components();
        y_screen_components = new Y_screen_components();
    }
    public Alphabets(Context alphabets_context){
        this.alphabets_context = alphabets_context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabets_menu);

        //telling the button what to do
        //A button
        button = (Button) findViewById(R.id.a_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", a_screen_components));
            }
        });
        //B button
        button = (Button) findViewById(R.id.b_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", b_screen_components));
            }
        });
        //C button
        button = (Button) findViewById(R.id.c_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", c_screen_components));
            }
        });
        //Y button
        button = (Button) findViewById(R.id.y_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra("screen_components", y_screen_components));
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
            Intent intent = new Intent(v.getContext(), Alphabets.class);
            alphabets_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {



        }
    };
}