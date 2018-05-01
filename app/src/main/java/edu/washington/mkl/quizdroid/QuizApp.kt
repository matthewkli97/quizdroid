package edu.washington.mkl.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application(), TopicRepository {

    companion object {
        lateinit var instance: QuizApp
            private set
        lateinit var quizLibrary: MutableMap<String, Quiz>
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Log.i("QuizApp", "Hit Me")

        val q1_1 = Question("1+1", arrayOf("2","10", "11", "16"), 0)
        val q1_2 = Question("10/10", arrayOf("1","10", "15", "16"), 0)
        val q1_3 = Question("11 * 22", arrayOf("242","0", "11", "16"), 0)

        val q2_1 = Question("Which of the following is a physical quantity that has a magnitude but no direction?",
                arrayOf("Vector","Frame of reference", "Resultant", "Scalar"), 3)
        val q2_2 = Question("Multiplying or dividing vectors by scalars results in",
                arrayOf("Vectors if multiplied or scalars if divided","Scalars if multiplied scalars", "Scalars", "Vectors"), 3)
        val q2_3 = Question("Identify the following quantities as scalar or vector: the mass of an object, the number of leaves on a tree, wind velocity.",
                arrayOf("Vector, scalar, scalar","Vector, scalar, vector", "Scalar, scalar, vector", "Scalar, vector, vector"), 2)
        val q2_4 = Question("Which of the following is an example of a vector quantity?",
                arrayOf("Temperature","Velocity", "Volume", "Mass"), 1)

        val q3_1 = Question("'Guardians of the Galaxy' director James Gunn confirmed via Twitter that the Orb holds which Infinity Stone?",
                arrayOf("The space stone","The mind stone", "The power stone", "The time stone"), 2)
        val q3_2 = Question("What line of dialogue reveals Senator Stern to be a villain in 'Captain America: The Winter Soldier'?",
                arrayOf("Death to Shield","I am issuing a direct order to kill Captain America on sight", "Hail Hydra", "Cut one off. Two shall take its place."),
                2)
        val q3_3 = Question("What's Abomination's real name?", arrayOf("Brock Rumlow","Thunderbolt Ross", "Georges Batroc", "Emil Blonsky"), 3)

        val Quiz1 = Quiz("Math", "Short Math Desc","Long Math Desc", mutableListOf(q1_1,q1_2,q1_3));
        val Quiz2 = Quiz("Physics", "Short Physic Desc","Long Physic Desc", mutableListOf(q2_1,q2_2,q2_3,q2_4));
        val Quiz3 = Quiz("Marvel", "Short Marvel Desc","Long Marvel Desc", mutableListOf(q3_1,q3_2,q3_3));

        quizLibrary = mutableMapOf("Math" to Quiz1, "Physics" to Quiz2, "Marvel" to Quiz3)
    }

    override fun getQuestion(quizName: String, index: Int): Question? {
        val temp:Quiz? = getQuiz(quizName)

        if(index < temp!!.questions.size) {
            return temp!!.questions[index]
        }
        return null
    }

    override fun getQuiz(quizName:String): Quiz? {
        if (quizLibrary.containsKey(quizName)) {
            return quizLibrary.get(quizName)
        } else {
            throw RuntimeException("QuizLibrary does not contain quiz: " + quizName)
        }
        return null
    }

    override fun putQuiz(quizName: String, quiz: Quiz) {
        quizLibrary.put(quizName, quiz)
    }

    override fun putQuestion(quizName: String, question: Question) {
        val temp:Quiz? = getQuiz(quizName)

        temp!!.questions.add(question)
    }
}

class Question (val question:String, val choices:Array<String>, val answer:Int)
class Quiz (val title:String, val shortDesc:String, val longDesc:String, val questions:MutableList<Question>)

interface TopicRepository {
    fun getQuiz(quizName:String) : Quiz?
    fun getQuestion(quizName:String, index:Int) : Question?
    fun putQuiz(quizName: String, quiz:Quiz)
    fun putQuestion(quizName: String, question:Question)
}

