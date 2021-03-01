package com.example.prototypeb.ui.game.Game_components;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.game.GameFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class Game_adverbs extends AppCompatActivity {

    TextView quesAdverbs, textViewCoins;
    ImageButton opt1, opt2, opt3, opt4;
    ArrayList<String> adverbsSign = new ArrayList<>();
    ArrayList<String> newAdverbsSign = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    String[] answers = new String[4];
    Random random;
    int index, currentCoins;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_adverbs);
        setTitle("Adverbs");
        index = 0;
        init_backbtn();
        start_timer();

        quesAdverbs = findViewById(R.id.symbol_adverbs);

        opt1 = findViewById(R.id.option1);
        opt2 = findViewById(R.id.option2);
        opt3 = findViewById(R.id.option3);
        opt4 = findViewById(R.id.option4);

        textViewCoins = findViewById(R.id.text_view_coins);

        //Initialize Question image via String
        adverbsSign.add("yes");
        adverbsSign.add("no");
        adverbsSign.add("almost");
        adverbsSign.add("later");

        //Mapping Questions to correct answer
        map.put(adverbsSign.get(0), "yes");
        map.put(adverbsSign.get(1), "no");
        map.put(adverbsSign.get(2), "almost");
        map.put(adverbsSign.get(3), "later");
        Collections.shuffle(adverbsSign);
        random = new Random();
        generateQuestions(index);
    }

    //Method for setting up questions/rounds
    public void generateQuestions(int index) {
        String question = new String(map.get(adverbsSign.get(index)));
        quesAdverbs.setText(question);
        newAdverbsSign = (ArrayList<String>) adverbsSign.clone(); //generate wrong options
        newAdverbsSign.remove(index); //removing the correct option
        Collections.shuffle(newAdverbsSign); //randomize option placements
        int correctAnswerPosition = random.nextInt(4); //randomize correct answer position
        for(int i=0; i<4; i++){
            if(i == correctAnswerPosition)
                answers[i] = adverbsSign.get(index);
            else
                answers[i] = newAdverbsSign.get(i);
        }

        //Assign image to ImageButtons
        int opt1ID = getResources().getIdentifier(answers[0],"drawable", getPackageName());
        opt1.setImageResource(opt1ID);
        int opt2ID = getResources().getIdentifier(answers[1],"drawable", getPackageName());
        opt2.setImageResource(opt2ID);
        int opt3ID = getResources().getIdentifier(answers[2],"drawable", getPackageName());
        opt3.setImageResource(opt3ID);
        int opt4ID = getResources().getIdentifier(answers[3],"drawable", getPackageName());
        opt4.setImageResource(opt4ID);
        newAdverbsSign.clear();
    }

    //When an option(answer) is selected
    public void answerSelected(View view) {
        String answer = this.map.get(adverbsSign.get(index)); //assign var answer to option selected via String
        //If answer equals to question text (Correct answer)
        if (answer.equals(adverbsSign.get(index))) {
            currentCoins++;
            textViewCoins.setText("Coins: " + currentCoins);
            Toast.makeText(Game_adverbs.this, "Correct Answer!", Toast.LENGTH_SHORT);
            //this.setBackgroundColour(Color.GREEN);
        }
        else {
            Toast.makeText(Game_adverbs.this, "Wrong Answer!", Toast.LENGTH_SHORT);
         }
        index++;
        if (index > adverbsSign.size() - 1){
            quesAdverbs.setVisibility(View.GONE);
            openDialog();
        }
        else {
            generateQuestions(index);
        }
    }

    //Dialog for end game
    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "gameenddialog");
    }

    //Back button
    private void init_backbtn (){
        Button backbtn = findViewById(R.id.backbtn1);
        setBackbtn(backbtn);
    }

    private void setBackbtn(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GameFragment.class);
                startActivity(intent);
            }
        });
    }

    //Countdown Timer
    public void start_timer(){
        TextView timer;
        CountDownTimer countDownTimer;

        timer = findViewById(R.id.text_view_timer);

        countDownTimer = new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("TIME'S UP!");
                Toast.makeText(Game_adverbs.this, "Time's Up! No answer selected!", Toast.LENGTH_SHORT);
                index++;
                generateQuestions(index);
            }
        };
        countDownTimer.start();
    }


}
