package com.example.prototypeb.controller.lesson_unlocking;

import com.example.prototypeb.controller.app_data.App_data;

public class Get_button_index {

    /**
     * get index for button for main menu screen of lesson and game
     * @param category - category to check index of its button
     * @return index- index associated to the category
     */
    public int get_index(String category){
        // get categories of app
        App_data app_data = new App_data();


        int index = 0;
        // get the categories and save it in an array
        String[] categories = app_data.getCategories();
        int length = categories.length;

        // get the index of the category associated with the category
        for(int i = 0;i<length;i++){
            if (categories[i].equals(category)){
                index = i;
            }
        }
        return index;

    }



}
