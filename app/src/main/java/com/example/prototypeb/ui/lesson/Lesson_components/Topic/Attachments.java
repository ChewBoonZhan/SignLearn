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
import com.example.prototypeb.controller.lesson_screen.Attachments.Adore_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Dislike_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Iloveyou_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Like_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;


public class Attachments extends Button_notification{
    //attachment context in Lesson
    private Context attachments_context;
    //constant for button
    private Button button;
    //component for dislike sign
    private Dislike_screen_components dislike_screen_components;
    //component for I love you sign
    private Iloveyou_screen_components iloveyou_screen_components;
    //component for like sign
    private Like_screen_components like_screen_components;
    //component for adore sign
    private Adore_screen_components adore_screen_components;
    //the topic in the Lesson
    private Lesson_topics lesson_topics = this;

    /**
     * Create new instance for all of the signs and its context
     */
    public Attachments(){
        dislike_screen_components = new Dislike_screen_components();
        iloveyou_screen_components = new Iloveyou_screen_components();
        like_screen_components = new Like_screen_components();
        adore_screen_components = new Adore_screen_components();
        this.attachments_context = LessonFragment.getLesson_context();
    }

    /**
     * Initialize the attachments context
     * @param attachments_context
     */
    public Attachments(Context attachments_context){
        this.attachments_context = attachments_context;
    }

    /**
     * Initialize the signs for Attachments
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attachments_menu);
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
        set_notifi_text_visible(attachments_context);
    }

    /**
     * get the notification text for Attachments signs
     */
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.like_notifi));
        notifi_text.add(findViewById(R.id.dislike_notifi));
        notifi_text.add(findViewById(R.id.iloveyou_notifi));
        notifi_text.add(findViewById(R.id.adore_notifi));
    }

    /**
     * manipulate the button what to do
     */
    private void set_buttons_on_click(){
        //Like button
        button = (Button) findViewById(R.id.like_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, like_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //Dislike button
        button = (Button) findViewById(R.id.dislike_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) { openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, dislike_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
            }
        });
        //ILoveYou button
        button = (Button) findViewById(R.id.iloveyou_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) { openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, iloveyou_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
            }
        });
        //Adore button
        button = (Button) findViewById(R.id.adore_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) { openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, adore_screen_components)
                        .putExtra(translator_label,category_elements.get(3))
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
     * @return  locked_on_click - event that occur during on click
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
            Intent intent = new Intent(v.getContext(), Attachments.class);
            attachments_context.startActivity(intent);


        }
    };
    /**
     * called when click on locked topic
     */
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(attachments_context,lesson_topics,v);
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

        return categories[2];
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
        return 30;
    }
}