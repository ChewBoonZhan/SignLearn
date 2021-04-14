package com.example.prototypeb.controller.translator_verify;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.Toast;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Intent_key;
import com.example.prototypeb.controller.camera.Camera_handle;
import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.file_connections.File_connection_key;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.new_screen.New_screen;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
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

public class Translator_verify extends Sub_action_bar {
    private final int SCORE_INCREMENT = 7;
    private FrameLayout camera_frame;
    private Button switch_camera;
    private TextView title_text,camera_type;
    private ProgressBar verify_progress;
    private Context translator_verify_context;
    private Activity translator_verify_activity;
    private Camera_handle camera_handle;
    private Translator translator;
    private String translator_category;
    private String correct_string;
    private Intent_key intent_key = new Intent_key();
    private Button lesson_camera_permission;
    private int title_text_id;
    private boolean complete_verifying = false;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_verify);
        get_serializable_contents();

        get_screen_elements();
        set_back_button_onclick();
        set_title_text(title_text_id);

        get_component_from_screen();

        set_switch_camera_onclick();
        set_camera_permission_onclick();
        set_progress_bar_width();
        get_context_and_activity();
        setup_translator();



    }

    private void set_camera_permission_onclick(){
        lesson_camera_permission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                request_permission_thread();
            }
        });
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
        setCorrect_string(getIntent().getStringExtra(intent_key.getTranslator_label()));
        translator_category = (String) getIntent().getStringExtra(intent_key.getTranslator_lesson_topics());
    }
    private void get_component_from_screen(){
        title_text = findViewById(R.id.text_desc_lesson_verify);
        camera_type =findViewById(R.id.camera_type_verify);
        camera_frame = findViewById(R.id.camera_frame_verify_lesson);
        switch_camera = findViewById(R.id.switch_camera_verify_lesson);
        verify_progress = findViewById(R.id.verify_progress_lesson_verify);
        lesson_camera_permission = findViewById(R.id.lesson_camera_permission);
    }
    private void get_context_and_activity(){
        translator_verify_context = TranslatorFragment.getContext_here();
        translator_verify_activity = TranslatorFragment.getActivity_here();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setup_translator(){
        camera_handle = new Camera_handle(translator_verify_context,translator_verify_activity,camera_frame,this,camera_type);
        translator = camera_handle.getTranslator();
        translator.load_model_tflite(translator_category);
        start_request_permission();

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

        String element_to_check = correct_string.toLowerCase() + file_connection_key.getLesson_passed_back_key();
        boolean lesson_passed = file_connections.check_lesson_learnt(correct_string);

        if(!lesson_passed) {
            file_connections.update_score(file_connections.getScore() + SCORE_INCREMENT);

            file_connections.unlock_category(element_to_check);
            new Success_toast(translator_verify_context, "Sign: \"" + correct_string.toUpperCase() + "\" was learnt!\n + 10 Points").show_toast();

        }
        else{
            new Success_toast(translator_verify_context, "Sign: \"" + correct_string.toUpperCase() + "\" was correct!").show_toast();
        }

        //go back to the screen
        handle_leave_screen();

    }
    private void press_back(){
        //equal to pressing the back button
        super.onBackPressed();


    }
    private void handle_leave_screen(){
        //New_screen new_screen = new New_screen(1000, translator_verify_context,translator_verify_activity);
        //otenew_screen.go_to_new_screen("com.example.prototypeb.ui.home.HomeFragment",false);
        press_back();
    }
    private void increment_progress_bar(){
        if(!complete_verifying){
            if(verify_progress.getProgress() >= verify_progress.getMax()){
                //done~
                handle_score_increment();
                complete_verifying = true;

            }
            else{
                verify_progress.incrementProgressBy(1);
            }
        }



    }
    private void reset_progress_bar(){
        verify_progress.setProgress(0);
    }



    //permissions, as dealing with fragments is too complicated.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {

        if (ContextCompat.checkSelfPermission(translator_verify_context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permission_not_granted(false);
            request_permission_thread();
        } else {
            permission_granted(true);

        }
    }
   


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission_thread() {


        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission_granted(false);
            } else {
                permission_not_granted(true);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_granted(boolean permission_initially_granted) {
        if(!permission_initially_granted){
            Toast.makeText(translator_verify_context, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            //loading_screen.start_timer();


        }
        camera_handle.start_camera_met();
        enable_or_disable_button(lesson_camera_permission,false);
        show_translator_elements(true);
        //TranslatorFragment.all_important_buttons_status(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){
            Toast.makeText(translator_verify_context, "Camera Permission not Granted", Toast.LENGTH_SHORT).show();
        }


        enable_or_disable_button(lesson_camera_permission,true);
        show_translator_elements(false);
        //TranslatorFragment.all_important_buttons_status(false);

    }
    private void show_translator_elements(boolean show){
        if(show){
            enable_or_disable_button(switch_camera,true);
            camera_frame.setVisibility(View.VISIBLE);
            title_text.setVisibility(View.VISIBLE);
            verify_progress.setVisibility(View.VISIBLE);


        }
        else{
            enable_or_disable_button(switch_camera,false);
            camera_frame.setVisibility(View.GONE);
            title_text.setVisibility(View.GONE);
            verify_progress.setVisibility(View.GONE);

        }
    }
    private void enable_or_disable_button(Button button,boolean enable){
        if(enable){
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        else{
            button.setVisibility(View.GONE);
            button.setEnabled(false);
        }
    }
    public void setCorrect_string(String correct_string){
        this.correct_string = correct_string;

    }
    public String getCorrect_string(){
        return this.correct_string;
    }


}
