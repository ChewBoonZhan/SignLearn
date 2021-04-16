package com.example.prototypeb.ui.translator;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;

import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.prototypeb.MainActivity;
import com.example.prototypeb.R;



import com.example.prototypeb.controller.camera.Camera_handle;


import com.example.prototypeb.controller.choice_message.Two_choice_message;
import com.example.prototypeb.controller.translator.Translator;

import com.example.prototypeb.controller.translator.Translator_categories;

import java.util.HashMap;
import java.util.function.Consumer;

public class TranslatorFragment extends Fragment {


    // activity and context of translator fragment
    private static Activity activity_here;
    private static Context context_here;

    //layout components
    public FrameLayout framelayout;

    //button to change camera
    private Button change_camera;

    //spinner to allow user to change category
    private Spinner change_category;

    //button to get user's camera permission
    private Button camera_permission;

    // text to show classified text and camera type on the screen to users
    private TextView classitext,camera_type;

    // string to store the classified cateogory of the input image
    private String classify_category;

    //view that contains all display elements
    private View root;

    // handles the camera, as well as frames of the camera
    private Camera_handle camera_handle;

    // handles translation of sign language to english text
    private Translator translator;

    // handles all categories that is in sign language
    private Translator_categories translator_categories;

    /**
     * constructor for TranslatorFragment
     * @param activity - activity of the fragment
     * @param context - context of the fragment
     */
    public TranslatorFragment(Activity activity,Context context){
        this.activity_here = activity;
        this.context_here = context;
    }

    /**
     * This method is called when the view is created for the first time, or each time user comes to "translator" winndow
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root - Initialized view with all the elements in it.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_translator, container, false);

        // get components displayed on screen
        set_layout_components();

        //set onclick function of switch camera
        set_on_click_switch_camera();

        //set onclick function to get user's camera permission
        set_on_click_camera_permission();

        //creating new instance of camera_handle to handle camera connections
        camera_handle = new Camera_handle(context_here,activity_here,framelayout,classitext,camera_type);

        //getting the camera_handle's connection to translator (TFLITE)
        translator = camera_handle.getTranslator();

        //creating new class of translator categories
        translator_categories = new Translator_categories();

        //initialize the spinner for user to choose different categories of sign language
        init_drop_down();

        // the first category is the category loaded into the translator by default
        classify_category  =translator_categories.get_Beginning_Category();

        //loading the default category (the first category) into the translator
        translator.load_model_tflite(classify_category);

        //check if need to request for user's permission
        start_request_permission();

        return root;
    }

    /**
     * This function initializes the component inside the drop-down, as well as customize
     * the look of drop down
     */
    private void init_drop_down(){
        //generating arrayadapter for spinner to customize it
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context_here, R.layout.custom_spinner,translator_categories.getItems());

        //set the view of the drop down to look better with the theme of App
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        //set the adapter of drop down to our custom drop down
        change_category.setAdapter(adapter);

        //set what happens when a category is clicked by user
        change_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            /**
             * set what happends when a category is selected by user
             * @param parentView
             * @param selectedItemView
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // get a hashmap that maps all categories of the app to number
                HashMap <Integer,String> items_with_number =translator_categories.getItems_with_number();

                // get the category that is clicked by the user in string
                classify_category = items_with_number.get((Integer)(int)id);

                //load new category into translator
                translator.load_model_tflite(classify_category);

                //reset the interval before next translation takes place
                // this allows the translator to start checking new sign based on new category
                camera_handle.setInterval_counter(camera_handle.getTranslate_interval());
            }

            /**
             * set that nothing happends if user does not select anything from the spinner drop-down
             * @param parentView
             */
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    /**
     * This method gets all components from the user's view
     *
     */
    private void set_layout_components(){
        //getting framelayout to show camera contents
        framelayout = root.findViewById(R.id.camera_frame);

        //get the text to show user texts after translator has classified sign language
        classitext = root.findViewById(R.id.classitext);

        //get text to show user what camera type the user is currently on
        camera_type = root.findViewById(R.id.camera_type);

        //get the button that allows user to switch camera inside the app.
        change_camera = root.findViewById(R.id.switch_camera);

        //get spinner that allows user to change to different category
        change_category = root.findViewById(R.id.category_selector);

        // find button that allows app to get user's permission
        camera_permission = root.findViewById(R.id.translator_camera_permission);
    }

    /**
     * set the switch camera button to allow user to change to different camera
     */
    private void set_on_click_switch_camera(){
        change_camera.setOnClickListener(new View.OnClickListener() {
            /**
             * set to allow clicking on the change camera button will allow user to switch camera
             * @param v
             */
            @Override
            public void onClick(View v) {
                camera_handle.switch_camera_met();
            }
        });
    }

