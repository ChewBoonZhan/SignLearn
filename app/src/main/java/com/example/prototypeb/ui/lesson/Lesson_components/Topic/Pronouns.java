package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_screen.Pronouns.Me_screen_components;
import com.example.prototypeb.controller.lesson_screen.Pronouns.You_screen_components;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;

public class Pronouns extends Sub_action_bar implements Lesson_topics{
    private Context pronouns_context;
    private Button button;
    private Me_screen_components me_screen_components;
    private You_screen_components you_screen_components;
    private Lesson_topics lesson_topics = this;
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
        set_buttons_on_click();
        get_screen_elements();
        set_back_button_onclick();
        set_title_text(this.toString()+" Syllabus");

    }
    private void set_buttons_on_click(){
        //telling the button what to do
        //I button
        button = (Button) findViewById(R.id.me_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, me_screen_components)
                        .putExtra(translator_label,"Me")
                        .putExtra(translator_lesson_topics,get_model_category())

                );
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
                        .putExtra(screen_component, you_screen_components)
                        .putExtra(translator_label,"You")
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
            Intent intent = new Intent(v.getContext(), Pronouns.class);
            pronouns_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(pronouns_context,lesson_topics);
            lesson_unlocking.user_clicks_locked_lesson();


        }
    };
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[4];
    }
    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 50;
    }
}