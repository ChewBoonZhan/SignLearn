package com.example.prototypeb.ui.lesson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;

import com.example.prototypeb.controller.category.Category_init;
import com.example.prototypeb.controller.file_connections.File_connection_key;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Adverbs;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Alphabets;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Attachments;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics_init;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Numbers;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Pronouns;
import com.example.prototypeb.controller.file_connections.File_connections;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonFragment extends Fragment {

    private LessonViewModel lessonViewModel;
    private File_connections file_connections;

    private static Context lesson_context;
    private ArrayList <Button> category_buttons;
    public static ArrayList <TextView> category_notifi;
    private View lesson_root;

    private Lesson_topics_init lesson_topics_init;
    private Category_init category_init;
    public LessonFragment(Context context){
        this.lesson_context = context;

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lessonViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LessonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);
        lesson_root = root;

        lesson_topics_init = new Lesson_topics_init(lesson_context);
        file_connections = new File_connections(lesson_context);

        category_init = new Category_init(lesson_context,lesson_topics_init);
        init_category_buttons();
        get_screen_lesson_notifi();
        init_categories_according_to_unlocked();


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        init_lesson_learnt();
    }
    private void get_screen_lesson_notifi(){
        category_notifi = new ArrayList<TextView>();
        category_notifi.add(lesson_root.findViewById(R.id.lesson_1_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_2_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_3_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_4_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_5_notifi));
    }
    private void init_lesson_learnt(){

        App_data app_data = new App_data();
        Category_elements category_elements = new Category_elements();

        String[] categories = app_data.getCategories();
        int length = categories.length;
        for(int i = 0;i<length;i++){
            ArrayList <String> elements = category_elements.get_category_elements_testable(i);     //all_category_elements.get(categories[i]);

            int category_elements_length  = elements.size();


            int number_of_unpassed = 0;
            for(int j = 0;j<category_elements_length;j++){

                if(!file_connections.check_lesson_learnt(elements.get(j).toLowerCase())){
                    number_of_unpassed++;
                }


            }
            if(number_of_unpassed <= 0){
                category_notifi.get(i).setVisibility(View.GONE);
            }
            else{
                category_notifi.get(i).setText((String)(number_of_unpassed + ""));
            }


        }
    }
    private void init_categories_according_to_unlocked(){
        category_init.init_category_button_according_to_unlocked(category_buttons,category_notifi,false);
    }
    private void init_category_buttons(){
        category_buttons = new ArrayList<Button>();
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson1_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson2_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson3_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson4_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson5_id));


    }
    public static ArrayList <TextView> getCategory_notifi(){
        return category_notifi;
    }



    public static Context getLesson_context(){
        return lesson_context;
    }
}