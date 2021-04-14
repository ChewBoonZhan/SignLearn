package com.example.prototypeb.ui.game.Game_components;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.prototypeb.R;
import com.example.prototypeb.controller.app_data.App_data;
import com.example.prototypeb.controller.app_data.Category_elements;
import com.example.prototypeb.controller.choice_message.One_choice_message;
import com.example.prototypeb.controller.file_connections.File_connections;
import com.example.prototypeb.controller.sub_action_bar.Sub_action_bar;
import com.example.prototypeb.controller.toast.Alert_toast;
import com.example.prototypeb.controller.toast.Custom_toasts;
import com.example.prototypeb.controller.toast.Success_toast;
import com.example.prototypeb.ui.game.GameFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public abstract class Game_screen extends Sub_action_bar implements  Game_components{
    //set score increment to const 7 for every correct answer
    private final int SCORE_INCREMENT = 7;
    //Declare answer options as buttons in array;
    private ArrayList<Button> options;
    //Declare answer as string in array to be mapped
    private ArrayList <String> signLang;
    //HashMap for question to answer mapping
    private HashMap<String, String> map;
    //HashMap for recording whether question has been answered correctly
    private HashMap <String,Boolean> sign_passed;
    //In-game countdown timer
    private CountDownTimer countDownTimer;
    //Display remaining time as text
    private TextView timer;
    //Each game frame components
    private Context context;
    //Main game component
    private Context game_main_context;
    //Integers for indexing and Points gained
    private int index, currentPoints;
    //Toast for wrong answer
    private Alert_toast alert_toast;
    //Toast for correct answer
    private Success_toast success_toast;
    //Image for questions and timer
    private ImageView imageQuestion, timerImage;
    private File_connections file_connections;
    //set number of answer options to a const 4
    private final int NUMBER_OF_CHOICES = 4;
    //index for data saving
    private int app_data_index;
    public Game_screen(){
        game_main_context = GameFragment.getGame_context();
        index = 0;
        currentPoints = 0;
        file_connections = new File_connections(game_main_context);

    }

    /**
     * Initializes all game components
     */
    private void init_game_elements(){
       get_screen_button_options();
       get_screen_elements();

       set_points(getCurrentPoints());

       setComponent_images();
   }

    /**
     * Initializes correct/wrong answer toasts
     */
    private void init_toasts(){
        alert_toast = new Alert_toast(context);
        success_toast = new Success_toast(context);
    }

    /**
     * Initialize buttons for answer options
     */
    public void get_screen_button_options(){
        options = new ArrayList <Button> ();
        options.add(findViewById(R.id.opt1));
        options.add(findViewById(R.id.opt2));
        options.add(findViewById(R.id.opt3));
        options.add(findViewById(R.id.opt4));

    }

    /**
     * Check if question has been answered correctly previously
     */
    private void init_game_passed(){
        sign_passed = new HashMap<String,Boolean>();
        int length = signLang.size();
        for(int i = 0;i<length;i++){
            sign_passed.put(signLang.get(i),file_connections.check_game_category_passed(signLang.get(i)));
        }
    }

    /**
     * Initialize HashMap to map answers to questions
     */
    private void init_map(){
        map= new HashMap<String, String>();
        int length = signLang.size();
        for(int i = 0;i<length;i++){
            map.put(signLang.get(i),signLang.get(i));
        }

    }
    @Override
    /**
     * check if all answers have been correctly answered
     * @return categories_passed - all answers answered correctly
     */
    public boolean check_all_categories_passed(){
        boolean categories_passed = true;
        int length = sign_passed.size();
        for(int i = 0;i<length;i++){
            categories_passed = categories_passed & file_connections.check_game_category_passed(signLang.get(i));
        }
        return categories_passed;
    }

    /**
     * Get method for HashMap
     * @return map
     */
    public HashMap<String, String> getMap(){
        return map;
    }

    @Override
    /**
     * Load game screen
     * @param savedInstanceState
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }

    @Override
    /**
     * Resume action
     */
    protected void onResume() {
        super.onResume();
        App_data app_data = new App_data();


        init_game_elements();
        set_title_text(app_data.getCategories()[app_data_index]);
    }

    @Override
    /**
     * Stop action
     */
    protected void onDestroy() {
        super.onDestroy();
        //cancel the timer when user leave the screen
        countDownTimer.cancel();
    }

    /**
     *  Function called when an option(answer) is selected
     */
    public void answerSelected(View view) {
        countDownTimer.cancel();
        String answer = ((Button)view).getText().toString(); //assign var answer to option selected via String
        //If answer equals to question text (Correct answer)
        if (answer.equals(signLang.get(index))) {

            if(!sign_passed.get(answer)){ //if question has been answered correctly for the first time

                currentPoints=currentPoints+SCORE_INCREMENT;

                set_points(currentPoints);

                file_connections.set_game_category_passed(answer);

                success_toast.show_toast("Correct Answer!\nPoint +"+SCORE_INCREMENT,false);

            }
            else{
                success_toast.show_toast("Correct Answer!",false);
            }



        }
        //wrong option(answer) selected
        else {
            alert_toast.show_toast("Wrong Answer!",false);

        }
        single_question_over();
    }

    /**
     * called when no more questions left
     * @return index exceeding (number of questions - 1)
     */
    private boolean no_more_questions(){
        return (index > (signLang.size() - 1));
    }

    /**
     * Game Ended method
     */
    private void end_game(){
        imageQuestion.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        timerImage.setVisibility(View.GONE);
        countDownTimer.cancel();
        openDialog();
    }

    /**
     * back button pressed
     */
    private void press_back(){
        //equal to pressing the back button
        super.onBackPressed();
    }

    /**
     * Pop up end game dialog
     */
    private void openDialog() {
        String message = "Good job! You have successfully collected:\n\n"+ currentPoints+" Points!";

        if(currentPoints<1){
            if(check_all_categories_passed()){
                message = "You did well, however no points are collected as you have already scored all categories before.";
            }
            else{
                message = "Unfortunately, you have only collected: \n\n"+currentPoints+" Points.";

            }

        }
        DialogInterface.OnClickListener on_click =new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                press_back();
            }
        };

        One_choice_message one_choice_message = new One_choice_message(this,"GAME ENDED",message,"Okay",on_click);
        one_choice_message.show_message();
        file_connections.update_score(file_connections.getScore()+getCurrentPoints());

    }

    /**
     * Initialize syllabus for respective game category from app data
     * @param app_data_index
     */
    public void init_syllabus(int app_data_index){
        this.app_data_index = app_data_index;
        Category_elements category_elements = new Category_elements();
        App_data app_data = new App_data();
        String[] all_categories = app_data.getCategories();
        signLang = category_elements.getCategory_elements().get(all_categories[app_data_index]);

        init_map();
        init_game_passed();
        Collections.shuffle(signLang);
    }

    /**
     * Generate questions
     * @param index
     */
    private void generateQuestions(int index) {
        String[] answers = new String[4];
        ArrayList<String> newSignLang = new ArrayList<>();
        start_timer();
        String question = new String(getMap().get(signLang.get(index)));

        int questionChange = getResources().getIdentifier(question.toLowerCase().replaceAll("[\\s\"]", "")+"_still","drawable", getPackageName());
        imageQuestion.setImageResource(questionChange); //set image for question based on string contents in array

        newSignLang = (ArrayList<String>) signLang.clone(); //generate wrong options
        newSignLang.remove(index); //removing the correct option
        Collections.shuffle(newSignLang); //randomize option placements
        int correctAnswerPosition = new Random().nextInt(NUMBER_OF_CHOICES); //randomize correct answer position
        int get_new_index = 0;
        for(int i=0; i<NUMBER_OF_CHOICES; i++){

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

    /**
     * Start countdown timer
     */
    private void start_timer(){
        timer = findViewById(R.id.text_view_timer);

        countDownTimer = new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("TIME'S UP!");
                alert_toast.show_toast("Time's Up! No answer selected!",false);
                single_question_over();
            }
        };
        countDownTimer.start();
    }

    /**
     * Question answered
     */
    private void single_question_over(){
        index++;
        if(no_more_questions()){
            end_game();
        }
        else {
            generateQuestions(index);
        }
    }

    /**
     * Get method for points
     * @return currentPoints
     */
    public int getCurrentPoints(){
        return currentPoints;
    }

    /**
     * Set method for toasts
     * @param context
     */
    public void setContext(Context context){
        this.context = context;
        init_toasts();
    }

    /**
     * Set method for game screen images
     */
    public void setComponent_images(){
        this.imageQuestion = findViewById(R.id.imageQuestion);
        this.timerImage = findViewById(R.id.timer_image);
        generateQuestions(index);
    }
}
