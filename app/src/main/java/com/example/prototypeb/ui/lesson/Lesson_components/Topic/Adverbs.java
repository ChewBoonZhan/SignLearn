package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.lesson_screen.Adverbs.No_screen_components;
import com.example.prototypeb.controller.lesson_screen.Adverbs.Yes_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;
import com.example.prototypeb.ui.game.Game_components.Game_components;


public class Adverbs extends AppCompatActivity implements Lesson_topics{
    private Context adverbs_context;
    private Button button;
    private No_screen_components no_screen_components;
    private Yes_screen_components yes_screen_components;
    private Lesson_topics lesson_topics = this;

    public Adverbs(){
        no_screen_components = new No_screen_components();
        yes_screen_components = new Yes_screen_components();

    }
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
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, yes_screen_components)
                        .putExtra(translator_label,"Yes")
                        .putExtra(translator_lesson_topics,get_model_category())

                );
                /*
                Intent intent = new Intent(getApplicationContext(), Lesson_screen.class);
                startActivity(intent);

                 */
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
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, no_screen_components)
                        .putExtra(translator_label,"No")
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
            Intent intent = new Intent(v.getContext(), Adverbs.class);
            adverbs_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(adverbs_context,lesson_topics);
            lesson_unlocking.user_clicks_locked_lesson();


        }
    };

    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[0];
    }

    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 10;
    }


}