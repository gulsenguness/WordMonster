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
import com.gulsengunes.wordmonster.data.repository.WordRepository
import com.gulsengunes.wordmonster.databinding.FragmentAddWordBinding
import com.gulsengunes.wordmonster.databinding.FragmentWordBinding
import com.gulsengunes.wordmonster.ui.adapter.WordAdapter

class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var repository: WordRepository
    private var wordList: List<Word> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(layoutInflater)
        repository = WordRepository()
        setupRecyclerView()
        loadWords()
        return binding.root
    }

    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(wordList)
        binding.recyclerWord.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerWord.adapter = wordAdapter
    }

    private fun loadWords() {
        repository.getWords { words ->
            wordList = words
            wordAdapter.updateWordList(wordList)
        }
    }

}