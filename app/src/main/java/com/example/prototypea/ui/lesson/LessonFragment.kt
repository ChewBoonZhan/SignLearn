package com.example.prototypea.ui.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import com.example.prototypea.R

class LessonFragment : Fragment() {

    private lateinit var lessonViewModel: LessonViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        lessonViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(LessonViewModel::class.java)
        val root = inflater.inflate(R.layout.lesson, container, false)

        return root
    }
}