package com.gulsengunes.wordmonster.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.gulsengunes.wordmonster.data.model.Word
import com.gulsengunes.wordmonster.databinding.FragmentAddWordBinding


class AddWordFragment : Fragment() {
    private lateinit var binding: FragmentAddWordBinding
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWordBinding.inflate(layoutInflater)

        database = FirebaseDatabase.getInstance().getReference("words")
        binding.btnaddWord.setOnClickListener {
            addWord()
        }
        return binding.root
    }

    private fun addWord() {
        val word = binding.editTextWord.text.toString().trim()
        val meaning = binding.editTextMeaning.text.toString().trim()

        if (word.isNotEmpty() && meaning.isNotEmpty()) {
            val id = database.push().key
            val newWord = Word(id ?: "", word, meaning)
            database.child(id!!).setValue(newWord).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Word added!", Toast.LENGTH_SHORT).show()

                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Mistake: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

}