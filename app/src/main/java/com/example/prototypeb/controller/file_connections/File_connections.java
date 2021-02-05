package com.example.prototypeb.controller.file_connections;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;

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
