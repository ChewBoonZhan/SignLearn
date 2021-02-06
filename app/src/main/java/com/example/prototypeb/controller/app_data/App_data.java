package com.example.prototypeb.controller.app_data;

import java.util.ArrayList;
import java.util.HashMap;

public class App_data {
    private String[] categories = new String[]{"Adverbs","Alphabets", "Attachments","Numbers","Pronoun"};
    public String[] getCategories() {
        return categories;
    }

    private String button_default_color = "#28346E";
    public String getButton_default_color() {
        return button_default_color;
    }

    private String button_disabled_color = "#949ED1";
    public String getButton_disabled_color() {
        return button_disabled_color;
    }

    private HashMap <String, Integer> points_to_unlock_category= new HashMap<String, Integer>();
    public App_data(){
        init_points_to_unlock_category();
        init_category_to_index();

    }
    private void init_points_to_unlock_category(){
        int length = categories.length;
        int points_required = 0;
        for(int i = 0;i<length;i++){
            points_to_unlock_category.put(categories[i],points_required);
            points_required +=10;
        }
    }
    public HashMap <String, Integer> getPoints_to_unlock_category(){
        return points_to_unlock_category;
    }

    private HashMap <String, Integer> category_to_index = new HashMap <String, Integer>();
    private void init_category_to_index(){
        int length = categories.length;
        for(int i = 0;i<length;i++){
            category_to_index.put(categories[i],i);
        }
    }
    public  HashMap <String, Integer> getCategory_to_index(){
        return category_to_index;
    }


}
