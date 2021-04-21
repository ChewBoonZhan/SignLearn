package com.example.prototypeb.controller.category;

import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics;

import java.util.HashMap;

public interface Category_classes {
    /**
     * Get method for Hash of integer mapping to category components
     * @return game_classes
     */
    public HashMap<Integer, Category_components> get_classes();
}
