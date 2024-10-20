package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.databinding.FragmentAddWordBinding
import com.gulsengunes.wordmonster.viewmodel.WordViewModel


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private val wordViewModel: WordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWordBinding.inflate(layoutInflater)

        binding.btnaddWord.setOnClickListener {
            val word = binding.editTextWord.text.toString()
            val meaning = binding.editTextMeaning.text.toString()

            if (word.isNotEmpty() && meaning.isNotEmpty()) {
                addWordToFirebase(word, meaning)
            } else {
                Toast.makeText(requireContext(), "Please fill in both fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    private fun addWordToFirebase(word: String, meaning: String) {
        val word = binding.editTextWord.text.toString().trim()
        val meaning = binding.editTextMeaning.text.toString().trim()

        if (word.isNotEmpty() && meaning.isNotEmpty()) {
            val firestore = FirebaseFirestore.getInstance()
            val newWord = Word(word = word, meaning = meaning)

            firestore.collection("words")
                .add(newWord)
                .addOnSuccessListener {
                    wordViewModel.addWord(Word())
                    Toast.makeText(requireContext(), "Word added", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to add word", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }
}