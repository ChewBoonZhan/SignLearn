package com.example.prototypeb.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.example.prototypeb.R;

public class LessonFragment extends Fragment {

    private LessonViewModel lessonViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lessonViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LessonViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lesson, container, false);

        return root;
    }
}