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
import com.example.prototypeb.ui.Show_Score;

public class File_connections {
    private File_connection_key file_connection_key;
    private App_data app_data;
    private Context category_context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public File_connections(Context context){
        app_data = new App_data();
        file_connection_key = new File_connection_key();
        category_context = context;
        init_file();
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
        new Show_Score().update_points(this);
    }
    private void save_other_data_in_file(){
        editor.putInt(file_connection_key.getScore_key(),50);
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
    public boolean check_lesson_learnt(String lesson_key){
        String lesson_passed_back_key = file_connection_key.getLesson_passed_back_key();
        lesson_key = lesson_key + lesson_passed_back_key;
        return sharedPref.getBoolean(lesson_key,false);
    }
    public int getScore(){
        return sharedPref.getInt(file_connection_key.getScore_key(),-1);
    }
    public String get_user_name(){
        return sharedPref.getString(file_connection_key.getUser_name(),"");
    }
    public int get_user_icon(){
        return sharedPref.getInt(file_connection_key.getUser_icon(),0);
    }
    public SharedPreferences getSharedPref(){
        return sharedPref;
    }

}
