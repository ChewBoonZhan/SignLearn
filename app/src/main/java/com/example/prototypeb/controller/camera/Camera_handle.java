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
import com.example.prototypeb.controller.translator_verify.Translator_verify;

public class Camera_handle {
    // camera number id on the user's phone
    private int camera_num;

    // boolean to check if the app is switching camera
    private boolean switching_camera_state = false;

    // camera API to interact with the phone camera
    private Camera camera;

    //context for camera_handle
    private Context context;

    //activity for camera_handle
    private Activity activity;

    //framelayout to display camera's contents
    private FrameLayout framelayout;

    //handle surface properties of the camera
    private ShowCamera showcamera;

    //handle translation of sign language to english text
    private Translator translator;

    //text to display camera type to user
    private TextView camera_type;

    //interval between translation of frames from camera to prevent lag
    private int translate_interval;

    //counter to count the interval between each translation of image
    private int interval_counter;

    // height of framelayout to show contents of camera
    private int frame_layout_height;

    // constructor of handle of camera
    public Camera_handle(Context context, Activity activity, FrameLayout framelayout, TextView classify_text,TextView camera_type){
        // set camera num to be 1, the default camera number
        camera_num = 1;

        //set context of camera handle
        this.context = context;

        // set textview camera_type to set text to be displayed to user.
        this.camera_type = camera_type;
        this.framelayout = framelayout;
        this.translate_interval = 3;
        this.interval_counter = 0;
        showcamera = new ShowCamera(context,camera,this);
        translator = new Translator(activity,camera_num,classify_text);

        this.frame_layout_height = 0;

    }
    public Camera_handle(Context context, Activity activity, FrameLayout framelayout, Translator_verify translator_verify,TextView camera_type){
        camera_num = 1;
        this.context = context;
        this.camera_type = camera_type;
        this.framelayout = framelayout;
        this.translate_interval = 3;
        this.interval_counter = 0;
        showcamera = new ShowCamera(context,camera,this);
        translator = new Translator(activity,camera_num,translator_verify);

        this.frame_layout_height = 0;

    }


