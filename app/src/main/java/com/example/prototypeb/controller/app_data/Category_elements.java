package com.example.prototypeb.controller.app_data;

import android.util.Log;

import com.example.prototypeb.controller.category.Category_init;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Category_elements {
    public Category_elements(){
        init_category_elements();
        init_not_testable_elements();

    }
    private HashMap<String, ArrayList<String>> category_elements = new  HashMap <String, ArrayList <String>>();
    private ArrayList <String> not_testable_elements = new ArrayList<String>();

    private void init_not_testable_elements(){
        App_data app_data = new App_data();
        not_testable_elements.add(category_elements.get(app_data.getCategories()[0]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[0]).get(3));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[2]).get(3));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[3]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[3]).get(3));

        not_testable_elements.add(category_elements.get(app_data.getCategories()[4]).get(2));
        not_testable_elements.add(category_elements.get(app_data.getCategories()[4]).get(3));
    }
    private void init_category_elements(){
        App_data app_data = new App_data();
        String[] categories = app_data.getCategories();
        ArrayList <String> adverb_elements = new ArrayList<String>();
        adverb_elements.add("Yes");
        adverb_elements.add("No");
        adverb_elements.add("Almost");
        adverb_elements.add("Later");


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
        attachments_elements.add("Adore");

        category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        numbers_elements.add("One");
        numbers_elements.add("Two");
        numbers_elements.add("Three");
        numbers_elements.add("Four");
        numbers_elements.add("Ten");

        category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        pronouns_elements.add("Me");
        pronouns_elements.add("You");
        pronouns_elements.add("They");
        pronouns_elements.add("We");



        category_elements.put(categories[4],pronouns_elements);


    }
    public HashMap <String, ArrayList <String>> getCategory_elements(){
        return category_elements;
    }
    public boolean check_element_not_testable(String category_element){
        return (not_testable_elements.contains(category_element));

    }
    public String string_with_space(String string_with_no_space){
        if(string_with_no_space.equals("iloveyou")){
            return "I Love You";
        }
        else{
            return string_with_no_space;
        }
    }


}
