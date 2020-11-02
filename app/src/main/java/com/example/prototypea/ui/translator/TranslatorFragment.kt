package com.example.prototypea.ui.translator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import com.example.prototypea.R

class TranslatorFragment : Fragment() {

    private lateinit var translatorViewModel: TranslatorViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        translatorViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(TranslatorViewModel::class.java)
        val root = inflater.inflate(R.layout.translator, container, false)

        return root
    }
}