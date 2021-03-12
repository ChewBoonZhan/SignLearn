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
    private File_connection_key file_connection_key;
    private App_data app_data;
    private Context category_context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private int default_user_icon_value,default_score_value;
    private String default_user_name;
    public File_connections(Context context){
        init_default_value();
        app_data = new App_data();
        file_connection_key = new File_connection_key();
        category_context = context;
        init_file();
    }
    private void init_default_value(){
        default_user_icon_value = 0;
        default_score_value = 50;
        default_user_name = "";
    }
    public void unlock_category(String category){
        editor = sharedPref.edit();
        editor.putBoolean(category,true);
        editor.apply();
    }


    private void init_file(){
        sharedPref = category_context.getSharedPreferences(file_connection_key.getFilename(), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        boolean init_aldy = sharedPref.getBoolean(file_connection_key.getComplete_initial_init(),false);
        if(!init_aldy){
            //data are not initialized yet
            save_category_in_file();
            //save_lesson_not_scored_in_file();
            save_other_data_in_file();
            editor.apply();

        }

    }
    public void save_lesson_not_scored_in_file(){
        String key_dic = file_connection_key.getComplete_init_lesson_passed();
        if(sharedPref.getBoolean(key_dic,false)){
            Category_elements category_elements_object = new Category_elements();
            HashMap <String, ArrayList<String>> category_elements = category_elements_object.getCategory_elements();
            editor = sharedPref.edit();
            int length = category_elements.size();
            String[] categories = app_data.getCategories();
            String lesson_passed_back_key = file_connection_key.getLesson_passed_back_key();
            for(int i = 0;i<length;i++){
                String category = categories[i];
                ArrayList <String> elements = category_elements.get(category);
                int elements_length = elements.size();
                for(int j = 0;j<elements_length;i++){
                    String editor_title_of_component = elements.get(j) + lesson_passed_back_key;
                    editor.putBoolean(editor_title_of_component,false);
                }
            }
            editor.putBoolean(key_dic,true);
            editor.apply();
        }



    }

    public void set_user_name(String name){
        editor = sharedPref.edit();
        editor.putString(file_connection_key.getUser_name(),name);
        editor.apply();
    }
    public void set_user_icon(int user_icon){
        editor = sharedPref.edit();
        editor.putInt(file_connection_key.getUser_icon(),user_icon);
        editor.apply();
    }
    public void update_score(int score){
        editor = sharedPref.edit();
        editor.putInt(file_connection_key.getScore_key(),score);
        editor.apply();
    }
    private void save_other_data_in_file(){
        editor.putInt(file_connection_key.getScore_key(),default_score_value);
        editor.putBoolean(file_connection_key.getComplete_initial_init(),true);
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
        return sharedPref.getInt(file_connection_key.getScore_key(),default_score_value);
    }
    public String get_user_name(){
        return sharedPref.getString(file_connection_key.getUser_name(),default_user_name);
    }
    public int get_user_icon(){
        return sharedPref.getInt(file_connection_key.getUser_icon(),default_user_icon_value);
    }
    public SharedPreferences getSharedPref(){
        return sharedPref;
    }
    public void reset_name(){
        editor = sharedPref.edit();
        editor.putString(file_connection_key.getUser_name(),default_user_name);
        editor.apply();
    }
    public void reset_icon(){
        editor = sharedPref.edit();
        editor.putInt(file_connection_key.getUser_icon(),default_user_icon_value);
        editor.apply();
    }
    public void reset_score(){
        editor = sharedPref.edit();
        editor.putInt(file_connection_key.getScore_key(),default_score_value);
        editor.apply();
    }
    public int getDefault_user_icon_value(){
        return default_user_icon_value;
    }
    public int getDefault_score_value(){
        return default_score_value;
    }
    public String getDefault_user_name(){
        return default_user_name;
    }

}
