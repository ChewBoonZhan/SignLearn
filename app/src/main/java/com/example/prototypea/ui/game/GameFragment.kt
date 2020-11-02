package com.example.prototypea.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import com.example.prototypea.R

class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gameViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(GameViewModel::class.java)
        val root = inflater.inflate(R.layout.game, container, false)


        return root
    }
}