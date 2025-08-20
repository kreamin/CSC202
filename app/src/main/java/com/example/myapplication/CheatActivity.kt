package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answer = intent.getBooleanExtra("ANSWERTEXT", false)

        binding.Yes.setOnClickListener {
            displayAnswer()
        }

        binding.Back.setOnClickListener {
            this.finish()
        }
    }
    private fun displayAnswer() {
        binding.Answer.text = answer.toString()
    }
}