package com.example.prototypeb.controller.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.prototypeb.ui.translator.TranslatorFragment;

import java.lang.reflect.Parameter;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{
    private int rotation_normal;
    static Camera camera;
    private Camera_handle camera_handle;
    SurfaceHolder surfaceholder;

    public ShowCamera(Context context, Camera camera,Camera_handle camera_handle) {
        super(context);
        this.camera = camera;
        this.camera_handle = camera_handle;
        surfaceholder = getHolder();
        surfaceholder.addCallback(this);
        //after this surface created will be called
    }

    public void change_camera(Camera camera){
        this.camera = camera;
        surfaceholder = getHolder();
        surfaceholder.addCallback(this);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Camera.Parameters params = camera.getParameters();
        if(camera_handle.getCamera_num() == 1){
            //front camera
            rotation_normal = 270;
        }
        else{
            //back camera
            rotation_normal = 90;
        }

        params.set("orientation","portrait");

        camera.setDisplayOrientation(90);
        params.setRotation(rotation_normal);

        if(camera_handle.getCamera_num()==0){
            //autofocus the camera, automatically back camera
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        camera.setParameters(params);
        try{
            camera.setPreviewDisplay(surfaceholder);
            camera.startPreview();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("Surface:","Changed");
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("Surface:","Destroyed");
        if(!camera_handle.isSwitching_camera_state()){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
        }




    }

}
