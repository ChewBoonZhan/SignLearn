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
import com.example.prototypeb.ui.game.Game_components.Game_screen;

import java.util.ArrayList;
import java.util.HashMap;

public class Category_init {
    // file connection to access file contents
    private File_connections file_connections;

    // app_data to get categories in app
    private App_data app_data;


    private Category_classes category_classes;

    // get context for file connection
    private Context context;

    /**
     * Constructor
     * @param context
     * @param category_classes
     */
    public Category_init(Context context,Category_classes category_classes){
        this.context = context;
        file_connections = new File_connections(context);

        app_data = new App_data();
        this.category_classes =category_classes;
    }

    /**
     * Initialize arraylist of button according if they are unlocked in file connection
     * @param category_buttons - arraylist of button to be initialized
     * @param category_notifi - arraylist of notification for each button
     * @param game - wether the category button to be initialized is game or not
     */
    public void init_category_button_according_to_unlocked(ArrayList<Button> category_buttons, ArrayList<TextView> category_notifi,boolean game){
        // get categories in the app
        String[] categories = app_data.getCategories();

        for(int i = 0;i<category_buttons.size();i++){
            String category = categories[i];

            // check if the category is unlocked
            boolean unlocked = file_connections.is_category_unlocked(category);

            // set the button correctly according if the category is unlocked or not
            set_button_correctly(category_buttons.get(i),category_notifi.get(i),i,unlocked,game);
        }
    }

    /**
     * initialize button correctly according to if the category is unlocked or not
     * @param button - button to be set
     * @param notifi - text notification beside button
     * @param index - index of the category
     * @param unlocked - if the category is unlocked or not in file connection
     * @param game - if the button belongs in game
     */
    private void set_button_correctly(Button button,TextView notifi, int index,boolean unlocked,boolean game){

        //category classes according to integer
        HashMap<Integer, Category_components> category_classes_components = category_classes.get_classes();
        Category_components category_components =category_classes_components.get(index);

        // if the category is unlocked
        if(unlocked){

            // set the button's on click according to category components that are unlocked's onclick
            button.setOnClickListener(category_components.get_unlocked_On_click());
            if(game){
                if(((Game_components)category_components).check_all_categories_passed()){

                    //all syllabus of category have passed
                    // dont need to see notification for this game anymore.
                    notifi.setVisibility(View.GONE);
                }
            }
        }
        else{
            // category is locked

            // set the colour of the category button as a warning colour
            int color = ContextCompat.getColor(context, R.color.warning);

            // set the notification text to be not showned to user
            notifi.setVisibility(View.GONE);

            // set the background color as set
            button.setBackgroundColor(color);

            // set button onclick as locked onclick
            button.setOnClickListener(category_components.get_locked_On_click());
        }

    }

}
