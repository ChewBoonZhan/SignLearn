package com.example.prototypeb.controller.translator;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.TextView;

import com.example.prototypeb.controller.translator_verify.Translator_verify;

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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;



public class Translator {
    public Translator(Activity activity,int camera_num,TextView classify_text){
        this.activity = activity;

        this.camera_num = camera_num;
        this.classify_text = classify_text;
    }
    public Translator(Activity activity, int camera_num, Translator_verify translator_verify){
        this.activity = activity;
        this.translator_verify = translator_verify;
        this.camera_num = camera_num;

    }
    private Translator_verify translator_verify= null;
    private Activity activity;
    private int camera_num;

    private String classify_category;

    private TensorImage inputImageBuffer;
    private  int imageSizeX;
    private  int imageSizeY;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    protected Interpreter tflite;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;
    private TextView classify_text = null;

    /**
     * This function resizes the image, and returns it as a tensor image
     * @param bitmap - bitmap from the camera
     * @return
     */
    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        // TODO(b/143564309): Fuse ops inside ImageProcessor.

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
    
    /**
     * Load the model into tflite
     */
    public void load_model_tflite(String classify_category){


        this.classify_category = classify_category;

        try{
            //initializing the intepretor
            tflite=new Interpreter(loadmodelfile(activity,classify_category));   //get the activity from translator_fragment
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Loads the mode, and return it as a map byte buffer
     * @param activity
     * @param classify_category - category the translator needs to classify i n
     * @return
     * @throws IOException
     */
    private MappedByteBuffer loadmodelfile(Activity activity,String classify_category) throws IOException {
        String filename = classify_category + "/" + classify_category + "_" + "model.tflite";

        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd(filename);
        //open the tflite file
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
        //it returns the model as a map byte buffer
    }
    public void start_clasify(Bitmap bitmap_image){
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

        Bitmap input_to_tensor = rotate_for_tensor(bitmap_image);

        //convert the bitmap into a tensorimage
        inputImageBuffer = loadImage(input_to_tensor);

        tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
        //this gives the input into the mode, and get the result of the output

        String classify_result_text = set_classify_result();
        if(classify_text == null){
            translator_verify.text_changed(classify_result_text);
        }
        else{
            classify_text.setText(classify_result_text);   //this sets the text for it
        }
    }

    private String set_classify_result(){
        //get classify_category from translator
        String classify_result_text = "";
        String filepath = classify_category + "/" + classify_category + "_" + "labels.txt";
        List<String> labels = new ArrayList<String>();
        try{
            labels = FileUtil.loadLabels(activity,filepath);   //reading the labels from the txt file
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
                classify_result_text = (entry.getKey());   //this sets the text for it
            }
        }
        return classify_result_text;
        //note that the model needs to be quantized, based on latest testings
    }

    private Bitmap rotate_for_tensor(Bitmap bitmap_image){
        //get camera_num from translator

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

        scaledBitmap = Bitmap.createScaledBitmap(bitmap_image, bitmap_image.getWidth(), bitmap_image.getHeight(), true);
        rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);


        return rotatedBitmap;
    }

    private TensorOperator getPostprocessNormalizeOp(){
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }
    public void setClassify_category(String classify_category) {
        this.classify_category = classify_category;
    }
}
