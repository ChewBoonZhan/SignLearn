package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.category.Category_classes;
import com.example.prototypeb.controller.category.Category_components;


public class Game_classes_init implements Category_classes {
    //Map integers onto game categories
    private HashMap <Integer, Category_components> game_classes;
    //Game context
    private Context game_class_init_context;

    /**
     * Initialize game category classes using HashMap
     * @param context
     */
    public Game_classes_init(Context context){
        game_class_init_context = context;

        init_game_classes();
    }

    /**
     * Mapping integers to each game category class
     */
    private void init_game_classes(){
        game_classes = new HashMap<Integer,Category_components>();
        game_classes.put(0,new Game_adverbs(game_class_init_context));
        game_classes.put(1,new Game_alphabets(game_class_init_context));
        game_classes.put(2,new Game_attachments(game_class_init_context));
        game_classes.put(3,new Game_numbers(game_class_init_context));
        game_classes.put(4,new Game_pronoun(game_class_init_context));


    }

    /**
     * Get method for Hash of integer mapping to category components
     * @return game_classes
     */
    public HashMap<Integer,Category_components> get_classes(){
        return game_classes;
    }
}
