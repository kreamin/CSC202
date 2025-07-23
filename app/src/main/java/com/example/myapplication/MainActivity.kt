package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface Question {
    val text: String
    fun isCorrectAnswer(answer: Boolean): Boolean
}

data class TrueFalseQuestion(
    override val text: String,
    val correctAnswer: Boolean
): Question {
    override fun isCorrectAnswer(answer: Boolean): Boolean {
        return answer == correctAnswer
    }
}

val questions: List<Question> = listOf(
    TrueFalseQuestion("Canberra is the capital of Australia", true),
    TrueFalseQuestion("this class is going to be fun", false),
    TrueFalseQuestion("Lightning never strikes the same place twice.", false),
    TrueFalseQuestion("Humans have walked on the Moon.", true),
    TrueFalseQuestion("Goldfish have a memory span of only three seconds.", false),
    TrueFalseQuestion("There are more stars in the universe than grains of sand on Earth.", true),
    TrueFalseQuestion("Bats are blind.", false),
    TrueFalseQuestion("The Great Wall of China is visible from space.", false),
    TrueFalseQuestion("Sharks are mammals.", false),
    TrueFalseQuestion("Your fingernails keep growing after you die.", false),
    TrueFalseQuestion("Water conducts electricity.", true),
    TrueFalseQuestion("Mount Everest is the tallest mountain on Earth.", true),
    TrueFalseQuestion("Bananas grow on trees.", false),
    TrueFalseQuestion("Humans share about 50% of their DNA with bananas.", true),
    TrueFalseQuestion("The human body has four lungs.", false),
    TrueFalseQuestion("Sound travels faster in water than in air.", true),
    TrueFalseQuestion("An octopus has three hearts.", true)
)

class MainActivity : AppCompatActivity() {
    lateinit var trueButton: Button
    lateinit var falseButton: Button
    lateinit var questionNum: TextView
    lateinit var questionTxt: TextView
    var currentQuestion: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionNum = findViewById(R.id.questionNum)
        questionTxt = findViewById(R.id.questionText)
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)

        trueButton.setOnClickListener{ view: View ->
            val correct = questions[currentQuestion].isCorrectAnswer(true)
            nextQuestion(correct)

        }

        falseButton.setOnClickListener{ view: View ->
            val correct = questions[currentQuestion].isCorrectAnswer(false)
            nextQuestion(correct)
        }

        showQuestion()
    }

    fun showQuestion() {
        val question = questions[currentQuestion]
        questionNum.text = "Question: ${currentQuestion + 1}"
        questionTxt.text = question.text
    }

    fun nextQuestion(correct: Boolean) {
        lifecycleScope.launch {
            if (correct) {
                questionTxt.text = "Correct!"
            } else {
                questionTxt.text = "Incorrect :("
            }
            trueButton.isEnabled = false
            falseButton.isEnabled = false

            delay(2000)

            trueButton.isEnabled = true
            falseButton.isEnabled = true
            currentQuestion++

            if (currentQuestion >= questions.size) {
                println("congrats you finished the questionnaire")
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            } else {
                showQuestion()
            }
        }
    }

}