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
import com.example.prototypeb.controller.lesson_screen.Attachments.Dislike_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Iloveyou_screen_components;
import com.example.prototypeb.controller.lesson_screen.Attachments.Like_screen_components;
import com.example.prototypeb.controller.lesson_screen.Lesson_screen;
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;


public class Attachments extends Button_notification implements Lesson_topics{
    private Context attachments_context;
    private Button button;
    private Dislike_screen_components dislike_screen_components;
    private Iloveyou_screen_components iloveyou_screen_components;
    private Like_screen_components like_screen_components;
    private Lesson_topics lesson_topics = this;

    public Attachments(){
        dislike_screen_components = new Dislike_screen_components();
        iloveyou_screen_components = new Iloveyou_screen_components();
        like_screen_components = new Like_screen_components();
        this.attachments_context = LessonFragment.getLesson_context();
    }
    public Attachments(Context attachments_context){
        this.attachments_context = attachments_context;
    }

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
    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible(attachments_context);
    }


    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.like_notifi));
        notifi_text.add(findViewById(R.id.dislike_notifi));
        notifi_text.add(findViewById(R.id.iloveyou_notifi));
    }

    private void set_buttons_on_click(){
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
                        .putExtra(screen_component, like_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
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
                        .putExtra(screen_component, dislike_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
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
                        .putExtra(screen_component, iloveyou_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
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
            Intent intent = new Intent(v.getContext(), Attachments.class);
            attachments_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(attachments_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();

        }
    };
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[2];
    }
    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 30;
    }
}