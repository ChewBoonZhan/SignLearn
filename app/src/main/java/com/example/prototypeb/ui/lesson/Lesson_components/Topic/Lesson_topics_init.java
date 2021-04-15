package com.example.prototypeb.ui.lesson.Lesson_components.Topic;

import android.content.Context;

import com.example.prototypeb.controller.category.Category_classes;
import com.example.prototypeb.controller.category.Category_components;

import java.util.HashMap;

public class Lesson_topics_init implements Category_classes {
    //Map the integers to each lesson categories
    private HashMap<Integer, Category_components> lesson_topics;
    //Lesson's context
    private Context lesson_topics_init_context;

    /**
     * Initialize the Lesson's topic using the HashMap
     * @param context
     */
    public Lesson_topics_init(Context context){
        lesson_topics_init_context = context;
        init_lesson_classes();
    }

    /**
     * Mapping integers to each lesson category class
     */
    private void  init_lesson_classes(){
        lesson_topics = new HashMap<Integer,Category_components>();
        lesson_topics.put(0,new Adverbs(lesson_topics_init_context));
        lesson_topics.put(1,new Alphabets(lesson_topics_init_context));
        lesson_topics.put(2,new Attachments(lesson_topics_init_context));
        lesson_topics.put(3,new Numbers(lesson_topics_init_context));
        lesson_topics.put(4,new Pronouns(lesson_topics_init_context));
    }

    /**
     * get method for Hash
     * @return lesson_topics
     */
    public HashMap<Integer,Category_components> get_classes(){
        return lesson_topics;
    }
}
