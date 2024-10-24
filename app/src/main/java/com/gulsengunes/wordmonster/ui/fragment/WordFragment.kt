package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import java.util.Locale

class WordFragment : Fragment(),TextToSpeech.OnInitListener {
    private lateinit var binding: FragmentWordBinding
    private lateinit var wordAdapter: WordAdapter
    private lateinit var repository: WordRepository
    private lateinit var learnedRepository: LearnedRepository
    private lateinit var favoriteRepository: FavoriteRepository
    private var wordList: List<Word> = emptyList()
    private lateinit var tts: TextToSpeech


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWordBinding.inflate(layoutInflater)
        repository = WordRepository()
        favoriteRepository = FavoriteRepository(requireContext())
        learnedRepository = LearnedRepository(requireContext())
        tts = TextToSpeech(context, this)
        setupRecyclerView()
        loadWords()
        setupSearchBar()
        return binding.root
    }


    private fun setupRecyclerView() {
        wordAdapter = WordAdapter(wordList, learnedRepository, favoriteRepository,tts)
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

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Dil desteklenmiyor.")
            }
        } else {
            Log.e("TTS", "Text-to-Speech başlatılamadı.")
        }
    }


}