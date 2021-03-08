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
import com.example.prototypeb.controller.lesson_unlocking.Lesson_unlocking;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;


public class Numbers extends Sub_action_bar implements Lesson_topics{
    private Context numbers_context;
    private Button button;
    private Num1_screen_components num1_screen_components;
    private Num2_screen_components num2_screen_components;
    private Num10_screen_components num10_screen_components;
    private Lesson_topics lesson_topics = this;

    private ArrayList <TextView> notifi_text;
    private ArrayList<String> category_elements;

    public Numbers(){
        num1_screen_components = new Num1_screen_components();
        num2_screen_components = new Num2_screen_components();
        num10_screen_components = new Num10_screen_components();
        this.numbers_context = LessonFragment.getLesson_context();
    }
    public Numbers(Context numbers_context){
        this.numbers_context = numbers_context;
    }

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
    @Override
    protected void onResume() {
        super.onResume();
        set_notifi_text_visible();
    }

    private void init_category_elements(){
        Category_elements all_category_elements = new Category_elements();
        category_elements = all_category_elements.getCategory_elements().get(toString());
    }
    private void get_noti_text(){
        notifi_text = new ArrayList<TextView>();
        notifi_text.add(findViewById(R.id.num1_notifi));
        notifi_text.add(findViewById(R.id.num2_notifi));
        notifi_text.add(findViewById(R.id.num10_notifi));
    }
    private void set_notifi_text_visible(){
        File_connections file_connections = new File_connections(numbers_context);
        int length = notifi_text.size();
        for(int i = 0;i<length;i++){
            if(file_connections.check_lesson_learnt(category_elements.get(i).toLowerCase())){
                notifi_text.get(i).setVisibility(View.GONE);
            }
        }
    }
    private void set_buttons_on_click(){
        //telling the button what to do
        //1 button
        button = (Button) findViewById(R.id.num1_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

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
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num2_screen_components)
                        .putExtra(translator_label,category_elements.get(1))
                        .putExtra(translator_lesson_topics,get_model_category())
                );
            }
        });
        //3 button
        button = (Button) findViewById(R.id.num10_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                startActivity(new Intent(getApplicationContext(), Lesson_screen.class)
                        .putExtra(screen_component, num10_screen_components)
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
            Intent intent = new Intent(v.getContext(), Numbers.class);
            numbers_context.startActivity(intent);


        }
    };
    private View.OnClickListener locked_On_click= new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Lesson_unlocking lesson_unlocking = new Lesson_unlocking(numbers_context,lesson_topics,v);
            lesson_unlocking.user_clicks_locked_lesson();

        }
    };
    @Override
    public String toString(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();

        return categories[3];
    }
    public String get_model_category(){
        return toString().toLowerCase();
    }
    public int get_required_score(){
        return 40;
    }
}