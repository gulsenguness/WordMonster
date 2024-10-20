package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.FavoriteRepository
import com.gulsengunes.wordmonster.data.repository.LearnedRepository
import com.gulsengunes.wordmonster.data.repository.WordRepository
import com.gulsengunes.wordmonster.databinding.FragmentWordBinding
import com.gulsengunes.wordmonster.ui.adapter.WordAdapter

class WordFragment : Fragment() {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var repository: WordRepository
    private lateinit var learnedRepository: LearnedRepository
    private var wordList: List<Word> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(layoutInflater)
        repository = WordRepository()
        learnedRepository = LearnedRepository(requireContext())
        setupRecyclerView()
        loadWords()
        setupSearchBar()
        return binding.root
    }


    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(wordList, learnedRepository)
        binding.recyclerWord.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerWord.adapter = wordAdapter
    }

    private fun loadWords() {
        repository.getWords { words ->
            wordList = words
            val learnedWords = learnedRepository.getAllLearnedWords()
            wordList = wordList.filter { word -> !learnedWords.contains(word.word) }
            wordAdapter.updateWordList(wordList)
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterWordList(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterWordList(query: String) {
        val learnedWords = learnedRepository.getAllLearnedWords()
        val filteredList = if (query.isEmpty()) {
            wordList
        } else {
            wordList.filter {
                it.word.startsWith(query, ignoreCase = true) && !learnedWords.contains(it.word)
            }
        }
        wordAdapter.updateWordList(filteredList)
    }


}