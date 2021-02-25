package com.example.prototypeb.controller.translator_verify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.camera.Camera_handle;
import com.example.prototypeb.controller.file_connections.File_connection_key;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.toast.Success_toast;
import com.example.prototypeb.controller.translator.Translator;
import com.example.prototypeb.ui.lesson.LessonFragment;
import com.example.prototypeb.ui.lesson.Lesson_components.Topic.Lesson_topics;
import com.example.prototypeb.ui.translator.TranslatorFragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class Translator_verify extends AppCompatActivity {
    private FrameLayout camera_frame;
    private Button switch_camera;
    private TextView title_text;
    private ProgressBar verify_progress;
    private Context translator_verify_context;
    private Activity translator_verify_activity;
    private Camera_handle camera_handle;
    private Translator translator;
    private String translator_category;
    private String correct_string;
    private Intent_key intent_key = new Intent_key();
    private int title_text_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_verify);
        get_serializable_contents();
        get_component_from_screen();
        set_title();
        set_switch_camera_onclick();
        set_progress_bar_width();
        get_context_and_activity();
        setup_translator();

    }
    private void set_title(){
        title_text.setText(title_text_id);
    }
    private void set_progress_bar_width(){



        verify_progress.setScaleY(3f);

    }
    private void set_switch_camera_onclick(){
        switch_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_handle.switch_camera_met();
            }
        });
    }
    private void get_serializable_contents(){
        title_text_id = (int)getIntent().getIntExtra(intent_key.getTranslator_screen_title_id(),0);
        correct_string = (String) getIntent().getStringExtra(intent_key.getTranslator_label());
        translator_category = (String) getIntent().getStringExtra(intent_key.getTranslator_lesson_topics());
    }
    private void get_component_from_screen(){
        title_text = findViewById(R.id.text_desc_lesson_verify);
        camera_frame = findViewById(R.id.camera_frame_verify_lesson);
        switch_camera = findViewById(R.id.switch_camera_verify_lesson);
        verify_progress = findViewById(R.id.verify_progress_lesson_verify);

    }
    private void get_context_and_activity(){
        translator_verify_context = TranslatorFragment.getContext_here();
        translator_verify_activity = TranslatorFragment.getActivity_here();
    }
    private void setup_translator(){
        camera_handle = new Camera_handle(translator_verify_context,translator_verify_activity,camera_frame,this);
        translator = camera_handle.getTranslator();
        translator.load_model_tflite(translator_category);
        camera_handle.start_camera_met();
    }
    public void text_changed(String text){
        text =text.toLowerCase();
        if(text.equals(correct_string.toLowerCase())){
            increment_progress_bar();
            //increment
        }
        else{
            reset_progress_bar();
            //reset progress bar.
        }
    }
    private void handle_score_increment(){
        File_connections file_connections = new File_connections(translator_verify_context);
        File_connection_key file_connection_key = new File_connection_key();
        file_connections.save_lesson_not_scored_in_file();
        String element_to_check = correct_string + file_connection_key.getLesson_passed_back_key();
        boolean lesson_passed = file_connections.getSharedPref().getBoolean(element_to_check,false);
        if(!lesson_passed) {
            file_connections.update_score(file_connections.getScore() + 10);

            file_connections.unlock_category(element_to_check);
            new Success_toast(translator_verify_context, "Sign Language: \"" + correct_string.toUpperCase() + "\" was learnt!\n 10 Points are awarded").show_toast();

        }

        //go back to the screen
        handle_leave_screen();

    }
    private void press_back(){
        //equal to pressing the back button

        if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();

            return;
        }
        super.onBackPressed();


    }
    private void handle_leave_screen(){
        press_back();
    }
    private void increment_progress_bar(){
        if(verify_progress.getProgress() >= verify_progress.getMax()){
            //done~
            handle_score_increment();

        }
        else{
            verify_progress.incrementProgressBy(1);
        }


    }
    private void reset_progress_bar(){
        verify_progress.setProgress(0);
    }
    public String getCorrect_string(){
        return correct_string;
    }
}
