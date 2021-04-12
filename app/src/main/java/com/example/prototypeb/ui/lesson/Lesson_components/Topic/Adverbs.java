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
    private Context adverbs_context;
    private Button button;
    private No_screen_components no_screen_components;
    private Yes_screen_components yes_screen_components;
    private Almost_screen_components almost_screen_components;
    private Later_screen_components later_screen_components;

    private Lesson_topics lesson_topics = this;

    public Adverbs(){
        no_screen_components = new No_screen_components();
        yes_screen_components = new Yes_screen_components();
        almost_screen_components = new Almost_screen_components();
        later_screen_components = new Later_screen_components();
        this.adverbs_context = LessonFragment.getLesson_context();

    }
    public Adverbs(Context adverbs_context){
        this.adverbs_context = adverbs_context;

    }

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

    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible(adverbs_context);
    }


    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.yes_notifi));
        notifi_text.add(findViewById(R.id.no_notifi));
    }

    private void set_buttons_on_click(){
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
                        .putExtra(translator_label,category_elements.get(0))
                        .putExtra(translator_lesson_topics,get_model_category())

                );
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
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });

        button = (Button) findViewById(R.id.almost_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, almost_screen_components)
                        .putExtra(translator_label,"")
                        .putExtra(translator_lesson_topics,"")
                );
            }
        });

        button = (Button) findViewById(R.id.later_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, later_screen_components)
                        .putExtra(translator_label,"")
                        .putExtra(translator_lesson_topics,"")
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
            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(adverbs_context,lesson_topics,v);
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