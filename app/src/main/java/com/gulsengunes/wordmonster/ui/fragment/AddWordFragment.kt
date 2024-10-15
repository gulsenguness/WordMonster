package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.databinding.ActivityMainBinding
import com.gulsengunes.wordmonster.databinding.FragmentAddWordBinding


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWordBinding.inflate(layoutInflater)
        return binding.root
    }

}