package com.example.myapplication

import QuizViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    var currentIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.Previous.setOnClickListener{ view: View ->
            if (currentIndex == 0) {
                println("cant do that")
            } else {
                quizViewModel.moveToPrevious()
                showQuestion()
            }
        }

        binding.Next.setOnClickListener{ view: View ->
            if (currentIndex + 1 == quizViewModel.questions.size) {
                Log.w("cant do that", "cant do that")
            } else {
                quizViewModel.moveToNext()
                showQuestion()
            }
        }

        binding.trueButton.setOnClickListener{ view: View ->
            val correct = quizViewModel.questions[currentIndex].isCorrectAnswer(true)
            nextQuestion(correct)

        }

        binding.falseButton.setOnClickListener{ view: View ->
            val correct = quizViewModel.questions[currentIndex].isCorrectAnswer(false)
            nextQuestion(correct)
        }

        showQuestion()
    }

    fun showQuestion() {
        val question = quizViewModel.questions[currentIndex]
        binding.questionNum.text = "Question: ${currentIndex + 1}"
        binding.questionText.text = question.text
        if (quizViewModel.questions[currentIndex].answered) {
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        } else {
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }
    }

    fun nextQuestion(correct: Boolean) {
        lifecycleScope.launch {
            if (correct) {
                binding.questionText.text = "Correct!"
            } else {
                binding.questionText.text = "Incorrect :("
            }
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false

            delay(2000)

            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
            currentIndex++

            if (currentIndex >= quizViewModel.questions.size) {
                println("congrats you finished the questionnaire")
                binding.trueButton.isEnabled = false
                binding.falseButton.isEnabled = false
            } else {
                showQuestion()
            }
        }
    }

}