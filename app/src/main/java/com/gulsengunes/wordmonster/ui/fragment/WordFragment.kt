package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gulsengunes.wordmonster.R
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.databinding.FragmentAddWordBinding
import com.gulsengunes.wordmonster.databinding.FragmentWordBinding
import com.gulsengunes.wordmonster.ui.adapter.WordAdapter

class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordAdapter: WordAdapter

    //test
    private val wordList = listOf(
        Word("Ephemeral", "Lasting for a very short time"),
        Word("Serendipity", "Finding something good without looking for it"),
        Word("Petrichor", "The pleasant smell after rain"),
        Word("Gunes", "The pleasant smell after rain"),
        Word("Ay", "The pleasant smell after rain"),
        Word("Ay", "The pleasant smell after rain"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(layoutInflater)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(wordList)
        binding.recyclerWord.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerWord.adapter = wordAdapter
    }

}