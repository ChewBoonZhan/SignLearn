package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_screen.Pronouns.Me_screen_components;
import com.example.prototypeb.controller.lesson_screen.Pronouns.They_screen_components;
import com.example.prototypeb.controller.lesson_screen.Pronouns.We_screen_components;
import com.example.prototypeb.controller.lesson_screen.Pronouns.You_screen_components;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;

public class Pronouns extends Button_notification{
    //Pronouns context in Lesson
    private Context pronouns_context;
    //constant for button
    private Button button;
    //component for me sign
    private Me_screen_components me_screen_components;
    //component for you sign
    private You_screen_components you_screen_components;
    //component for we sign
    private We_screen_components we_screen_components;
    //component for they sign
    private They_screen_components they_screen_components;
    //the topic in the Lesson
    private Lesson_topics lesson_topics = this;

    /**
     * Create new instance for all of the signs and its context
     */
    public Pronouns(){

        me_screen_components = new Me_screen_components();
        you_screen_components = new You_screen_components();
        we_screen_components = new We_screen_components();
        they_screen_components = new They_screen_components();
        this.pronouns_context = LessonFragment.getLesson_context();
    }

    /**
     * Initialize the pronouns context
     * @param pronouns_context
     */
    public Pronouns(Context pronouns_context){
        this.pronouns_context = pronouns_context;
    }

    /**
     * Initialize the signs for Numbers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronouns_menu);
        set_buttons_on_click();
        get_screen_elements();
        set_back_button_onclick();
        set_title_text(this.toString()+" Syllabus");
        init_category_elements();
        get_noti_text();


    }

    /**
     * Resume action
     */
    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible(pronouns_context);
    }

    /**
     * get the notification text for Pronouns signs
     */
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.me_notifi));
        notifi_text.add(findViewById(R.id.you_notifi));
        notifi_text.add(findViewById(R.id.they_notifi));
        notifi_text.add(findViewById(R.id.we_notifi));
    }

    /**
     * manipulate the button what to do
     */
    private void set_buttons_on_click(){
        //I button
        button = (Button) findViewById(R.id.me_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, me_screen_components)
                        .putExtra(translator_label, category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
            }
        });
        //You button
        button = (Button) findViewById(R.id.you_id);
        button.setOnClickListener(new View.OnClickListener() {
            //display elements of description for the sign
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, you_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });

        // We button
        button = (Button) findViewById(R.id.we_id);
        button.setOnClickListener(new View.OnClickListener() {
            //display elements of description for the sign
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, we_screen_components)
                        .putExtra(translator_label,category_elements.get(3))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });

        button = (Button) findViewById(R.id.they_id);
        button.setOnClickListener(new View.OnClickListener() {
            //display elements of description for the sign
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, they_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
    }
    /**
     * Called when click on the topic that has been unlocked
     * @return unlocked_on_click - event that occurs during on click
     */
    public View.OnClickListener get_unlocked_On_click(){

        return on_unlocked_click;
    }

    /**
     * Called when click on the topic that is locked
     * @return locked_on_click - event that occur during on click
     */
    public View.OnClickListener get_locked_On_click(){
        return locked_On_click;
    }

    /**
     * called when click on unlocked topic, display the activity
     */
    private View.OnClickListener on_unlocked_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Pronouns.class);
            pronouns_context.startActivity(intent);


        }
    };

    /**
     * called when click on locked topic
     */
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(pronouns_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();


        }
    };

    /**
     * get the string of the sign from data file
     * @return category - string that represents the current category
     */
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[4];
    }

    /**
     * get the string of the model category
     * @return model_category - string to represent model to be loaded in to tflite
     */
    public String get_model_category(){
        return toString().toLowerCase();
    }

    /**
     *
     * @return score_required- score required to unlock the category
     */
    public int get_required_score(){
        return 50;
    }
}