package com.example.prototypeb.controller.camera_permission;


import android.widget.Button;
import java.util.function.Consumer;
import com.example.prototypeb.controller.camera.Camera_handle;

import java.security.Policy;

public class camera_permission_data {


    private static Camera_handle camera_handle;
    public static void setCamera_handle(Camera_handle in_camera_handle){
        camera_handle = in_camera_handle;
    }
    public static Camera_handle getCamera_handle(){
        return camera_handle;
    }

    private static Button allow_camera_button;
    public static void setAllow_camera_button(Button in_allow_camera_button){
        allow_camera_button = in_allow_camera_button;
    }
    public static Button getAllow_camera_button(){
        return allow_camera_button;
    }

    private static Consumer <Boolean> other_important_button_status;
    public static void setOther_important_button_status(Consumer <Boolean> in_disable_other_important_button){
        other_important_button_status = in_disable_other_important_button;
    }
    public static Consumer <Boolean> getOther_important_button_status(){
        return other_important_button_status;
    }

}
