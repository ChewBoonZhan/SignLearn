package com.example.prototypeb.controller.app_data;

import android.util.Log;

import com.example.prototypeb.controller.category.Category_init;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_elements {
    public Category_elements(){
        init_category_elements();
    }
    private HashMap<String, ArrayList<String>> category_elements = new  HashMap <String, ArrayList <String>>();
    private void init_category_elements(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        ArrayList <String> adverb_elements = new ArrayList<String>();
        adverb_elements.add("Yes");
        adverb_elements.add("No");


        category_elements.put(categories[0],adverb_elements);

        ArrayList <String> alphabets_elements = new ArrayList<String>();
        alphabets_elements.add("\"A\"");
        alphabets_elements.add("\"B\"");
        alphabets_elements.add("\"C\"");
        alphabets_elements.add("\"Y\"");

        category_elements.put(categories[1],alphabets_elements);


        ArrayList <String> attachments_elements = new ArrayList<String>();
        attachments_elements.add("Like");
        attachments_elements.add("Dislike");
        attachments_elements.add("I Love You");

        category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");
        numbers_elements.add("Ten");

        category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");


        category_elements.put(categories[4],pronouns_elements);


    }
    public HashMap <String, ArrayList <String>> getCategory_elements(){
        return category_elements;
    }

}
