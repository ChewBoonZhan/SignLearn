package com.example.prototypeb.controller.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import com.example.prototypeb.controller.translator.Translator;

public class Camera_handle {
    private int camera_num;
    private boolean switching_camera_state = false;
    private Camera camera;

    private Context context;
    private Activity activity;
    private FrameLayout framelayout;
    private ShowCamera showcamera;
    private Translator translator;
    private int translate_interval;
    private int interval_counter;

    private int frame_layout_height;


    public Camera_handle(Context context, Activity activity, FrameLayout framelayout, TextView classify_text){
        camera_num = 1;
        this.context = context;
        this.framelayout = framelayout;
        this.translate_interval = 3;
        this.interval_counter = 0;
        showcamera = new ShowCamera(context,camera,this);
        translator = new Translator(activity,camera_num,classify_text);

        this.frame_layout_height = 0;

    }

    public void switch_camera_met(){
        //changing camera ID
        if(camera_num == 1){
            camera_num = 0;
        }
        else{
            camera_num = 1;
        }
        switching_camera_state = true;

        //resetting old camera
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();

        //initializing new camera
        initialize_camera();
        framelayout.removeAllViews();
        showcamera.change_camera(camera);
        framelayout.addView(showcamera);
        set_framelayout_dimensions_to_image_sizes();

        switching_camera_state = false;
    }
    private void set_framelayout_dimensions_to_image_sizes(){

        frame_layout_height = framelayout.getHeight();



        Camera.Size camera_param_size = camera.getParameters().getPreviewSize();


        set_frame_layout_to_size(get_correct_frame_layout_size(camera_param_size));


    }
    private void set_frame_layout_to_size( Hashtable<String, Integer> params){
        ViewGroup.LayoutParams frame_layout_params =  framelayout.getLayoutParams();

        frame_layout_params.height = params.get("width");
        frame_layout_params.width = params.get("height");

        framelayout.setLayoutParams(frame_layout_params);
    }
    private  Hashtable<String, Integer> get_correct_frame_layout_size(Camera.Size size_of_preview_image){
        double image_ratio = (double)size_of_preview_image.width/(double)size_of_preview_image.height;

        int frame_layout_to_be_height = frame_layout_height;

        int frame_layout_to_be_width = Math.round((float)((double)frame_layout_to_be_height/image_ratio));
        Hashtable<String, Integer> frame_params = new Hashtable<String, Integer>();

        frame_params.put("height",frame_layout_to_be_width);
        frame_params.put("width",frame_layout_to_be_height);

        return frame_params;

    }

    /**
     * This methods is called each time there is a frame received from the camera
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            if(frame_layout_height == 0 && framelayout.getWidth() >0 && framelayout.getHeight() > 0){

                set_framelayout_dimensions_to_image_sizes();

            }

            if(camera!=null){

                interval_counter ++;
                if(interval_counter >= translate_interval) {
                    ByteArrayOutputStream out  = new ByteArrayOutputStream();
                    Bitmap bitmap;
                    Bitmap bOutput;
                    Camera.Parameters parameters = camera.getParameters();

                    YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                    yuvImage.compressToJpeg(new Rect(0, 0, parameters.getPreviewSize().width, parameters.getPreviewSize().height), 90, out);
                    byte[] imageBytes = out.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    Matrix matrix = new Matrix();
                    if(camera_num == 1){
                        //front camera needs to invert

                        matrix.preScale(1.0f, -1.0f,bitmap.getWidth()/2f,bitmap.getHeight()/2f);
                    }
                    else{
                        //back camera flip horizontally
                        matrix.preScale(-1.0f, 1.0f,bitmap.getWidth()/2f,bitmap.getHeight()/2f);
                    }


                    bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    translator.start_clasify(bOutput);
                    interval_counter = 0;

                    try{
                        out.flush();
                        out.close();
                    }
                    catch(Exception e){
                        Log.d("Exception occured",e.getStackTrace() + "");
                    }
                }
            }
        }
    };

    private void initialize_camera(){
        camera = Camera.open(camera_num);
        camera.setPreviewCallback(previewCallback);

    }

    public void start_camera_met(){
        //sending the camera into showcamera
        initialize_camera();
        showcamera = new ShowCamera(context,camera,this);


        framelayout.addView(showcamera);
        //framelayout.setRotation(image_rot);
    }

    public Translator getTranslator(){
        return this.translator;
    }
    public boolean isSwitching_camera_state(){
        return switching_camera_state;
    }
    public int getCamera_num() {
        return camera_num;
    }
    public void setInterval_counter(int interval_counter) {
        this.interval_counter = interval_counter;
    }
    public int getTranslate_interval() {
        return translate_interval;
    }

}
