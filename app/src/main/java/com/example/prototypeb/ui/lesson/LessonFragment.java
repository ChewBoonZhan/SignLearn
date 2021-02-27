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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.category.Category_init;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Adverbs;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Alphabets;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Attachments;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics_init;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Numbers;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Pronouns;
import com.example.prototypeb.controller.file_connections.File_connections;

import java.util.ArrayList;

public class LessonFragment extends Fragment {

    private LessonViewModel lessonViewModel;
    private File_connections file_connections;

    private static Context lesson_context;
    private static ArrayList <Button> category_buttons;
    private View lesson_root;
    private App_data app_data;
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
        app_data = new App_data();
        lesson_topics_init = new Lesson_topics_init(lesson_context);
        file_connections = new File_connections(lesson_context);
        category_init = new Category_init(lesson_context,lesson_topics_init);
        init_category_buttons();
        init_category_button_according_to_unlocked();


        return root;
    }
    private void init_category_button_according_to_unlocked(){
        category_init.init_category_button_according_to_unlocked(category_buttons);
    }
    private void init_category_buttons(){
        category_buttons = new ArrayList<Button>();
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson1_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson2_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson3_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson4_id));
        category_buttons.add((Button) lesson_root.findViewById(R.id.lesson5_id));
    }
    public static ArrayList<Button> getCategory_buttons(){
        return category_buttons;
    }

    public static void setLesson_context(Context context){
        lesson_context = context;
    }
}