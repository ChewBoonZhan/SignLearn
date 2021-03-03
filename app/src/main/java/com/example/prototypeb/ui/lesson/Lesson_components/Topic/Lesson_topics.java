package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.view.View;

import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.category.Category_components;

import java.io.Serializable;

public interface Lesson_topics extends Category_components, Serializable {
    public String get_model_category();
    public int get_required_score();
    public Intent_key intent_key = new Intent_key();
    public String screen_component = intent_key.getScreen_component();
    public String translator_label = intent_key.getTranslator_label();
    public String translator_lesson_topics = intent_key.getTranslator_lesson_topics();

}
