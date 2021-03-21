package com.example.prototypeb.controller.lesson_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.translator_verify.Translator_verify;

import pl.droidsonroids.gif.GifImageView;

public class Lesson_screen extends Sub_action_bar {
    private GifImageView gifImageView;
    private TextView lesson_text_title;
    private TextView lesson_info_defcontent;
    private Button check_translator_info_lesson;
    private int title_text_id;
    private Intent_key intent_key = new Intent_key();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_lesson);

        get_components_screen();
        set_components_value();

        get_screen_elements();
        set_back_button_onclick();
        set_title_text(title_text_id);
    }
    private void set_components_value(){
        Lesson_screen_components lesson_screen_components = (Lesson_screen_components) getIntent().getSerializableExtra(intent_key.getScreen_component());
        title_text_id = lesson_screen_components.get_title_id();
        lesson_info_defcontent.setText(lesson_screen_components.get_content_id());
        lesson_text_title.setText(title_text_id);
        gifImageView.setImageResource(lesson_screen_components.get_gif_id());
        set_translator_button_on_click();

    }
    private void press_back(){
        //equal to pressing the back button
        super.onBackPressed();
    }
    private void set_translator_button_on_click(){
        String correct_translator_label = getIntent().getStringExtra(intent_key.getTranslator_label());
        String translator_lesson_topics = getIntent().getStringExtra(intent_key.getTranslator_lesson_topics());
        View.OnClickListener check_on_click = null;
        if((correct_translator_label.equals("")) && (translator_lesson_topics.equals(""))){


           Lesson_screen lesson_screen = this;
            check_on_click = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener positive_choice = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    };
                    One_choice_message one_choice_message = new One_choice_message(lesson_screen,"Content Unavailable.","The verification feature for this category will be released in the upcoming update! We apologize for any difficulties or misunderstanding caused.","Okay.",positive_choice);
                    one_choice_message.show_message();
                }
            };
        }
        else{
            check_on_click = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Translator_verify.class)
                            .putExtra(intent_key.getTranslator_screen_title_id(),title_text_id)
                            .putExtra(intent_key.getTranslator_label(),correct_translator_label)
                            .putExtra(intent_key.getTranslator_lesson_topics(),translator_lesson_topics)

                    );
                }
            };
        }
        check_translator_info_lesson.setOnClickListener(check_on_click);
    }
    private void get_components_screen(){
        gifImageView = findViewById(R.id.lesson_gif_imageView);
        lesson_text_title = findViewById(R.id.lesson_text_title);
        lesson_info_defcontent = findViewById(R.id.lesson_info_defcontent);
        check_translator_info_lesson = findViewById(R.id.check_translator_info_lesson);
    }




}
