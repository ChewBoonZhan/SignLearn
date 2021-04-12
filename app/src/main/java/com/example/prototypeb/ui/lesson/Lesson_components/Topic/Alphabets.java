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
    private Context alphabets_context;
    private Button button;
    private A_screen_components a_screen_components;
    private B_screen_components b_screen_components;
    private C_screen_components c_screen_components;
    private Y_screen_components y_screen_components;
    private Lesson_topics lesson_topics = this;

    public Alphabets(){
        a_screen_components = new A_screen_components();
        b_screen_components = new B_screen_components();
        c_screen_components = new C_screen_components();
        y_screen_components = new Y_screen_components();
        this.alphabets_context = LessonFragment.getLesson_context();
    }
    public Alphabets(Context alphabets_context){
        this.alphabets_context = alphabets_context;

    }
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
    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible(alphabets_context);
    }


    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.a_notifi));
        notifi_text.add(findViewById(R.id.b_notifi));
        notifi_text.add(findViewById(R.id.c_notifi));
        notifi_text.add(findViewById(R.id.y_notifi));
    }

    private void set_buttons_on_click(){
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
                        .putExtra(screen_component, a_screen_components)
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
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
                        .putExtra(screen_component, b_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
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
                        .putExtra(screen_component, c_screen_components)
                        .putExtra(translator_label,category_elements.get(2))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
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
                        .putExtra(screen_component, y_screen_components)
                        .putExtra(translator_label,category_elements.get(3))
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
            Intent intent = new Intent(v.getContext(), Alphabets.class);
            alphabets_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(alphabets_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();

        }
    };
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[1];
    }
    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 20;
    }
}