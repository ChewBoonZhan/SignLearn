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
import android.support.annotation.Nullable;
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
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import com.example.prototypeb.R;



import com.example.prototypeb.controller.camera.Camera_handle;


import com.example.prototypeb.controller.translator.Translator;

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




        start_request_permission();


        return root;
    }



    public static void all_important_buttons_status(boolean enable){
        change_camera.setEnabled(enable);
        change_category.setEnabled(enable);
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



    //permissions, as dealing with fragments is too complicated.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {
        //get_permission_datas();

        init_allow_camera_button();
        if (ContextCompat.checkSelfPermission(context_here, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permission_not_granted(false);
            request_permission();
        } else {
            camera_handle.start_camera_met();
            permission_granted(true);

        }
    }

    /*
    private void get_permission_datas() {
        activity = TranslatorFragment.getActivity_here();
        context = TranslatorFragment.getContext_here();
        camera_handle = camera_permission_data.getCamera_handle();
        allow_camera_button = camera_permission_data.getAllow_camera_button();

    }

     */

    private void init_allow_camera_button() {
        allow_camera_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_permission();

            }
        });

    }

    public void request_permission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_here, Manifest.permission.CAMERA)) {
            //user denied it before, therefore explain why we need it
            new AlertDialog.Builder(context_here).setTitle("Permission needed").setMessage("This permission is needed for the Sign Language translator.").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);

                    request_permission_thread();
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                    dialog.dismiss();
                    permission_not_granted(true);
                }
            }).create().show();
        } else {
            //request for permission like normal
            //ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, 1);
            request_permission_thread();
        }
    }

    private void request_permission_thread() {

        if (!isAdded()) {

            Log.d("Odd","Error incoming?");

        }
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
            Toast.makeText(context_here, "Permission Granted", Toast.LENGTH_SHORT).show();
            camera_handle.start_camera_met();
        }
        disable_allow_camera_button();
        TranslatorFragment.all_important_buttons_status(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){
            Toast.makeText(context_here, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }

        enable_allow_camera_button();
        TranslatorFragment.all_important_buttons_status(false);

    }
    private void disable_allow_camera_button() {
        allow_camera_permission.setVisibility(View.GONE);
        allow_camera_permission.setEnabled(false);
    }

    private void enable_allow_camera_button() {
        allow_camera_permission.setVisibility(View.VISIBLE);
        allow_camera_permission.setEnabled(true);
    }


}