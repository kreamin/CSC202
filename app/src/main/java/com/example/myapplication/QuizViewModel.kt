import androidx.lifecycle.ViewModel

interface Question {
    val text: String
    fun isCorrectAnswer(answer: Boolean): Boolean
    val answered: Boolean
    val correctAnswer: Boolean
}

data class TrueFalseQuestion(
    override val text: String,
    override val correctAnswer: Boolean,
    override var answered: Boolean = false
): Question {
    override fun isCorrectAnswer(answer: Boolean): Boolean {
        answered = true
        return answer == correctAnswer
    }
}

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
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

    private var currentIndex: Int = 0
    val currentQuestionAnswer: Boolean
        get() = questions[currentIndex].correctAnswer
    val currentQuestionText: String
        get() = questions[currentIndex].text
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questions.size
    }
    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questions.size
    }
}
