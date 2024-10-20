package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.LearnedRepository
import com.gulsengunes.wordmonster.data.repository.WordRepository
import com.gulsengunes.wordmonster.databinding.FragmentLearnedBinding
import com.gulsengunes.wordmonster.ui.adapter.LearnedAdapter


class LearnedFragment : Fragment() {
    private lateinit var binding: FragmentLearnedBinding
    private lateinit var learnedRepository: LearnedRepository
    private lateinit var learnedAdapter: LearnedAdapter
    private lateinit var wordRepository: WordRepository
    private var learnedWords: Set<String> = emptySet()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLearnedBinding.inflate(layoutInflater)
        learnedRepository = LearnedRepository(requireContext())
        wordRepository = WordRepository()
        setupRecyclerView()
        loadLearnedWords()
        return binding.root
    }

    private fun setupRecyclerView() {
        learnedAdapter = LearnedAdapter(emptyList(), learnedRepository) { word ->
            learnedRepository.removeLearnedWord(word.word)
            loadLearnedWords()
        }
        binding.recyclerLearned.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerLearned.adapter = learnedAdapter
    }

    private fun loadLearnedWords() {
        learnedWords = learnedRepository.getAllLearnedWords()
        val wordList = mutableListOf<Word>()

        for (wordString in learnedWords) {
            wordRepository.getMeaningForWord(wordString) { meaning ->
                wordList.add(Word(id = "", word = wordString.trim(), meaning = meaning ?: "Anlam Bulunamadı", favorite = false))
                if (wordList.size == learnedWords.size) {
                    learnedAdapter.updateLearnedWords(wordList)
                }
            }
        }
    }
}