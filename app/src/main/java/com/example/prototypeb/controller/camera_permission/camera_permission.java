package com.example.prototypeb.controller.camera_permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import androidx.annotation.RequiresApi;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.camera.Camera_handle;

import com.example.prototypeb.ui.translator.TranslatorFragment;

import java.util.function.Consumer;

public class camera_permission extends Fragment {

    private Context context;
    private Camera_handle camera_handle;
    private Activity activity;
    private Button allow_camera_button;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void start_request_permission() {
        get_permission_datas();

        init_allow_camera_button();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permission_not_granted(false);
            request_permission();
        } else {
            camera_handle.start_camera_met();
            permission_granted(true);

        }
    }


    private void get_permission_datas() {
        activity = TranslatorFragment.getActivity_here();
        context = TranslatorFragment.getContext_here();
        camera_handle = camera_permission_data.getCamera_handle();
        allow_camera_button = camera_permission_data.getAllow_camera_button();

    }

    private void init_allow_camera_button() {
        allow_camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_permission();

            }
        });

    }

    public void request_permission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            //user denied it before, therefore explain why we need it
            new AlertDialog.Builder(context).setTitle("Permission needed").setMessage("This permission is needed for the Sign Language translator.").setPositiveButton("ok", new DialogInterface.OnClickListener() {
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
            Log.d("Odd","Error incoming");
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
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
            camera_handle.start_camera_met();
        }
        disable_allow_camera_button();
        TranslatorFragment.all_important_buttons_status(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void permission_not_granted(boolean user_clicked_no) {
        if(user_clicked_no){
            Toast.makeText(context, "Permission not Granted", Toast.LENGTH_SHORT).show();
        }

        enable_allow_camera_button();
        TranslatorFragment.all_important_buttons_status(false);

    }

    private void disable_allow_camera_button() {
        allow_camera_button.setVisibility(View.GONE);
        allow_camera_button.setEnabled(false);
    }

    private void enable_allow_camera_button() {
        allow_camera_button.setVisibility(View.VISIBLE);
        allow_camera_button.setEnabled(true);
    }
}

