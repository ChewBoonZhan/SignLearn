package com.example.prototypeb.controller.file_connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;

public class File_connections {

    private App_data app_data;
    private Context category_context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public File_connections(Context context){
        app_data = new App_data();

        category_context = context;
        init_file();
    }
    public void unlock_category(String category){
        editor = sharedPref.edit();
        editor.putBoolean(category,true);
        editor.apply();
    }


    private void init_file(){
        sharedPref = category_context.getSharedPreferences("simple_file", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        boolean init_aldy = sharedPref.getBoolean("init_aldy",false);
        if(!init_aldy){
            //data are not initialized yet
            save_category_in_file();
            //save_lesson_not_scored_in_file();
            save_other_data_in_file();
            editor.apply();

        }

    }
    public void save_lesson_not_scored_in_file(){
        String key_dic = "init_lesson_passed";
        if(sharedPref.getBoolean(key_dic,false)){
            Category_elements category_elements_object = new Category_elements();
            HashMap <String, ArrayList<String>> category_elements = category_elements_object.getCategory_elements();
            editor = sharedPref.edit();
            int length = category_elements.size();
            String[] categories = app_data.getCategories();
            for(int i = 0;i<length;i++){
                String category = categories[i];
                ArrayList <String> elements = category_elements.get(category);
                int elements_length = elements.size();
                for(int j = 0;j<elements_length;i++){
                    String editor_title_of_component = elements.get(j) + "_lesson_passed";
                    editor.putBoolean(editor_title_of_component,false);
                }
            }
            editor.putBoolean(key_dic,true);
            editor.apply();
        }



    }
    public void update_score(int score){
        editor = sharedPref.edit();
        editor.putInt("score",score);
        editor.apply();
    }
    private void save_other_data_in_file(){
        editor.putInt("score",50);
        editor.putBoolean("init_aldy",true);
    }
    private void save_category_in_file(){
        String[] categories = app_data.getCategories();
        editor.putBoolean(categories[0],true);
        for(int i = 1;i<categories.length;i++){
            /* for testing purposes
            if(i == 1){
                unlocked = true;
            }
            */

            editor.putBoolean(categories[i],false);
        }
    }
    public int getScore(){
        return sharedPref.getInt("score",-1);
    }
    public SharedPreferences getSharedPref(){
        return sharedPref;
    }

}
