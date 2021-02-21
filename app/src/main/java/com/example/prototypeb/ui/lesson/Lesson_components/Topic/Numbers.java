package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num10_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num1_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num2_screen_components;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;


public class Numbers extends AppCompatActivity implements Lesson_topics{
    private Context numbers_context;
    private Button button;
    private Num1_screen_components num1_screen_components;
    private Num2_screen_components num2_screen_components;
    private Num10_screen_components num10_screen_components;
    private Lesson_topics lesson_topics = this;
    public Numbers(){
        num1_screen_components = new Num1_screen_components();
        num2_screen_components = new Num2_screen_components();
        num10_screen_components = new Num10_screen_components();
    }
    public Numbers(Context numbers_context){
        this.numbers_context = numbers_context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_menu);

        //telling the button what to do
        //1 button
        button = (Button) findViewById(R.id.num1_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num1_screen_components)
                        .putExtra(translator_label,"one")
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //2 button
        button = (Button) findViewById(R.id.num2_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num2_screen_components)
                        .putExtra(translator_label,"two")
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //3 button
        button = (Button) findViewById(R.id.num10_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num10_screen_components)
                        .putExtra(translator_label,"ten")
                        .putExtra(translator_lesson_topics,get_model_category())
                );
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
            Intent intent = new Intent(v.getContext(), Numbers.class);
            numbers_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(numbers_context,lesson_topics);
            lesson_unlocking.user_clicks_locked_lesson();

        }
    };
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[3];
    }
    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 40;
    }
}