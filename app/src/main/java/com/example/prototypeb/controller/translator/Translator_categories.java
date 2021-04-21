package com.example.prototypeb.controller.translator;

import java.util.HashMap;
import com.example.prototypeb.controller.app_data.App_data;

public class Translator_categories {
    // array of items
    private  String[] items;

    // app_data to get app's categories
    private App_data app_data = new App_data();

    // set categories with numbers
    private HashMap <Integer,String> items_with_number;

    /**
     * Constructor for translator categories
     */
    public Translator_categories(){
        items = app_data.getCategories();

        // initialize hashmap of categories of sign language with respective numbers
        init_hashmap();
    }

    /**
     * Initialize hashmap of numbers to categories of app
     */
    private void init_hashmap(){
        items_with_number = new HashMap <Integer,String>();

        // categories are saved into hashmap according to numbers
        for(int i = 0;i<items.length;i++){
            items_with_number.put(i,items[i].toLowerCase());
        }
    }

    public String[] getItems() {
        return items;
    }

    public HashMap<Integer, String> getItems_with_number() {
        return items_with_number;
    }
    public String get_Beginning_Category(){
        return items_with_number.get(0);
    }
}
