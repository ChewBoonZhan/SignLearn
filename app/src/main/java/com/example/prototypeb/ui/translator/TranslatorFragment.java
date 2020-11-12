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
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.prototypeb.R;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TranslatorFragment extends Fragment {

    private TranslatorViewModel translatorViewModel;

    public static Activity activity_here;
    public static Context context_here;

    public static int camera_num= 1;
    public static Camera camera;

    //layout components
    public static FrameLayout framelayout;
    public static ShowCamera showcamera;
    private Button change_camera;

    private static boolean switching_camera_state;

    private Bitmap bitmap;
    private Bitmap bOutput;
    private TextView classitext;

    //classifier variables
    protected Interpreter tflite;
    private  int imageSizeX;
    private  int imageSizeY;
    private TensorImage inputImageBuffer;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private List<String> labels;

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        translatorViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TranslatorViewModel.class);
        root = inflater.inflate(R.layout.fragment_translator, container, false);

        set_layout_components();
        set_on_click_switch_camera();

        load_model_tflite();

        first_entry_translator();

        return root;
    }
    public static int get_camera_num(){
        return camera_num;
    }
    private void set_layout_components(){
        framelayout = root.findViewById(R.id.camera_frame);
        classitext = root.findViewById(R.id.classitext);
        change_camera = root.findViewById(R.id.switch_camera);
    }

    private void set_on_click_switch_camera(){
        change_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch_camera_met();
            }
        });
    }

    private void start_clasify(){
        int imageTensorIndex = 0;
        int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
        //getting the input shape into the model
        imageSizeY = imageShape[1];
        imageSizeX = imageShape[2];
        DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();
        // trying to get the type of the model input

        int probabilityTensorIndex = 0;
        int[] probabilityShape =
                tflite.getOutputTensor(probabilityTensorIndex).shape(); // {1, NUM_CLASSES}
        DataType probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();
        //trying to get the shape and the data type of the output

        inputImageBuffer = new TensorImage(imageDataType);  //creating a new tensor image input

        outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
        probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();
        //these are for the output of the model

        Bitmap input_to_tensor = rotate_for_tensor();

        //convert the bitmap into a tensorimage
        inputImageBuffer = loadImage(input_to_tensor);

        tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
        //this gives the input into the mode, and get the result of the output

        showresult();
    }

    private Bitmap rotate_for_tensor(){
        int rotation_angle = 0;
        Bitmap scaledBitmap;
        Matrix matrix;
        Bitmap rotatedBitmap;
        if(camera_num == 0){
            //back_camera
            rotation_angle = 270;
        }
        else{
            rotation_angle = 90;
        }

        matrix = new Matrix();

        matrix.postRotate(rotation_angle);

        scaledBitmap = Bitmap.createScaledBitmap(bOutput, bOutput.getWidth(), bOutput.getHeight(), true);
        rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


        return rotatedBitmap;
    }

    private void switch_camera_met(){
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
        camera = Camera.open(camera_num);
        camera.setPreviewCallback(previewCallback);
        framelayout.removeAllViews();
        showcamera.change_camera(camera);
        framelayout.addView(showcamera);

        switching_camera_state = false;
    }
    private void showresult(){

        try{
            labels = FileUtil.loadLabels(activity_here,"labels.txt");   //reading the labels from the txt file
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();
        //now we have the labelled probability in float

        float maxValueInMap =(Collections.max(labeledProbability.values()));
        //finding the max of the probability


        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                classitext.setText(entry.getKey());   //this sets the text for it
            }
        }

        //note that the model needs to be mobilenet, float
    }

    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        // TODO(b/143564309): Fuse ops inside ImageProcessor.

        /*
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);
        */

        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        // TODO(b/169379396): investigate the impact of the resize algorithm on accuracy.
                        // To get the same inference results as lib_task_api, which is built on top of the Task
                        // Library, use ResizeMethod.BILINEAR.
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(new Rot90Op(-90))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);


        //resize the image, and return it as a tensorimage
    }

    private TensorOperator getPostprocessNormalizeOp(){
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }
    private void load_model_tflite(){
        try{
            //initializing the intepretor
            tflite=new Interpreter(loadmodelfile(activity_here));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd("model.tflite");
        //open the tflite file
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);

        //it returns the model as a map byte buffer
    }
    public void first_entry_translator(){
        if (ContextCompat.checkSelfPermission(context_here, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

            request_permission();
        }
        else{
            start_camera_met();
        }
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if(camera!=null){
                Camera.Parameters parameters = camera.getParameters();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                YuvImage yuvImage = new YuvImage(data, parameters.getPreviewFormat(), parameters.getPreviewSize().width, parameters.getPreviewSize().height, null);
                yuvImage.compressToJpeg(new Rect(0, 0, parameters.getPreviewSize().width, parameters.getPreviewSize().height), 90, out);
                byte[] imageBytes = out.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Matrix matrix = new Matrix();
                if(camera_num == 1){
                    //only front camera needs to invert, back dont need
                    matrix.preScale(1.0f, -1.0f);
                }


                bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                start_clasify();
                //imageviews.setImageBitmap(bOutput);
                try{
                    out.flush();
                    out.close();
                }
                catch(Exception e){
                    Log.d("Exception occured",e.getStackTrace() + "");

                }

                // Do something with the frame
            }

        }
    };





    public void start_camera_met(){
        //sending the camera into showcamera
        camera = Camera.open(camera_num);
        camera.setPreviewCallback(previewCallback);
        showcamera = new ShowCamera(activity_here,camera);


        framelayout.addView(showcamera);
        //framelayout.setRotation(image_rot);



    }

    public void request_permission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_here, Manifest.permission.CAMERA)){
            //user denied it before, therefore explain why we need it
            new AlertDialog.Builder(context_here).setTitle("Permission needed").setMessage("This permission is needed because of bla bla").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ActivityCompat.requestPermissions(activity_here, new String[] {Manifest.permission.CAMERA}, 1);
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                    dialog.dismiss();

                }
            }).create().show();
        }
        else{
            //request for permission like normal
            //ActivityCompat.requestPermissions(activity_here, new String[] {Manifest.permission.CAMERA}, 1);
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {


        if(requestCode == 1){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context_here,"Permission Granted",Toast.LENGTH_SHORT).show();
                start_camera_met();
            }
            else{
                Toast.makeText(context_here,"Permission not Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static boolean isSwitching_camera_state() {
        return switching_camera_state;
    }



}