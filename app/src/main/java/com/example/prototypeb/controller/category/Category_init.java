package com.example.prototypeb.controller.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.Game_components.Game_components;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_init {
    private File_connections file_connections;
    private App_data app_data;
    private Category_classes category_classes;
    public Category_init(Context context,Category_classes category_classes){
        file_connections = new File_connections(context);
        app_data = new App_data();
        this.category_classes =category_classes;
    }
    public void init_category_button_according_to_unlocked(ArrayList<Button> category_buttons){
        SharedPreferences sharedPreferences = file_connections.getSharedPref();
        String[] categories = app_data.getCategories();

        for(int i = 0;i<category_buttons.size();i++){
            String category = categories[i];
            boolean unlocked = sharedPreferences.getBoolean(category,false);
            set_button_correctly(category_buttons.get(i),i,unlocked);
            /*
            if(unlocked){

                Button category_is_unlocked = category_buttons.get(i);
                category_is_unlocked.setBackgroundColor(Color.parseColor(app_data.getButton_default_color()));
                add_onclick_to_button(category_is_unlocked,i);

            }
            else{
                //not unlocked
                Button category_not_unlocked = category_buttons.get(i);
                category_not_unlocked.setBackgroundColor(Color.parseColor(app_data.getButton_disabled_color()));

                category_not_unlocked.setOnClickListener(not_unlocked_onclick);



            }

             */

        }
    }
    private void set_button_correctly(Button button, int index,boolean unlocked){
        HashMap<Integer, Category_components> category_classes_components = category_classes.get_classes();
        Category_components category_components =category_classes_components.get(index);
        if(unlocked){
            button.setBackgroundColor(Color.parseColor(app_data.getButton_default_color()));
            button.setOnClickListener(category_components.get_unlocked_On_click());
        }
        else{
            button.setBackgroundColor(Color.parseColor(app_data.getButton_disabled_color()));
            button.setOnClickListener(category_components.get_locked_On_click());
        }

    }



}
