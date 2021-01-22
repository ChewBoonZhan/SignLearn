package com.example.prototypeb.controller.file_connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;

public class Categories {
    private HashMap<String,Boolean> category_unlocked;
    private App_data app_data;
    private Context category_context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public Categories(Context context){
        app_data = new App_data();
        init_category_unlocked();
        category_context = context;
        init_file();
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

    private void init_file(){
        sharedPref = category_context.getSharedPreferences("simple_file", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        boolean init_aldy = sharedPref.getBoolean("init_aldy",false);
        if(!init_aldy){
            //data are not initialized yet
            save_category_in_file();
            save_other_data_in_file();
            editor.apply();

        }

    }
    private void save_other_data_in_file(){
        editor.putInt("score",0);
        editor.putBoolean("init_aldy",true);
    }
    private void save_category_in_file(){
        String[] categories = app_data.getCategories();
        boolean unlocked = true;
        for(int i = 0;i<categories.length;i++){

            editor.putBoolean(categories[i],unlocked);
        }
    }
    public SharedPreferences getSharedPref(){
        return sharedPref;
    }
    public HashMap<String, Boolean> getCategory_unlocked() {
        return category_unlocked;
    }
}
