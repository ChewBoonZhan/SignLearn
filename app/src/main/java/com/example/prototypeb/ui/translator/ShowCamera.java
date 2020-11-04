package com.example.prototypeb.ui.translator;


import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;



public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{
    private int rotation_normal;

    static Camera camera;
    SurfaceHolder surfaceholder;

    public ShowCamera(Context context,Camera camera) {
        super(context);
        this.camera = camera;

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
        if(TranslatorFragment.get_camera_num() == 1){
            rotation_normal = 270;

        }
        else{
            rotation_normal = 90;

        }




        //change the orientation of camera
        //else camera is in wrong orientation


        //back camera 90 is correct
        //front 270 is correct
        if(this.getResources().getConfiguration().orientation!= Configuration.ORIENTATION_LANDSCAPE){
            params.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            params.setRotation(rotation_normal);
        }
        else{
            params.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            params.setRotation(0);
        }

        if(TranslatorFragment.get_camera_num()==0){
            //autofocus the camera, automatically
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
        if(!TranslatorFragment.isSwitching_camera_state()){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
        }




    }
}

