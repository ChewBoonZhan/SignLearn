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
    // Score increment when the user verifies sign language to the translator correctly
    private final int SCORE_INCREMENT = 7;

    // camera_frame to show the camera content to user
    private FrameLayout camera_frame;

    // button to allow user to switch camera
    private Button switch_camera;

    // textview to show user hint text, and type of camera on screen
    private TextView title_text,camera_type;

    // progress bar of user verifying sign language
    private ProgressBar verify_progress;

    // context for verifying sign language
    private Context translator_verify_context;

    // activity for verifying sign language
    private Activity translator_verify_activity;

    // handles the camera connection
    private Camera_handle camera_handle;

    // handles the translator of sign language
    private Translator translator;

    // string to show the translator category
    private String translator_category;

    // correct string when sign language is checked by user
    private String correct_string;

    // intent key to get intent date
    private Intent_key intent_key = new Intent_key();

    // button to get user's camera permission if it is not enabled
    private Button lesson_camera_permission;

    // id of text to be showned as title text
    private int title_text_id;

    // boolean to check if the verification of sign language has been completed
    private boolean complete_verifying = false;

    /**
     * Function called when elements on the screen are created
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the content view to be lesson_verify for user to verify sign language
        setContentView(R.layout.lesson_verify);

        // get intent contents to interact with it
        get_serializable_contents();

        // get the elements on the screen to interact with them for the sub_action_bar
        get_screen_elements();

        // set back button on screen's on click
        set_back_button_onclick();

        // set the title text of the screen
        set_title_text(title_text_id);

        // get the current elements shown on the screen
        get_component_from_screen();

        // set switch camera on click
        set_switch_camera_onclick();

        // set button that enable camera permission on click
        set_camera_permission_onclick();

        // set progress bar width to be more visible
        set_progress_bar_width();

        // get context and activity for translator
        get_context_and_activity();

        // setup translator to be user to verify user's hand signs
        setup_translator();



    }

    /**
     * Set camera permission's on click to get user to allow camera permission
     */
    private void set_camera_permission_onclick(){
        lesson_camera_permission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // call request permission thread
                request_permission_thread();
            }
        });
    }

    /**
     * Set the width of the progress bar to make it more visible to user
     */
    private void set_progress_bar_width(){
        verify_progress.setScaleY(3f);
    }

    /**
     * Set switch camera button on click listener and event
     */
    private void set_switch_camera_onclick(){
        switch_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_handle.switch_camera_met();
            }
        });
    }

    /**
     * Get intent contents
     */
    private void get_serializable_contents(){
        // get title text id
        title_text_id = (int)getIntent().getIntExtra(intent_key.getTranslator_screen_title_id(),0);

        // set correct string of the translator verify based on content of intent
        setCorrect_string(getIntent().getStringExtra(intent_key.getTranslator_label()));

        //set translator category
        translator_category = (String) getIntent().getStringExtra(intent_key.getTranslator_lesson_topics());
    }

    /**
     * Get components from the screen such as the title text, as well as button to switch camera
     * as well as to progress bar
     */
    private void get_component_from_screen(){
        title_text = findViewById(R.id.text_desc_lesson_verify);
        camera_type =findViewById(R.id.camera_type_verify);
        camera_frame = findViewById(R.id.camera_frame_verify_lesson);
        switch_camera = findViewById(R.id.switch_camera_verify_lesson);
        verify_progress = findViewById(R.id.verify_progress_lesson_verify);
        lesson_camera_permission = findViewById(R.id.lesson_camera_permission);
    }

    /**
     * Get context and activity to be used to process data
     */
    private void get_context_and_activity(){
        translator_verify_context = TranslatorFragment.getContext_here();
        translator_verify_activity = TranslatorFragment.getActivity_here();
    }

    /**
     * Setups the translator for use
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setup_translator(){

        //initialize camera handle
        camera_handle = new Camera_handle(translator_verify_context,translator_verify_activity,camera_frame,this,camera_type);

        // get translator from camera_handle
        translator = camera_handle.getTranslator();

        // load model into translator based on translator category
        translator.load_model_tflite(translator_category);

        // start checking to get camera permission
        start_request_permission();

    }

    /**
     * Is called each time the output is given from the translator
     * This updates the progress bar if the string output from translator matches correct string
     * Else it resets the progress bar
     * @param text - Output from the translator as english text
     */
    public void text_changed(String text){
        text =text.toLowerCase();
        if(text.equals(correct_string.toLowerCase())){
            // if the text is same as output from translator
            increment_progress_bar();
            //increment progress bar
        }
        else{
            //reset the progress bar to 0
            reset_progress_bar();

        }
    }

    /**
     * Increments the score if user verify sign language correctly
     */
    private void handle_score_increment(){
        // create new file connection to handle file connections
        File_connections file_connections = new File_connections(translator_verify_context);

        //create new file connection key to save and get data from file connection
        File_connection_key file_connection_key = new File_connection_key();

        // check if the user has learnt the lesson and correctly verified the sign language or not
        String element_to_check = correct_string.toLowerCase() + file_connection_key.getLesson_passed_back_key();
        boolean lesson_passed = file_connections.check_lesson_learnt(correct_string);

        // if the lesson has not been learnt by user before
        if(!lesson_passed) {
            // increment the score to the new score and save it in file connection
            file_connections.update_score(file_connections.getScore() + SCORE_INCREMENT);

            // set such that the syllabus of category is learnt
            file_connections.syllabus_learnt(element_to_check);

            // show toast that the sign language is learnt correctly
            new Success_toast(translator_verify_context, "Sign: \"" + correct_string.toUpperCase() + "\" was learnt!\n + 10 Points").show_toast();

        }
        else{
            // category is already learnt by user

            // show toast that the sign language was correct, but no score is gained
            new Success_toast(translator_verify_context, "Sign: \"" + correct_string.toUpperCase() + "\" was correct!").show_toast();
        }

        //go back to the previous screen
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

    /**
     * Increments the progres bar, until full
     */
    private void increment_progress_bar(){
        if(!complete_verifying){
            // if the progress of progress bar is more or equal to the max of the progress bar
            if(verify_progress.getProgress() >= verify_progress.getMax()){
                // sign language is correct

                // check if score needs to be incremented
                handle_score_increment();

                // verify complete of sign language
                complete_verifying = true;

            }
            else{
                // increment progress by 1
                verify_progress.incrementProgressBy(1);
            }
        }



    }

    /**
     * Reset the progress bar to have progress of 0
     */
    private void reset_progress_bar(){
        verify_progress.setProgress(0);
    }


    /**
     * Start request for permission from user
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {

        if (ContextCompat.checkSelfPermission(translator_verify_context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            // permission not granted to camera initially
            permission_not_granted(false);

            //request for permission from user using thread
            request_permission_thread();
        } else {

            // permission granted to camera
            permission_granted(true);

        }
    }


    /**
     * Request for permission from user using UI thread
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission_thread() {


        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }


    /**
     * This function is called once permission message has been closed, wether user has accept or deny permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // user grant permission, but permission is just being granted to user
                permission_granted(false);
            } else {
                // permission is not granted, and usr just clicked deny permission
                permission_not_granted(true);
            }
        }
    }

    /**
     * Permission is granted for camera
     * @param permission_initially_granted
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_granted(boolean permission_initially_granted) {
        if(!permission_initially_granted){
            // permission is not initially granted, user just allowed camera permission

            // show toast to show that camera permission is granted to app
            Toast.makeText(translator_verify_context, "Camera Permission Granted", Toast.LENGTH_SHORT).show();

        }

        // start camera method to take in input to process sign language
        camera_handle.start_camera_met();

        // disable camera permission button as camera permission is already granted
        enable_or_disable_button(lesson_camera_permission,false);

        //show translator elements for user to verify sign language
        show_translator_elements(true);

    }

    /**
     * Check if permission is not granted to camera
     * @param user_clicked_no - if user clicked no to not allow camera permission to be granted
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){

            // user clicked on deny permission

            // show toast that user dont grant app camera permission
            Toast.makeText(translator_verify_context, "Camera Permission not Granted", Toast.LENGTH_SHORT).show();
        }

        //enable camera permission button to allow user to allow app to get camera permission
        enable_or_disable_button(lesson_camera_permission,true);

        // hide translator elements as permission not granted to camera
        show_translator_elements(false);


    }

    /**
     * Show or hide translator elements on screen
     * @param show - boolean wether to show or hide translator elements on screen
     */
    private void show_translator_elements(boolean show){

        if(show){
            // show translator element on screen

            // enable switch_camera button
            enable_or_disable_button(switch_camera,true);

            // set visibility of camera frame to visible
            camera_frame.setVisibility(View.VISIBLE);

            // set title text of camera frame or hint text to be visible
            title_text.setVisibility(View.VISIBLE);

            // set progress bar to be visible
            verify_progress.setVisibility(View.VISIBLE);


        }
        else{
            // disable switch camera button
            enable_or_disable_button(switch_camera,false);

            // set camera frame to be unable to be seen
            camera_frame.setVisibility(View.GONE);

            // set title hint text to be unable to be seen
            title_text.setVisibility(View.GONE);

            // set progress bar to be unable to be seen
            verify_progress.setVisibility(View.GONE);

        }
    }

    /**
     * Enable or disable button
     * @param button - button to be enabled or disabled
     * @param enable - boolean to enable or disable button
     */
    private void enable_or_disable_button(Button button,boolean enable){
        if(enable){
            //Enable button

            //set button to be visible
            button.setVisibility(View.VISIBLE);

            // enable button for user interaction
            button.setEnabled(true);
        }
        else{
            // disable button

            // set button to be gone
            button.setVisibility(View.GONE);

            // disable button for user interaction
            button.setEnabled(false);
        }
    }

    /**
     * Sets the correct string for translator to increment progress
     * @param correct_string - correct string for class to increment progress
     */
    public void setCorrect_string(String correct_string){
        this.correct_string = correct_string;

    }



}
