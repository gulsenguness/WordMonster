package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.data.repository.WordRepository
import com.gulsengunes.wordmonster.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var wordRepository: WordRepository
    private var wordList: List<Word> = emptyList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        wordRepository = WordRepository()
        loadWords()
        binding.start.setOnClickListener {
            checkUserWord()
        }
        return binding.root
    }

    private fun loadWords() {
        wordRepository.getWords { words ->
            wordList = words
            startGame()
        }
    }

    private fun startGame() {
        if (wordList.isNotEmpty()) {
            val randomWord = wordList.random()
            binding.word.text = randomWord.word
        } else {
            Toast.makeText(requireContext(), "Words could not be loaded!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkUserWord() {
        val userInput = binding.startword.text.toString().trim()
        if (userInput.isNotEmpty()) {
            val currentWord = binding.word.text.toString()
            val lastChar = currentWord.last()
            if (userInput.startsWith(lastChar, ignoreCase = true)) {
                Toast.makeText(requireContext(), "The right word!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Incorrect word, please start with the last letter!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a word!", Toast.LENGTH_SHORT).show()
        }
    }
}