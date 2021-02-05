package com.example.prototypeb.controller.lesson_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.prototypeb.R;

import pl.droidsonroids.gif.GifImageView;

public class Lesson_screen extends AppCompatActivity {
    private GifImageView gifImageView;
    private TextView lesson_text_title;
    private TextView lesson_info_defcontent;
    private Button check_translator_info_lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_lesson);

        get_components_screen();
        set_components_value();
    }
    private void set_components_value(){
        Lesson_screen_components lesson_screen_components = (Lesson_screen_components) getIntent().getSerializableExtra("screen_components");
        lesson_info_defcontent.setText(lesson_screen_components.get_content_id());
        lesson_text_title.setText(lesson_screen_components.get_title_id());
        gifImageView.setImageResource(lesson_screen_components.get_gif_id());
    }
    private void get_components_screen(){
        gifImageView = findViewById(R.id.lesson_gif_imageView);
        lesson_text_title = findViewById(R.id.lesson_text_title);
        lesson_info_defcontent = findViewById(R.id.lesson_info_defcontent);
    }
    private int getImageid(){
        return R.drawable.alpha_b;
    }
    private int gettextID(){
        return R.string.b_title;
    }



}
