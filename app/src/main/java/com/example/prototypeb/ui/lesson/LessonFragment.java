package com.example.prototypeb.ui.lesson;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prototypeb.R;
import com.example.prototypeb.ui.lesson.Topic.Adverbs;
import com.example.prototypeb.ui.lesson.Topic.Alphabets;
import com.example.prototypeb.ui.lesson.Topic.Attachments;
import com.example.prototypeb.ui.lesson.Topic.Numbers;
import com.example.prototypeb.ui.lesson.Topic.Pronouns;

public class LessonFragment extends Fragment {

    private LessonViewModel lessonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lessonViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LessonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);

        //Button Selection
        Button newPage = (Button) root.findViewById(R.id.lesson1_id);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Adverbs.class);
                startActivity(intent);
            }
        });
        newPage = (Button) root.findViewById(R.id.lesson2_id);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Alphabets.class);
                startActivity(intent);
            }
        });
        newPage = (Button) root.findViewById(R.id.lesson3_id);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Attachments.class);
                startActivity(intent);
            }
        });
        newPage = (Button) root.findViewById(R.id.lesson4_id);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Numbers.class);
                startActivity(intent);
            }
        });
        newPage = (Button) root.findViewById(R.id.lesson5_id);
        newPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Pronouns.class);
                startActivity(intent);
            }
        });

        return root;
    }
}