package com.example.prototypeb.controller.category;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;

import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.ui.game.Game_components.Game_components;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_init {
    private File_connections file_connections;
    private App_data app_data;
    private Category_classes category_classes;
    private Context context;
    public Category_init(Context context,Category_classes category_classes){
        this.context = context;
        file_connections = new File_connections(context);

        app_data = new App_data();
        this.category_classes =category_classes;
    }
    public void init_category_button_according_to_unlocked(ArrayList<Button> category_buttons, ArrayList<TextView> category_notifi){

        String[] categories = app_data.getCategories();

        for(int i = 0;i<category_buttons.size();i++){
            String category = categories[i];
            boolean unlocked = file_connections.is_category_unlocked(category);

            set_button_correctly(category_buttons.get(i),category_notifi.get(i),i,unlocked);

        }
    }
    public void init_category_button_according_to_unlocked(ArrayList<Button> category_buttons){

        String[] categories = app_data.getCategories();

        for(int i = 0;i<category_buttons.size();i++){
            String category = categories[i];
            boolean unlocked = file_connections.is_category_unlocked(category);

            set_button_correctly(category_buttons.get(i),i,unlocked);

        }
    }


    private void set_button_correctly(Button button, int index,boolean unlocked){
        HashMap<Integer, Category_components> category_classes_components = category_classes.get_classes();
        Category_components category_components =category_classes_components.get(index);
        if(unlocked){
            button.setOnClickListener(category_components.get_unlocked_On_click());
        }
        else{
            int color = ContextCompat.getColor(context, R.color.warning);

            button.setBackgroundColor(color);

            button.setOnClickListener(category_components.get_locked_On_click());
        }

    }
    private void set_button_correctly(Button button,TextView notifi, int index,boolean unlocked){
        HashMap<Integer, Category_components> category_classes_components = category_classes.get_classes();
        Category_components category_components =category_classes_components.get(index);
        if(unlocked){
            button.setOnClickListener(category_components.get_unlocked_On_click());

        }
        else{
            int color = ContextCompat.getColor(context, R.color.warning);
            notifi.setVisibility(View.GONE);
            button.setBackgroundColor(color);

            button.setOnClickListener(category_components.get_locked_On_click());
        }

    }

}
