package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.lesson_screen.Alphabets.A_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.B_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.C_screen_components;
import com.example.prototypeb.controller.lesson_screen.Alphabets.Y_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;
import com.example.prototypeb.ui.lesson.LessonFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Alphabets extends Button_notification{
    //alphabet context in Lesson
    private Context alphabets_context;
    //constant for button
    private Button button;
    //component for A sign
    private A_screen_components a_screen_components;
    //component for B sign
    private B_screen_components b_screen_components;
    //component for C sign
    private C_screen_components c_screen_components;
    //component for Y sign
    private Y_screen_components y_screen_components;
    //the topic in the Lesson
    private Lesson_topics lesson_topics = this;

    /**
     * Create new instance for all of the signs and its context
     */
    public Alphabets(){
        a_screen_components = new A_screen_components();
        b_screen_components = new B_screen_components();
        c_screen_components = new C_screen_components();
        y_screen_components = new Y_screen_components();
        this.alphabets_context = LessonFragment.getLesson_context();
    }

    /**
     * Initialize the alphabets context
     * @param alphabets_context
     */
    public Alphabets(Context alphabets_context){
        this.alphabets_context = alphabets_context;

    }

    /**
     * Initialize the signs for Alphabets
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabets_menu);
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
        set_notifi_text_visible(alphabets_context);
    }

    /**
     * get the notification text
     */
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.a_notifi));
        notifi_text.add(findViewById(R.id.b_notifi));
        notifi_text.add(findViewById(R.id.c_notifi));
        notifi_text.add(findViewById(R.id.y_notifi));
    }

    /**
     * manipulate the button what to do
     */
    private void set_buttons_on_click(){
        //A button
        button = (Button) findViewById(R.id.a_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, a_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //B button
        button = (Button) findViewById(R.id.b_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }

            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, b_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //C button
        button = (Button) findViewById(R.id.c_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, c_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //Y button
        button = (Button) findViewById(R.id.y_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, y_screen_components)
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
            Intent intent = new Intent(v.getContext(), Alphabets.class);
            alphabets_context.startActivity(intent);


        }
    };
    /**
     * called when click on locked topic
     */
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(alphabets_context,lesson_topics,v);
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

        return categories[1];
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
        return 20;
    }
}