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
    //view model of the Lesson System
    private LessonViewModel lessonViewModel;
    //link file to save data
    private File_connections file_connections;

    //the context of the Lesson system
    private static Context lesson_context;
    //button for the category
    private ArrayList <Button> category_buttons;
    //notifications that appears on the topic buttons
    public static ArrayList <TextView> category_notifi;
    //view that contains Lesson display elements
    private View lesson_root;

    //initialize the lesson topic
    private Lesson_topics_init lesson_topics_init;
    //initialize the category
    private Category_init category_init;

    /**
     *
     * @param context Shows the context of Lesson
     */
    public LessonFragment(Context context){
        this.lesson_context = context;

    }

    /**
     * Method is called when use goes to Lesson page
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Initialize the view of Lesson fragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lessonViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LessonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);
        //Show all the Lesson display elements
        lesson_root = root;

        //initialize the topic of Lesson
        lesson_topics_init = new Lesson_topics_init(lesson_context);
        //initialize the file to save data
        file_connections = new File_connections(lesson_context);

        //initialize the Lesson category
        category_init = new Category_init(lesson_context,lesson_topics_init);
        //initialize the topic button
        init_category_buttons();
        //initialize the notification
        get_screen_lesson_notifi();
        //initialize the topic unlocked
        init_categories_according_to_unlocked();


        return root;
    }

    /**
     * called to show categories being unlocked
     */
    @Override
    public void onResume() {
        super.onResume();
        init_lesson_learnt();
    }

    /**
     *called to initialize the notification each of the topic
     */
    private void get_screen_lesson_notifi(){
        category_notifi = new ArrayList<TextView>();
        category_notifi.add(lesson_root.findViewById(R.id.lesson_1_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_2_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_3_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_4_notifi));
        category_notifi.add(lesson_root.findViewById(R.id.lesson_5_notifi));
    }

    /**
     *called when needed to initialize any sign learned
     */
    private void init_lesson_learnt(){

        //update the data in save file
        App_data app_data = new App_data();
        //new instance for category elements
        Category_elements category_elements = new Category_elements();

        //get string class from save data of categories
        String[] categories = app_data.getCategories();
        //length will be the length of string class before
        int length = categories.length;
        //until i surpass length, the bar will keep rising
        for(int i = 0;i<length;i++){
            ArrayList <String> elements = category_elements.get_category_elements_testable(i);     //all_category_elements.get(categories[i]);

            int category_elements_length  = elements.size();


            int number_of_unpassed = 0;
            for(int j = 0;j<category_elements_length;j++){

                if(!file_connections.check_lesson_learnt(elements.get(j).toLowerCase())){
                    number_of_unpassed++;
                }


            }
            //notification in topic button will be gone
            if(number_of_unpassed <= 0){
                category_notifi.get(i).setVisibility(View.GONE);
            }
            //shows the number of notification of unsuccessful sign left in topic button
            else{
                category_notifi.get(i).setText((String)(number_of_unpassed + ""));
            }


        }
    }

    /**
     *called to initialize lesson button after unlocking it
     */
    private void init_categories_according_to_unlocked(){
        category_init.init_category_button_according_to_unlocked(category_buttons,category_notifi,false);
    }

    /**
     *called to initialize the category buttons
     */
    private void init_category_buttons(){
        category_buttons = new ArrayList<Button>();
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson1_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson2_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson3_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson4_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson5_id));


    }

    /**
     *method is called to get the array list of lesson category's notification
     * @return category_notifi - notification for lesson screen
     */
    public static ArrayList <TextView> getCategory_notifi(){
        return category_notifi;
    }


    /**
     *method is called to get the Lesson context
     * @return context  - context of lesson screen
     */
    public static Context getLesson_context(){
        return lesson_context;
    }
}