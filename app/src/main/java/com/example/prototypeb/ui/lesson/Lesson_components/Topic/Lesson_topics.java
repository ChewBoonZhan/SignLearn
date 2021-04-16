package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.view.View;

import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.category.Category_components;

import java.io.Serializable;

public interface Lesson_topics extends Category_components, Serializable {
    //string constant of model category
    public String get_model_category();
    //number of required score
    public int get_required_score();
    //new instance of operation that will be performed
    public Intent_key intent_key = new Intent_key();
    //get constant string of screen component
    public String screen_component = intent_key.getScreen_component();
    //get constant string of translator label
    public String translator_label = intent_key.getTranslator_label();
    //get constant string of the translator's topic
    public String translator_lesson_topics = intent_key.getTranslator_lesson_topics();

}