    /**
     * Allow clicking on camera permission button to allow user to get camera permission
     */
    private void set_on_click_camera_permission(){
        camera_permission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ;request_permission();
            }
        });
    }

    /**
     * Getter for activity of Translator Fragment. Used by classes that cannot access activity,
     * such as navigationview
     * @return activity - activity of translator fragment
     */
    public static Activity getActivity_here() {
        return activity_here;
    }

    /**
     * Getter for context of translator fragment, to be used by class that have no access
     * to contexts
     * @return context - context of translator fragment
     */
    public static Context getContext_here() {
        return context_here;
    }


    //permissions, as dealing with fragments is too complicated, and errorous
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void start_request_permission() {

        if (ContextCompat.checkSelfPermission(context_here, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            //permission is not granted by the user
            permission_not_granted(false);

            //request for permission from user for the camera
            request_permission();
        } else {

            //permission is already granted by user.
            permission_granted(true);

        }
    }

    /**
     * request for permission from user using a thread by calling request_permission_thread
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission() {
        request_permission_thread();

    }

    /**
     * request for camera permission from user.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission_thread() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }


    /**
     * this function is called when user has either clicked allow or deny on the user permission
     * for the user's cameras
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // if the user has granted permission

                permission_granted(false);
            } else {
                //if the user has not granted camera permission
                permission_not_granted(true);
            }
        }
    }

    /**
     * If the camera permission is granted, it will start the camera and start to classify hand signs
     * if the camera permission was recently granted by user, a toast will be shown
     * @param permission_initially_granted - boolean for if the camera permission was initially granted or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_granted(boolean permission_initially_granted) {

        if(!permission_initially_granted){
            //permission was not initially granted by user, but recently granted by user
            Toast.makeText(context_here, "Camera Permission Granted", Toast.LENGTH_SHORT).show();

        }

        //start the camera handle to start loading frames from camera onto screen and
        //sending frames into the TensorFlowLite API
        camera_handle.start_camera_met();

        //disable button to get camera permission from user, since camera permission is granted
        enable_or_disable_button(camera_permission,false);

        //show the framelayout and other components of translator to be viewed by user.
        //These elements are hidden by default
        show_translator_elements(true);

    }

    /**
     * This function is called when permission is not granted for camera
     * @param user_clicked_no - User has clicked deny permission or not
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {

        // if user has clicked not to grant permission when dialog box appears
        if(user_clicked_no){
            // show a toast saying camera permission is not granted to user
            Toast.makeText(context_here, "Camera Permission not Granted", Toast.LENGTH_SHORT).show();
        }

        // enable camera permission button so that user can show toast to grant permission of app to camera
        enable_or_disable_button(camera_permission,true);

        // hide translator elements, that is elements related to translator such as switch camera button
        show_translator_elements(false);

    }

    /**
     * this function controls the visibility of translator elements, such as switch camera button.
     * When translator elements are hidden, user cannot interact or click it.
     * @param show - boolean, if it's true, translator elements are shown. If not they are hidden
     */
    private void show_translator_elements(boolean show){
        if(show){
            //show translator elements

            //show switch_camera button
            enable_or_disable_button(change_camera,true);

            //show text which shows the result of classification of symbols
            classitext.setVisibility(View.VISIBLE);

            //shows spinner for user to change to different category for translating sign language
            change_category.setVisibility(View.VISIBLE);

            //allow user to change category by setting it to be enabled
            change_category.setEnabled(true);

        }
        else{
            //hide translator elements

            //hide switch camera button
            enable_or_disable_button(change_camera,false);

            //hide text which shows the result of classification of symbols
            classitext.setVisibility(View.GONE);

            //hides spinner for user to change to different category for translating sign language
            change_category.setVisibility(View.GONE);

            //disables user drom change category by setting it to be false
            change_category.setEnabled(false);
        }
    }

    /**
     * this function either sets a button to be visible, and enables it
     * or sets a button not to be visible, and disables it
     * @param button - button to be set
     * @param enable - boolean wether to enable it, and allow it to be seen, or disable it, and disallow it from being seen
     */
    private void enable_or_disable_button(Button button,boolean enable){

        if(enable){
            //enables a button, and allow it to be visible

            //sets a button to be visible
            button.setVisibility(View.VISIBLE);

            //sets a button to be enabled to allow user to interact with it
            button.setEnabled(true);
        }
        else{
            //disables a button, and allow it to be not visible

            //sets a button to be not visible
            button.setVisibility(View.GONE);

            //sets a button to be disabled to not allow user to interact with it
            button.setEnabled(false);
        }
    }



}