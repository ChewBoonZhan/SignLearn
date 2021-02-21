package com.example.prototypeb.controller.translator;

import java.util.HashMap;
import com.example.prototypeb.controller.app_data.App_data;

public class Translator_categories {
    private  String[] items;
    private App_data app_data = new App_data();
    private HashMap <Integer,String> items_with_number;
    public Translator_categories(){
        items = app_data.getCategories();
        init_hashmap();
    }
    private void init_hashmap(){
        items_with_number = new HashMap <Integer,String>();
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
