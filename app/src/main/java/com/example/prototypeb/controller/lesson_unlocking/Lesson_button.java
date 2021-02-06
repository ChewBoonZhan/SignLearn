package com.example.prototypeb.controller.lesson_unlocking;

import android.widget.Button;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.util.ArrayList;

public class Lesson_button {
    public Button get_specific_button(String category){
        ArrayList<Button> category_buttons = LessonFragment.getCategory_buttons();
        App_data app_data = new App_data();

        return category_buttons.get(app_data.getCategory_to_index().get(category));
    }
}
