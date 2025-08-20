package com.example.myapplication

import QuizViewModel
import android.content.Intent
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

    private var cheated = false

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

        binding.CheatButton.setOnClickListener{ view: View ->
            if (!cheated) {
                cheated = true
            }
            val intentToCheat = Intent(this, CheatActivity::class.java)
            intentToCheat.putExtra("ANSWERTEXT", quizViewModel.currentQuestionAnswer)
            startActivity(intentToCheat)
        }

        binding.Previous.setOnClickListener{ view: View ->
            if (quizViewModel.currentIndex == 0) {
                println("cant do that")
            } else {
                quizViewModel.moveToPrevious()
                showQuestion()
            }
        }

        binding.Next.setOnClickListener{ view: View ->
            if (quizViewModel.currentIndex + 1 == quizViewModel.questions.size) {
                Log.w("cant do that", "cant do that")
            } else {
                quizViewModel.moveToNext()
                showQuestion()
            }
        }

        binding.trueButton.setOnClickListener{ view: View ->
            val correct = quizViewModel.questions[quizViewModel.currentIndex].isCorrectAnswer(true)
            nextQuestion(correct)

        }

        binding.falseButton.setOnClickListener{ view: View ->
            val correct = quizViewModel.questions[quizViewModel.currentIndex].isCorrectAnswer(false)
            nextQuestion(correct)
        }

        showQuestion()
    }

    fun showQuestion() {
        val question = quizViewModel.questions[quizViewModel.currentIndex]
        binding.questionNum.text = "Question: ${quizViewModel.currentIndex + 1}"
        binding.questionText.text = question.text
        if (quizViewModel.questions[quizViewModel.currentIndex].answered) {
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
            quizViewModel.moveToNext()

            if (quizViewModel.currentIndex >= quizViewModel.questions.size) {
                if (!cheated){
                    println("congrats you finished the questionnaire")
                    binding.trueButton.isEnabled = false
                    binding.falseButton.isEnabled = false
                } else {
                    println("booooo you cheated the questionnaire")
                    binding.trueButton.isEnabled = false
                    binding.falseButton.isEnabled = false
                }
            } else {
                showQuestion()
            }
        }
    }

}