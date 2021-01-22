package com.example.prototypeb.controller.file_connections;

import java.util.HashMap;
import com.example.prototypeb.controller.app_data.App_data;

public class Categories {
    private HashMap<String,Boolean> category_unlocked;
    private App_data app_data;
    public Categories(){
        app_data = new App_data();
        init_category_unlocked();
    }

    private void init_category_unlocked(){
        category_unlocked = new HashMap<String,Boolean> ();
        String[] categories = app_data.getCategories();
        boolean unlocked = true;
        for(int i = 0;i<categories.length;i++){
            String category = categories[i];
            category_unlocked.put(category,unlocked);

            if(i>=1){
                unlocked = false;
            }
        }
    }

    public HashMap<String, Boolean> getCategory_unlocked() {
        return category_unlocked;
    }
}
