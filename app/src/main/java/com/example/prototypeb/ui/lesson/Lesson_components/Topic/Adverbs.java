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
import com.example.prototypeb.controller.lesson_screen.Adverbs.Almost_screen_components;
import com.example.prototypeb.controller.lesson_screen.Adverbs.Later_screen_components;
import com.example.prototypeb.controller.lesson_screen.Adverbs.No_screen_components;
import com.example.prototypeb.controller.lesson_screen.Adverbs.Yes_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.ui.game.Game_components.Game_adverbs;
import com.example.prototypeb.ui.game.Game_components.Game_components;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Adverbs extends Button_notification{
    //adverb context in Lesson
    private Context adverbs_context;
    //constant for button
    private Button button;
    //component for No sign
    private No_screen_components no_screen_components;
    //component for Yes sign
    private Yes_screen_components yes_screen_components;
    //component for Almost sign
    private Almost_screen_components almost_screen_components;
    //component for Later sign
    private Later_screen_components later_screen_components;

    //the topic in the Lesson
    private Lesson_topics lesson_topics = this;

    /**
     * Create new instance for all of the signs and its context
     */
    public Adverbs(){
        no_screen_components = new No_screen_components();
        yes_screen_components = new Yes_screen_components();
        almost_screen_components = new Almost_screen_components();
        later_screen_components = new Later_screen_components();
        this.adverbs_context = LessonFragment.getLesson_context();

    }

    /**
     * Initialize the adverbs context
     * @param adverbs_context
     */
    public Adverbs(Context adverbs_context){
        this.adverbs_context = adverbs_context;

    }

    /**
     * Initialize the signs for Adverbs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adverbs_menu);

        set_buttons_on_click();
        get_screen_elements();
        get_noti_text();

        set_back_button_onclick();
        set_title_text(this.toString()+" Syllabus");

        init_category_elements();

    }

    /**
     * Resume action
     */
    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible(adverbs_context);
    }


    /**
     * get the notification text
     */
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.yes_notifi));
        notifi_text.add(findViewById(R.id.no_notifi));
        notifi_text.add(findViewById(R.id.almost_notifi));
        notifi_text.add(findViewById(R.id.later_notifi));
    }

    /**
     * manipulate the button what to do
     */
    private void set_buttons_on_click(){

        //Yes button
        button = (Button) findViewById(R.id.yes_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, yes_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
            }
        });
        //No button
        button = (Button) findViewById(R.id.no_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, no_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //Almost button
        button = (Button) findViewById(R.id.almost_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, almost_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //Later button
        button = (Button) findViewById(R.id.later_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, later_screen_components)
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

            Intent intent = new Intent(v.getContext(), Adverbs.class);
            adverbs_context.startActivity(intent);


        }
    };
    /**
     * called when click on locked topic
     */
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(adverbs_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();


        }
    };

    /**
     *  get the string of the sign from data file
     * @return category - string that represents the current category
     */
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[0];
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
        return 10;
    }


}