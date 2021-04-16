package com.example.prototypeb.controller.app_data;

public class Intent_key {

    private String screen_component = "screen_components";

    /**
     * Getter for intent keys for screen components
     * @return screen_component_intent - String to get and pass screen components around as an intent
     */
    public String getScreen_component(){
        return screen_component;
    }

    private String translator_label = "correct_translator_label";

    /**
     * Getter for intent keys for translator labels
     * @return translator_label - String to get and pass translator_label between component as intent
     */
    public String getTranslator_label(){
        return translator_label;
    }

    private String translator_lesson_topics = "translator_lesson_topics";

    /**
     * Getter for intent keys for translator lesson_topics
     * @return translator_lesson_topics - String to get and pass translator_lesson_topics between components as intent
     */
    public String getTranslator_lesson_topics(){
        return translator_lesson_topics;
    }

    private String translator_screen_title_id = "translator_screen_title_id";

    /**
     * getter for translator screen title id
     * @return translator_screen_title_id - String to get and pass translator_screen_title_id between components as intent
     */
    public String getTranslator_screen_title_id(){
        return translator_screen_title_id;
    }
}
