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
    //"Adverbs,Alphabets,Attachments,Numbers,Pronouns"
//    private TranslatorViewModel translatorViewModel;


    private static Activity activity_here;
    private static Context context_here;

    //layout components
    public static FrameLayout framelayout;

    private static Button change_camera;
    private static Spinner change_category;

    private static Button camera_permission;

    private TextView classitext;
    private String classify_category;
    private View root;
    private Camera_handle camera_handle;
    private Translator translator;


    private Translator_categories translator_categories;
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

        set_layout_components();
        set_on_click_switch_camera();
        set_on_click_camera_permission();
        camera_handle = new Camera_handle(context_here,activity_here,framelayout,classitext);
        translator = camera_handle.getTranslator();
        translator_categories = new Translator_categories();
        init_drop_down();
        classify_category  =translator_categories.get_Beginning_Category();
        translator.load_model_tflite(classify_category);
        start_request_permission();




        return root;
    }




    private void init_drop_down(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context_here,android.R.layout.simple_spinner_dropdown_item,translator_categories.getItems());
        change_category.setAdapter(adapter);
        change_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                HashMap <Integer,String> items_with_number =translator_categories.getItems_with_number();
                classify_category = items_with_number.get((Integer)(int)id);
                translator.load_model_tflite(classify_category);
                camera_handle.setInterval_counter(camera_handle.getTranslate_interval());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    /**
     * This method sets all the variable to call on click listeners.
     */
    private void set_layout_components(){
        framelayout = root.findViewById(R.id.camera_frame);
        classitext = root.findViewById(R.id.classitext);
        change_camera = root.findViewById(R.id.switch_camera);
        change_category = root.findViewById(R.id.category_selector);

        camera_permission = root.findViewById(R.id.translator_camera_permission);
    }

    private void set_on_click_switch_camera(){
        change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_handle.switch_camera_met();
            }
        });
    }
    private void set_on_click_camera_permission(){
        camera_permission.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                request_permission();
            }
        });
    }

    public static Activity getActivity_here() {
        return activity_here;
    }

    public static Context getContext_here() {
        return context_here;
    }


    //permissions, as dealing with fragments is too complicated.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {

        if (ContextCompat.checkSelfPermission(context_here, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permission_not_granted(false);
            request_permission();
        } else {



            permission_granted(true);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void request_permission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_here, Manifest.permission.CAMERA)) {
            //user denied it before, therefore explain why we need it
            DialogInterface.OnClickListener positive_choice =new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);
                    request_permission_thread();
                }
            };
            DialogInterface.OnClickListener negative_choice =new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                    dialog.dismiss();
                    permission_not_granted(true);
                }
            };
            Two_choice_message two_choice_message = new Two_choice_message(context_here,"Permission needed","This device's camera is needed for the Sign Language translator.","OK","CANCEL",positive_choice,negative_choice);
            two_choice_message.show_message();

        } else {
            //request for permission like normal
            //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);
            request_permission_thread();
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
            Toast.makeText(context_here, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
            //loading_screen.start_timer();


        }
        camera_handle.start_camera_met();
        enable_or_disable_button(camera_permission,false);
        show_translator_elements(true);
        //TranslatorFragment.all_important_buttons_status(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){
            Toast.makeText(context_here, "Camera Permission not Granted", Toast.LENGTH_SHORT).show();
        }


        enable_or_disable_button(camera_permission,true);
        show_translator_elements(false);
        //TranslatorFragment.all_important_buttons_status(false);

    }
    private void show_translator_elements(boolean show){
        if(show){
            enable_or_disable_button(change_camera,true);
            classitext.setVisibility(View.VISIBLE);
            change_category.setVisibility(View.VISIBLE);
            change_category.setEnabled(true);

        }
        else{
            enable_or_disable_button(change_camera,false);
            classitext.setVisibility(View.GONE);
            change_category.setVisibility(View.GONE);
            change_category.setEnabled(false);
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



}