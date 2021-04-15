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
import com.example.prototypeb.controller.lesson_screen.Numbers.Num10_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num1_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num2_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num3_screen_components;
import com.example.prototypeb.controller.lesson_screen.Numbers.Num4_screen_components;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;


public class Numbers extends Button_notification {
    //numbers context in Lesson
    private Context numbers_context;
    //constant for button
    private Button button;
    //component for 1 sign
    private Num1_screen_components num1_screen_components;
    //component for 2 sign
    private Num2_screen_components num2_screen_components;
    //component for 10 sign
    private Num10_screen_components num10_screen_components;
    //component for 3 sign
    private Num3_screen_components num3_screen_components;
    //component for 4 sign
    private Num4_screen_components num4_screen_components;
    //the topic in the Lesson
    private Lesson_topics lesson_topics = this;

    /**
     * Create new instance for all of the signs and its context
     */
    public Numbers(){
        num1_screen_components = new Num1_screen_components();
        num2_screen_components = new Num2_screen_components();
        num10_screen_components = new Num10_screen_components();
        num3_screen_components = new Num3_screen_components();
        num4_screen_components = new Num4_screen_components();
        this.numbers_context = LessonFragment.getLesson_context();
    }

    /**
     * Initialize the numbers context
     * @param numbers_context
     */
    public Numbers(Context numbers_context){
        this.numbers_context = numbers_context;
    }

    /**
     * Initialize the signs for Numbers
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numbers_menu);
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
        set_notifi_text_visible(numbers_context);
    }

    /**
     * get the notification text for Numbers signs
     */
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.num1_notifi));
        notifi_text.add(findViewById(R.id.num2_notifi));
        notifi_text.add(findViewById(R.id.num3_notifi));
        notifi_text.add(findViewById(R.id.num4_notifi));
        notifi_text.add(findViewById(R.id.num10_notifi));
    }

    /**
     * manipulate the button what to do
     */
    private void set_buttons_on_click(){
        //1 button
        button = (Button) findViewById(R.id.num1_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num1_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //2 button
        button = (Button) findViewById(R.id.num2_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num2_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });


        //10 button
        button = (Button) findViewById(R.id.num10_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num10_screen_components)
                        .putExtra(translator_label,category_elements.get(4))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });

        // 3 button
        button = (Button) findViewById(R.id.num3_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num3_screen_components)
                        .putExtra(translator_label,category_elements.get(3))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });

        //4 button
        button = (Button) findViewById(R.id.num4_id);
        button.setOnClickListener(new View.OnClickListener() {
            //upon clicking the button, go to openActivity
            @Override
            public void onClick(View v) {
                openActivity();
            }
            //display elements of description for the sign
            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num4_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,"")
                );
            }
        });
    }
    /**
     * Called when click on the topic that has been unlocked
     * @return
     */
    public View.OnClickListener get_unlocked_On_click(){

        return on_unlocked_click;
    }

    /**
     * Called when click on the topic that is locked
     * @return
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
            Intent intent = new Intent(v.getContext(), Numbers.class);
            numbers_context.startActivity(intent);


        }
    };

    /**
     * called when click on locked topic
     */
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(numbers_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();

        }
    };

    /**
     * get the string of the sign from data file
     * @return
     */
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[3];
    }

    /**
     * get the string of the model category
     * @return
     */
    public String get_model_category(){
        return toString().toLowerCase();
    }

    /**
     * Obtain 40 points
     * @return
     */
    public int get_required_score(){
        return 40;
    }
}