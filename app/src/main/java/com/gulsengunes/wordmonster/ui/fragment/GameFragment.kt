package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
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
    private var score = 0
    private var timeLeft = 10
    private lateinit var timer: CountDownTimer

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
            startTimes()
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
                score++
                binding.score.text = "Score: $score"
                Toast.makeText(requireContext(), "The right word!", Toast.LENGTH_SHORT).show()
                timer.cancel()
                if (score >= 10) {
                    gameOver()
                } else {
                    startGame()
                }
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

    private fun startTimes() {
        timeLeft = 10
        binding.timerText.text = "Time Left:$timeLeft"

        timer = object : CountDownTimer(timeLeft * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft--
                binding.timerText.text = "Time Left: $timeLeft"
            }

            override fun onFinish() {
                gameOver()
            }

        }.start()
    }

    private fun gameOver() {
        Toast.makeText(requireContext(), "Game Over! Your score was: $score", Toast.LENGTH_SHORT)
            .show()
        score = 0
        binding.score.text = "Score: $score"
        binding.word.text = "Game Over!"
        timer.cancel()
    }

}