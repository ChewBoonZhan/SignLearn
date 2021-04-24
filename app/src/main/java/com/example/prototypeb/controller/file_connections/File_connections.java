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
    // to get the key to store data and get data from the file
    private File_connection_key file_connection_key;

    //app data to get the category of translator of the app
    private App_data app_data;

    //context to access the file
    private Context category_context;

    //sharedpref is user to store data in the file
    private SharedPreferences sharedPref;

    //to edit data inside the file
    private SharedPreferences.Editor editor;

    // integer to store default user's icon and user's default score value
    private int default_user_icon_value,default_score_value;

    //user's default name before signing up
    private String default_user_name;

    /**
     * Constructor for file connection
     * @param context - context to store and get data from file connection
     */
    public File_connections(Context context){

        //initialize default value of the app
        init_default_value();

        //create new instance of app_data to access category of sign language
        app_data = new App_data();

        //key to store and get data from file connection
        file_connection_key = new File_connection_key();
        category_context = context;

        //intiailize basic components required in the file for the app to work.
        init_file();
    }

    /**
     * Initialize default values of the app
     */
    private void init_default_value(){
        // by default, user has no icon
        default_user_icon_value = 0;

        //by default, a new user has score of 10
        default_score_value = 10;

        // by default, a new user has an empty name
        default_user_name = "";
    }

    /**
     * Unlock a category of sign language for user to learn and game
     * @param category - category to be unlocked by app
     */
    public void unlock_category(String category){
        editor = sharedPref.edit();
        //set that the category is to be unlocked
        editor.putBoolean(category,true);
        editor.apply();
    }

    /**
     * Sets that a syllabus is learnt by user, and have verified using translator
     * @param syllabus - syllabus learnt by user.
     */
    public void syllabus_learnt(String syllabus){
        editor = sharedPref.edit();
        //set that the syllabus is learnt
        editor.putBoolean(syllabus,true);
        editor.apply();
    }

    /**
     * Sets that a syllabus is not learnt by user, and have not verified using translator
     * @param syllabus - syllabus not learnt by user.
     */
    public void syllabus_not_learnt(String syllabus){
        editor = sharedPref.edit();
        //set that the syllabus is learnt
        editor.putBoolean(syllabus,false);
        editor.apply();
    }

    /**
     * Locks a category for sign language. Used only in testing on once the category is unlocked to lock the category
     * @param category - category to be locked by the app
     */
    public void lock_category(String category){
        editor = sharedPref.edit();
        // set that the category is to be locked
        editor.putBoolean(category,false);
        editor.apply();
    }


    /**
     * Initializes the file to be used, if the file is not initially initialized and is empty
     */
    private void init_file(){
        // get the shared pref of the file name
        sharedPref = category_context.getSharedPreferences(file_connection_key.getFilename(), Context.MODE_PRIVATE);

        //create and editor to edit the file
        editor = sharedPref.edit();

        //check if the file is already initialized or not.
        boolean init_aldy = sharedPref.getBoolean(file_connection_key.getComplete_initial_init(),false);
        if(!init_aldy){
            //data are not initialized yet
            save_default_category_unlock_in_file();
            //save_lesson_not_scored_in_file();
            save_other_data_in_file();

            //apply to commit save changes in the file
            editor.apply();
        }
    }

    /**
     * Set user's username in the file
     * @param name - String to save user's name in the file
     */
    public void set_user_name(String name){
        //get editor to edit the file
        editor = sharedPref.edit();

        //save user's name to the file
        editor.putString(file_connection_key.getUser_name(),name);

        //apply to commit name changes to the file
        editor.apply();
    }

    /**
     * Set user's icon to be saved in the file
     * @param user_icon - Integer that points to user's icon to be saved in the file
     */
    public void set_user_icon(int user_icon){
        // get editor to edit the file
        editor = sharedPref.edit();

        //save user's user's icon in the file
        editor.putInt(file_connection_key.getUser_icon(),user_icon);

        //apply to commit the user icon to the file
        editor.apply();
    }

    /**
     * Update the user's score to be saved in the file
     * @param score - updated score to be saved in the file
     */
    public void update_score(int score){
        // update score in file connection
        update_score_file_only(score);

        //update the score on the UI of the screen to display the correct score
        new Show_Score().update_points(this);

    }

    /**
     * Updates the score in the user's file connection
     * @param score - new score acquired by user.
     */
    public void update_score_file_only(int score){
        // get editor to edit the file
        editor = sharedPref.edit();

        //save user's updated score in the file
        editor.putInt(file_connection_key.getScore_key(),score);

        //apply to commit the user icon to the file
        editor.apply();
    }

    /**
     * This function is called when initializing the data to be stored in the app, especially
     * when the app is opened for the first time
     */
    private void save_other_data_in_file(){
        // Save the default score in the file
        editor.putInt(file_connection_key.getScore_key(),default_score_value);

        // save that initial data has been stored to the file
        editor.putBoolean(file_connection_key.getComplete_initial_init(),true);
    }

    /**
     * This function sets such that all the other categories are false, except the first one
     */
    public void save_default_category_unlock_in_file(){
        // get categories of sign language
        String[] categories = app_data.getCategories();

        //store that the first category is unlocked by default
        editor.putBoolean(categories[0],true);

        //store that the other categories are locked by default
        for(int i = 1;i<categories.length;i++){

            editor.putBoolean(categories[i],false);
        }

        //apply to commit the user icon to the file
        editor.apply();
    }

    /**
     * Check if the game category's syllabus is passed by user or not
     * @param game_category_key - syllabus to check if the user has passed this or not.
     * @return category_passed - if the game syllabus is passed by user or not.
     */
    public boolean check_game_category_passed(String game_category_key){
        // get game_passed key to check if the game syllabus has passed or not
        String game_passed_key = file_connection_key.getGame_passed_key();

        // the key to check if a game sylalbus is passed is concat of the game sylalbus and the key
        game_category_key = game_category_key + game_passed_key;

        // check if the game syllabus is passed or not
        return sharedPref.getBoolean(game_category_key,false);
    }

    /**
     * Set that the game category has been passed by the user
     * @param game_category_passed- Game category that has been passed by user
     */
    public void set_game_category_passed(String game_category_passed){
        // get the key to store the game category has been passed to the file
        String game_category_passed_key =file_connection_key.getGame_passed_key();
        game_category_passed = game_category_passed + game_category_passed_key;

        // get editor to edit data stored in file
        editor = sharedPref.edit();

        // store that the category has been passed by user by storing true
        editor.putBoolean(game_category_passed,true);

        // commit to apply the changes to the category.
        editor.apply();
    }

    /**
     * Set that the game category is not passed by user. Is used in testing when a
     * category is passed and needs to be set to not passed
     * @param game_category_not_passed - game category to be not set to passed
     */
    public void set_game_category_not_passed(String game_category_not_passed){
        // key to set the game sylalbus to be not pass
        String game_category_passed_key =file_connection_key.getGame_passed_key();

        // the key to check if a game sylalbus is passed is concat of the game sylalbus and the key
        game_category_not_passed = game_category_not_passed + game_category_passed_key;

        // get editor to edit value
        editor = sharedPref.edit();

        // put false to show that game sylalbus is not passed
        editor.putBoolean(game_category_not_passed,false);

        //apply to commit changes
        editor.apply();
    }

    /**
     * Check if a syllabus of lesson is learnt
     * @param lesson_key - lesson key to check if the syllabus
     * @return lesson_learnt - boolean to show if the syllabus is learnt or not
     */
    public boolean check_lesson_learnt(String lesson_key){
        //convert the lesson key to lowercase before processing
        lesson_key =lesson_key.toLowerCase();

        // get the key for lesson to be stored in file
        String lesson_passed_back_key = file_connection_key.getLesson_passed_back_key();


        lesson_key = lesson_key + lesson_passed_back_key;

        // get if the lesson has being learnt by the user or not
        return sharedPref.getBoolean(lesson_key,false);
    }

    /**
     * Check if the category is unlocked for user to learn or not
     * @param category - category to check if the category is unlocked for user to learn or not
     * @return category_unlocked - check if the category is unlocked or not.
     */
    public boolean is_category_unlocked(String category){
        return sharedPref.getBoolean(category,false);
    }

    /**
     * Get the score from the file connection to be returned
     * @return score - score acquired from the file connection
     */
    public int getScore(){
        return sharedPref.getInt(file_connection_key.getScore_key(),default_score_value);
    }

    /**
     * Get the user's username stored in the file connection
     * @return user_name - User's user name acquired from the file connection
     */
    public String get_user_name(){
        return sharedPref.getString(file_connection_key.getUser_name(),default_user_name);
    }

    /**
     * Get the user's user icon stored in the file connection
     * @return user_ucon - User's user icon stored in int in the file connection
     */
    public int get_user_icon(){
        return sharedPref.getInt(file_connection_key.getUser_icon(),default_user_icon_value);
    }

    /**
     * Resets the name to its default user name
     */
    public void reset_name(){

        // get editor to edit file
        editor = sharedPref.edit();

        // store the user's username as default name
        editor.putString(file_connection_key.getUser_name(),default_user_name);

        // apply to commit changes
        editor.apply();
    }

    /**
     * Resets the user's user icon to the default user icon
     */
    public void reset_icon(){
        // get editor to edit the file
        editor = sharedPref.edit();

        // store the default icon as user's icon in the file
        editor.putInt(file_connection_key.getUser_icon(),default_user_icon_value);

        //apply to commit changes
        editor.apply();
    }

    /**
     * Resets the user's score to the default score
     */
    public void reset_score(){
        editor = sharedPref.edit();

        //store the default score as updated score in file
        editor.putInt(file_connection_key.getScore_key(),default_score_value);
        editor.apply();
    }

    /**
     * getter for default user icon
     * @return default_user_icon - Default user icon whn the user has not stored any user icon in the file
     */
    public int getDefault_user_icon_value(){
        return default_user_icon_value;
    }

    /**
     * Getter for default score value
     * @return default_score_value - Default score value in the file connection when the user has not acquired or spent a single score
     */
    public int getDefault_score_value(){
        return default_score_value;
    }

    /**
     * Getter for default_user_name
     * @return default_user_name - Default user name in the file connection when the user has not set user name yet
     */
    public String getDefault_user_name(){
        return default_user_name;
    }

}
