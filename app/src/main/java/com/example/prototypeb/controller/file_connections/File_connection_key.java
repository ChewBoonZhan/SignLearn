package com.example.prototypeb.controller.file_connections;

public class File_connection_key {
    //file name to store data of the app
    private String filename = "simple_file";

    /**
     * getter for filename
     * @return filename - name of the file to store the app's data
     */
    public String getFilename(){
        return filename;
    }

    // key to wether the file has been initially initialized or not.
    private String complete_initial_init = "init_aldy";

    /**
     * Getter for complete_initial_init
     * @return complete_initial_init - key for content of file wether the content of the file has been initialized or not.
     */
    public String getComplete_initial_init(){
        return complete_initial_init;
    }

    //key for cateogory of game passed
    private String game_passed_key = "_game_passed";

    /**
     * Getter for key of category of game passed
     * @return game_passed_key - Key for content of file wether the game category is passed
     */
    public String getGame_passed_key(){
        return game_passed_key;
    }

    // key for category wether the lesson is passed or not
    private String lesson_passed_back_key = "_lesson_passed";

    /**
     * Getter for key of category of lesson passed
     * @return lesson_passed_back_key - Key for content of file wether the lesson category is passed
     */
    public String getLesson_passed_back_key(){
        return lesson_passed_back_key;
    }

    // key to store user's score in the file and getting the score from the file
    private String score_key = "score";
    /**
     * Getter for key to store and get user's score from the file
     * @return score_key - key to store and get user's score.
     */
    public String getScore_key(){
        return score_key;
    }

    // key to store and get user's username
    private String user_name = "user_name";

    /**
     * Getter for key to store and get user's username
     * @return user_name - key to store and get user's username
     */
    public String getUser_name(){
        return user_name;
    }

    // key to store and get user's icon
    private String user_icon = "user_icon";

    /**
     * Getter for key to store and get user's icon
     * @return user_icon - key to store and get user's icon
     */
    public String getUser_icon(){
        return user_icon;
    }
}
