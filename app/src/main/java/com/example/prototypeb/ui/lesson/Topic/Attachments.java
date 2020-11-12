package com.example.prototypeb.ui.lesson.Topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Attachments.Dislike;
import com.example.prototypeb.ui.lesson.Attachments.ILoveYou;
import com.example.prototypeb.ui.lesson.Attachments.Like;

public class Attachments extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attachments_menu);

        //telling the button what to do
        //Like button
        button = (Button) findViewById(R.id.like_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Like.class);
                startActivity(intent);
            }
        });
        //Dislike button
        button = (Button) findViewById(R.id.dislike_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), Dislike.class);
                startActivity(intent);
            }
        });
        //ILoveYou button
        button = (Button) findViewById(R.id.iloveyou_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivity();
            }

            public void openActivity() {
                Intent intent = new Intent(getApplicationContext(), ILoveYou.class);
                startActivity(intent);
            }
        });

    }
}