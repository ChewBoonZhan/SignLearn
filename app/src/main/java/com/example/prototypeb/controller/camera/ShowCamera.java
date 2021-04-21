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
    private Camera camera;
    private Camera_handle camera_handle;
    SurfaceHolder surfaceholder;

    /**
     * Constructor
     * @param context - context to handle camera
     * @param camera - camera to preview callback
     * @param camera_handle - camera handle to handle other functions of the camera
     */
    public ShowCamera(Context context, Camera camera,Camera_handle camera_handle) {
        super(context);
        this.camera = camera;
        this.camera_handle = camera_handle;
        surfaceholder = getHolder();
        surfaceholder.addCallback(this);
        //after this surface created will be called
    }

    /**
     * User switch camera
     * @param camera - new camera user switched to
     */
    public void change_camera(Camera camera){

        // set the current camera as new camera
        this.camera = camera;
        surfaceholder = getHolder();
        surfaceholder.addCallback(this);
    }

    /**
     * This function is called when the surface is created for camera to be showed
     * @param holder
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Camera.Parameters params = camera.getParameters();

        // rotation of camera frame to be showed to user
        if(camera_handle.getCamera_num() == 1){
            //front camera
            rotation_normal = 270;

            // rotation of 270 so it is previwed normally to user
        }
        else{
            //back camera
            rotation_normal = 90;

            // rotation of 90 so it is previwed normally to user
        }
        // set orientation of camera to be always portrait
        params.set("orientation","portrait");

        // set display orientation of camera to be always 90
        camera.setDisplayOrientation(90);

        params.setRotation(rotation_normal);

        if(camera_handle.getCamera_num()==0){
            //autofocus the camera, automatically back camera
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        camera.setParameters(params);
        try{
            // start the preview of camera to be showed on screen
            camera.setPreviewDisplay(surfaceholder);
            camera.startPreview();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * When surface destroyed, user is not on camera screen anymore
     * User can be on other screens
     * @param holder
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        // if the camera is being switched
        if(!camera_handle.isSwitching_camera_state()){

            // do not send frames into tensorflow lite api
            camera.setPreviewCallback(null);

            // stop preview of camera on screen
            camera.stopPreview();

            // release the camera
            camera.release();
        }




    }

}