    public void switch_camera_met(){
        //changing camera ID
        if(camera_num == 1){
            camera_num = 0;
            camera_type.setText("Back Camera");
        }
        else{
            camera_num = 1;
            camera_type.setText("Front Camera");
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
        // Change text here.
        set_framelayout_dimensions_to_image_sizes();

        switching_camera_state = false;
    }

    /**
     * Set framelayot dimensions to display camera contents to user
     */
    private void set_framelayout_dimensions_to_image_sizes(){
        //get the frame layout's current height
        frame_layout_height = framelayout.getHeight();


        // get the camera's preview size to be displayed on the framelayout
        Camera.Size camera_param_size = camera.getParameters().getPreviewSize();

        //set the frame to the correct size according to the camera display size
        set_frame_layout_to_size(get_correct_frame_layout_size(camera_param_size));


    }

    /**
     * Set the framelayout to the size according to the size of the preview by the camera
     * @param params - camera parameter size input into the camera
     */
    private void set_frame_layout_to_size( Hashtable<String, Integer> params){
        //get the layout parameter of the frame layout
        ViewGroup.LayoutParams frame_layout_params =  framelayout.getLayoutParams();

        //get the height of frame layout
        frame_layout_params.height = params.get("width");

        //get the width of frame layout
        frame_layout_params.width = params.get("height");

        //set the layout of frame layout to the new frame layout
        framelayout.setLayoutParams(frame_layout_params);
    }

    /**
     * get the correct frame size and put it as the frame layout's new parameter
     * @param size_of_preview_image - size of the image to be previewed on the camera
     * @return frame_params - correct size of the image from the camera to be shown on the frame layout
     */
    private  Hashtable<String, Integer> get_correct_frame_layout_size(Camera.Size size_of_preview_image){
        //get the ratio of the preview image given by the camera
        double image_ratio = (double)size_of_preview_image.width/(double)size_of_preview_image.height;

        //get the actual frame layout current height
        int frame_layout_to_be_height = frame_layout_height;

        //calculate new width of frame layout to show image
        int frame_layout_to_be_width = Math.round((float)((double)frame_layout_to_be_height/image_ratio));
        Hashtable<String, Integer> frame_params = new Hashtable<String, Integer>();

        //store new height and width in Hashtable
        frame_params.put("height",frame_layout_to_be_width);
        frame_params.put("width",frame_layout_to_be_height);

        //return it to be set into new parameter of hashtable
        return frame_params;

    }

    /**
     * This methods is called each time there is a frame received from the camera
     */
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            //if the frame layout's width and height is not initialized to the correct height, which result in stretch imaage
            if(frame_layout_height == 0 && framelayout.getWidth() >0 && framelayout.getHeight() > 0){

                //set the frame layout dimensions to an actual size
                set_framelayout_dimensions_to_image_sizes();

            }
            //if camera is already initialized, and is ready to be used
            if(camera!=null){
                //increment interval counter to determine next interval before next sign by user is translated to english
                interval_counter ++;

                //if the time break before each translation per frame is reached
                if(interval_counter >= translate_interval) {

                    ByteArrayOutputStream out  = new ByteArrayOutputStream();
                    Bitmap bitmap;
                    Bitmap bOutput;

                    //get the camera parameter
                    Camera.Parameters parameters = camera.getParameters();

                    //compress camera output to jpeg
                    YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                    yuvImage.compressToJpeg(new Rect(0, 0, parameters.getPreviewSize().width, parameters.getPreviewSize().height), 90, out);
                    byte[] imageBytes = out.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    //creating new matrix to handle image inversion and flipping
                    Matrix matrix = new Matrix();
                    if(camera_num == 1){
                        //front camera needs to invert
                        //invert camera image
                        matrix.preScale(1.0f, -1.0f,bitmap.getWidth()/2f,bitmap.getHeight()/2f);
                    }
                    else{
                        //back camera flip horizontally
                        matrix.preScale(-1.0f, 1.0f,bitmap.getWidth()/2f,bitmap.getHeight()/2f);
                    }

                    //create new image based on the inverted matrix based on the camera type
                    bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    //start classification of image based on inverted image to make the image look normal
                    translator.start_clasify(bOutput);

                    //reset interval counter so image needs to wait an interval before being sent to translator.
                    interval_counter = 0;

                    //closing byte array to prevent data leak and memory wastage.
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

    /**
     * Initialize camera by opening the camera, and setting its callback function per frmae
     */
    private void initialize_camera(){

        //opening the camera based on camera_num
        camera = Camera.open(camera_num);

        //setting its callback function per frmae
        camera.setPreviewCallback(previewCallback);

    }

    /**
     * Method to start the camera to be used
     */
    public void start_camera_met(){

        //sending the camera into showcamera
        initialize_camera();

        //creating new show camera to handle what happends when camera frame is destroyed or created
        showcamera = new ShowCamera(context,camera,this);

        //set such frame layout will show what is being shown to the camera
        framelayout.addView(showcamera);
        //framelayout.setRotation(image_rot);
    }

    /**
     * return the instance of translator from camera_handle
     * @return translator - class that handles translator of sign language to english
     */
    public Translator getTranslator(){
        return this.translator;
    }

    /**
     *  returns boolean if the current camera is being switched from front and back camera
     * @return switching_camera_state - is the camera state being switched between front and back camera
     */
    public boolean isSwitching_camera_state(){
        return switching_camera_state;
    }

    /**
     *  Returns the current camera number ID, front or back camera
     * @return camera_num - current camera number, front or back camera
     */
    public int getCamera_num() {
        return camera_num;
    }

    /**
     * set interval that passed before next sign language is to be translated
     * @param interval_counter - interval that has passed before next sign language needs to be translated
     */
    public void setInterval_counter(int interval_counter) {
        this.interval_counter = interval_counter;
    }

    /**
     * Set the counter interval before the next sign language is translated to english by camera
     * @return translator_interval - - interval before next sign language is translated to english is set
     */
    public int getTranslate_interval() {
        return translate_interval;
    }

}
