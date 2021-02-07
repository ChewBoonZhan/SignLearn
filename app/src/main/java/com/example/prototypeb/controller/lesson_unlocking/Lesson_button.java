package com.example.prototypeb.controller.lesson_unlocking;

import android.widget.Button;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.ui.lesson.LessonFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lesson_button {
    public Button get_specific_button(String category){
        ArrayList<Button> category_buttons = LessonFragment.getCategory_buttons();
        App_data app_data = new App_data();
        List<String> categories = Arrays.asList(app_data.getCategories());
        int index = categories.indexOf(category);

        //int index = app_data.getCategory_to_index().get(category);
        return category_buttons.get(index);
    }
}
