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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;


import androidx.annotation.RequiresApi;

import com.example.prototypeb.R;



import com.example.prototypeb.controller.camera.Camera_handle;
import com.example.prototypeb.controller.camera_permission.camera_permission;

import com.example.prototypeb.controller.translator.Translator;
import com.example.prototypeb.controller.camera_permission.camera_permission_data;
import com.example.prototypeb.controller.translator.Translator_categories;

import java.util.HashMap;
import java.util.function.Consumer;

public class TranslatorFragment extends Fragment {
    //"Adverbs,Alphabets,Attachments,Numbers,Pronouns"
//    private TranslatorViewModel translatorViewModel;


    private static Activity activity_here;
    private static Context context_here;


    public static int camera_num= 1;


    //layout components
    public static FrameLayout framelayout;

    private static Button change_camera;
    private static Spinner change_category;
    private Button allow_camera_permission;


    private TextView classitext;
    private String classify_category;
    private View root;
    private Camera_handle camera_handle;
    private Translator translator;
    private camera_permission camera_permissions;

    private Translator_categories translator_categories;
    

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
        camera_handle = new Camera_handle(context_here,activity_here,framelayout,classitext);
        translator = camera_handle.getTranslator();
        translator_categories = new Translator_categories();
        init_drop_down();
        classify_category  =translator_categories.get_Beginning_Category();
        translator.load_model_tflite(classify_category);
        setup_permission_data();

        if(camera_permissions== null){

            camera_permissions = new camera_permission();
        }
        camera_permissions.start_request_permission();
        getFragmentManager().beginTransaction().replace(R.id.container_layout,camera_permissions).commit();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(camera_permissions== null){
            camera_permissions = new camera_permission();
        }
    }

    public static void all_important_buttons_status(boolean enable){
        change_camera.setEnabled(enable);
        change_category.setEnabled(enable);
    }
    private void setup_permission_data(){
        camera_permission_data.setCamera_handle(camera_handle);
        camera_permission_data.setAllow_camera_button(allow_camera_permission);
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
        allow_camera_permission = root.findViewById(R.id.allow_camera_permission);



    }

    private void set_on_click_switch_camera(){
        change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_handle.switch_camera_met();
            }
        });
    }



    public static int get_camera_type(){
        return camera_num;
    }
    public static Activity getActivity_here() {
        return activity_here;
    }
    public static void setActivity_here(Activity activity_here) {
        TranslatorFragment.activity_here = activity_here;
    }

    public static void setContext_here(Context context_here) {
        TranslatorFragment.context_here = context_here;
    }
    public static Context getContext_here() {
        return context_here;
    }


}