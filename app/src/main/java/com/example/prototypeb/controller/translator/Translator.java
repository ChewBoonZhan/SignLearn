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


// code is adopted from Google TensorFlow Lite API.
/*
 * Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// link to tensorflow lite example: https://github.com/tensorflow/examples
public class Translator {
    /**
     * Constructor for translator
     * @param activity - activity to process translator in
     * @param camera_num - camera number to preprocess some part of the images before translator start to classify
     * @param classify_text - textview to show classified results to user
     */
    public Translator(Activity activity,int camera_num,TextView classify_text){
        this.activity = activity;

        this.camera_num = camera_num;
        this.classify_text = classify_text;
    }

    /**
     * Constructor for translator
     * @param activity - activity to process translator in
     * @param camera_num - camera number to preprocess some part of the images before translator start to classify
     * @param translator_verify - class to verify sign language displayed to camera
     */
    public Translator(Activity activity, int camera_num, Translator_verify translator_verify){
        this.activity = activity;
        this.translator_verify = translator_verify;
        this.camera_num = camera_num;

    }
    // class to allow user to verify sign language
    private Translator_verify translator_verify= null;
    //activity to process translator in
    private Activity activity;

    //camera number to allow preprocess of image based on front or back camera
    private int camera_num;

    // string to show classified category based on sign language showned to camera
    private String classify_category;

    // tensor image to be sent to TFLite ro preprocess
    private TensorImage inputImageBuffer;
    // size of the width of image
    private  int imageSizeX;
    //sise of the height of the image
    private  int imageSizeY;

    // final probability to determine associated text to sign language showned to camera
    private final float IMAGE_MEAN = 0.0f;
    private final float IMAGE_STD = 1.0f;
    private final float PROBABILITY_MEAN = 0.0f;
    private final float PROBABILITY_STD = 255.0f;

    // TFLite API interpreter, and helper functions
    protected Interpreter tflite;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;

    // textview to show classified text to user
    private TextView classify_text = null;

    /**
     * This function resizes the image, and returns it as a tensor image
     * @param bitmap - bitmap from the camera
     * @return TensorImage - tensor image processed and ready to be loaded into TFLite
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
     * Load the model into tflite based on the input classify category
     * @param classify_category - classify category to load the category into the tflite model
     */
    public void load_model_tflite(String classify_category){


        this.classify_category = classify_category;

        try{
            //initializing the intepretor and loading the new category into the model
            tflite=new Interpreter(loadmodelfile(activity,classify_category));   //get the activity from translator_fragment
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Loads the model, and return it as a map byte buffer
     * @param activity - activity to process translator in
     * @param classify_category - category the translator needs to classify in
     * @return MappedByteBuffer - model converted to mappedbytebuffer to be loaded as a model into TFLite API
     * @throws IOException
     */
    private MappedByteBuffer loadmodelfile(Activity activity,String classify_category) throws IOException {
        // get the actual file name based on the classify category
        String filename = classify_category + "/" + classify_category + "_" + "model.tflite";

        //load the file
        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd(filename);
        //open the tflite file
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
        //it returns the model as a map byte buffer to be loaded into translator
    }

    /**
     * this function allows image to be start to be classified into text
     * @param bitmap_image - image that needs to be classified into text
     */
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

        //this gives the input into the mode, and get the result of the output
        tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());

        // get the string classified from the input image into tflite
        String classify_result_text = set_classify_result();

        //if there is no textview to show the classified text in.
        if(classify_text == null){
            //tell the translator_verify that the text has been updated
            translator_verify.text_changed(classify_result_text);
        }
        else{
            //set the text of textview to display classified text to user
            classify_text.setText(classify_result_text);
        }
    }

    private String set_classify_result(){
        //get classify_category from translator
        String classify_result_text = "";

        //get the label txt to get associated label to the number
        String filepath = classify_category + "/" + classify_category + "_" + "labels.txt";
        List<String> labels = new ArrayList<String>();
        try{
            labels = FileUtil.loadLabels(activity,filepath);   //reading the labels from the txt file
        }catch (Exception e){
            e.printStackTrace();
        }
        //getting the labelled probability in float for each label of the translator
        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();

        //finding the max of the probability of labels. The one with the highest label is the
        //label for the sign language input into the model
        float maxValueInMap =(Collections.max(labeledProbability.values()));


        //finding the value of each element in the label of the syllabys
        // if the index of the value is the same as the index of the max value, it is the label for the
        //sign language input into model
        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                classify_result_text = (entry.getKey());   //this sets the text for it
            }
        }
        // text assocaited to the sign language is returned.
        return classify_result_text;

    }

    /**
     *
     * @param bitmap_image - bitmap image to be processed
     * @return Bitmap - output of image after image is processed
     */
    private Bitmap rotate_for_tensor(Bitmap bitmap_image){

        int rotation_angle = 0;
        Bitmap scaledBitmap;
        Matrix matrix;
        Bitmap rotatedBitmap;
        if(camera_num == 0){
            //back_camera
            rotation_angle = 270;
        }
        else{
            //front camera
            rotation_angle = 90;
        }

        matrix = new Matrix();
        //rotate according to the angle set based on the camera number
        matrix.postRotate(rotation_angle);

        scaledBitmap = Bitmap.createScaledBitmap(bitmap_image, bitmap_image.getWidth(), bitmap_image.getHeight(), true);
        rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        //bitmap after being rotated is returned
        return rotatedBitmap;
    }

    /**
     * Returns the normalized OP after postprocessing
     * @return TensorOperator
     */
    private TensorOperator getPostprocessNormalizeOp(){
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }

}
