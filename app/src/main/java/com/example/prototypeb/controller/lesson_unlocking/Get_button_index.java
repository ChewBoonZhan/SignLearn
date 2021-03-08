package com.example.prototypeb.controller.lesson_unlocking;

import com.example.prototypeb.controller.app_data.App_data;

public class Get_button_index {
    public int get_index(String category){
        App_data app_data = new App_data();
        int index = 0;
        String[] categories = app_data.getCategories();
        int length = categories.length;
        for(int i = 0;i<length;i++){
            if (categories[i].equals(category)){
                index = i;
            }
        }
        return index;

    }



}
