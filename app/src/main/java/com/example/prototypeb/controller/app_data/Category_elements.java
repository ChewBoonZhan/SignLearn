package com.example.prototypeb.controller.app_data;

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
        adverb_elements.add("no");
        adverb_elements.add("yes");

        category_elements.put(categories[0],adverb_elements);

        ArrayList <String> alphabets_elements = new ArrayList<String>();
        adverb_elements.add("a");
        adverb_elements.add("b");
        adverb_elements.add("c");
        adverb_elements.add("y");

        category_elements.put(categories[1],alphabets_elements);

        ArrayList <String> attachments_elements = new ArrayList<String>();
        adverb_elements.add("like");
        adverb_elements.add("dislike");
        adverb_elements.add("iloveyou");

        category_elements.put(categories[2],attachments_elements);

        ArrayList <String> numbers_elements = new ArrayList<String>();
        adverb_elements.add("one");
        adverb_elements.add("two");
        adverb_elements.add("ten");

        category_elements.put(categories[3],numbers_elements);

        ArrayList <String> pronouns_elements = new ArrayList<String>();
        adverb_elements.add("me");
        adverb_elements.add("you");


        category_elements.put(categories[4],pronouns_elements);


    }
    public HashMap <String, ArrayList <String>> getCategory_elements(){
        return category_elements;
    }

}
