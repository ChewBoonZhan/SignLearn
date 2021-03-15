package com.example.prototypeb.ui.game.Game_components;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.toast.Custom_toasts;
import com.example.prototypeb.controller.toast.Success_toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Game_screen extends Sub_action_bar {
    private ArrayList<Button> options;
    private ArrayList <String> signLang;
    private HashMap<String, String> map;
    private CountDownTimer countDownTimer;
    private TextView timer;
    private Context context;
    private int index, currentPoints;
    private Alert_toast alert_toast;
    private Success_toast success_toast;
    private ImageView imageQuestion, timerImage;
    public Game_screen(){

        index = 0;
        currentPoints = 0;


    }
   public void init_game_elements(){
       get_screen_button_options();
       get_screen_elements();
       set_title_text("Adverbs");
       set_points(getCurrentPoints());

       setComponent_images();
   }
    private void init_toasts(){
        alert_toast = new Alert_toast(context);
        success_toast = new Success_toast(context);
    }
    public void get_screen_button_options(){
        options = new ArrayList <Button> ();
        options.add(findViewById(R.id.opt1));
        options.add(findViewById(R.id.opt2));
        options.add(findViewById(R.id.opt3));
        options.add(findViewById(R.id.opt4));

    }
    public ArrayList <Button> get_options(){
        return options;
    }
    public void setSignLang(ArrayList <String> signLang){
        this.signLang = signLang;
        init_map();
        Collections.shuffle(signLang);
    }
    public void init_map(){
        map= new HashMap<String, String>();
        int length = signLang.size();
        for(int i = 0;i<length;i++){
            map.put(signLang.get(i),signLang.get(i));
        }

    }
    public HashMap<String, String> getMap(){
        return map;
    }
    //When an option(answer) is selected
    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button)view).getText().toString(); //assign var answer to option selected via String
        //If answer equals to question text (Correct answer)
        if (answer.equals(signLang.get(index))) {
            currentPoints++;

            set_points(currentPoints);
            success_toast.show_toast("Correct Answer!",false);

        }
        else {
            alert_toast.show_toast("Wrong Answer!",false);
            //wrongToast.setGravity(Gravity.CENTER_VERTICAL,0,0);

        }
        index++;
        if (index > signLang.size() - 1){
            imageQuestion.setVisibility(View.GONE);
            timer.setVisibility(View.GONE);
            timerImage.setVisibility(View.GONE);
            openDialog();
        }
        else {
            generateQuestions(index);
        }
    }
    private void press_back(){
        //equal to pressing the back button
        super.onBackPressed();
    }
    //Dialog for end game
    public void openDialog() {
        String opening_string = "Good Job! Y";
        String closing_string = "successfully";
        if(currentPoints<1){
            opening_string = "Unfortunately, y";
            closing_string = "only";
        }
        DialogInterface.OnClickListener on_click =new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                press_back();
            }
        };

        One_choice_message one_choice_message = new One_choice_message(this,"GAME ENDED",opening_string+"ou have "+closing_string+" collected:\n\n" + currentPoints + " Points.","Okay",on_click);
        one_choice_message.show_message();

    }
    //Method for setting up questions/rounds
    public void generateQuestions(int index) {
        String[] answers = new String[4];
        ArrayList<String> newSignLang = new ArrayList<>();
        start_timer();
        String question = new String(getMap().get(signLang.get(index)));

        int questionChange = getResources().getIdentifier(question+"_still","drawable", getPackageName());
        imageQuestion.setImageResource(questionChange); //set image for question based on string contents in array

        newSignLang = (ArrayList<String>) signLang.clone(); //generate wrong options
        newSignLang.remove(index); //removing the correct option
        Collections.shuffle(newSignLang); //randomize option placements
        int correctAnswerPosition = new Random().nextInt(4); //randomize correct answer position
        int get_new_index = 0;
        for(int i=0; i<4; i++){

            if(i == correctAnswerPosition)
                answers[i] = signLang.get(index);
            else
            {
                answers[i] = newSignLang.get(get_new_index);
                get_new_index++;
            }
        }

        int length = options.size();
        for(int i = 0;i<length;i++){
            options.get(i).setText(answers[i]);
        }

        newSignLang.clear();
    }

    //Countdown Timer
    public void start_timer(){
        timer = findViewById(R.id.text_view_timer);

        countDownTimer = new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("TIME'S UP!");
                alert_toast.show_toast("Time's Up! No answer selected!",false);

                index++;
                generateQuestions(index);

            }
        };
        countDownTimer.start();
    }
    public int getCurrentPoints(){
        return currentPoints;
    }
    public void setContext(Context context){
        this.context = context;
        init_toasts();
    }
    public void setComponent_images(){
        this.imageQuestion = findViewById(R.id.imageQuestion);
        this.timerImage = findViewById(R.id.timer_image);
        generateQuestions(index);
    }
}
