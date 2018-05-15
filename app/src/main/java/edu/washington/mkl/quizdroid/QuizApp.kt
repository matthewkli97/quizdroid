package edu.washington.mkl.quizdroid

import android.app.Application
import android.util.Log
import android.os.Environment
import java.io.*
import org.json.JSONArray
import org.json.JSONObject

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

        quizLibrary = readLibrary()
    }

    fun updateLibrary() {
        quizLibrary = readLibrary()
    }

    fun readLibrary():MutableMap<String, Quiz> {

        var updatedLibrary: MutableMap<String, Quiz> = mutableMapOf()

        val sdcard = Environment.getExternalStorageDirectory()

        val file = File(sdcard,"questions.json")

        val inputStream: InputStream = file.inputStream()
        val inputString = inputStream.bufferedReader().use { it.readText() }

        var json:JSONArray? = null

        try {
            json = JSONArray(inputString)
        } catch (e:Exception) {
            Log.e("QuizApp", "Unable to read quiz data.")
        }

        for (i in 0..(json!!.length() - 1)) {
            val quiz:JSONObject = json.getJSONObject(i)

            val questions:JSONArray = quiz.getJSONArray("questions")

            var questionList:MutableList<Question>? = null

            for(j in 0..(questions!!.length()-1)) {

                val curr = questions.getJSONObject(j)

                val text = curr.getString("text")
                val answer = curr.getInt("answer")
                val choices = curr.getJSONArray("answers")


                val currQuestion = Question(text, arrayOf(choices.getString(0), choices.getString(1), choices.getString(2),
                        choices.getString(3)), answer)
                if(questionList == null) {
                    questionList = mutableListOf(currQuestion)
                } else {
                    questionList!!.add(currQuestion)
                }
            }

            val title = quiz.getString("title")
            val desc = quiz.getString("desc")
            val currQuiz = Quiz(title,desc, desc, questionList)

            if(i == 0) {
                updatedLibrary = mutableMapOf(title to currQuiz)
            } else {
                updatedLibrary.put(title, currQuiz)
            }

            Log.i("QuizApp", quiz.get("title").toString())
        }

        return updatedLibrary
    }

    override fun getQuestion(quizName: String, index: Int): Question? {
        val temp:Quiz? = getQuiz(quizName)

        if(index < temp!!.questions!!.size) {
            return temp!!.questions!![index]
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

        temp!!.questions!!.add(question)
    }

    override fun getTopics(): Array<String> {
        return quizLibrary.keys.toTypedArray()
    }
}

class Question (val question:String, val choices:Array<String>, val answer:Int)
class Quiz (val title:String, val shortDesc:String, val longDesc:String, val questions:MutableList<Question>?)

interface TopicRepository {
    fun getQuiz(quizName:String) : Quiz?
    fun getQuestion(quizName:String, index:Int) : Question?
    fun putQuiz(quizName: String, quiz:Quiz)
    fun putQuestion(quizName: String, question:Question)
    fun getTopics():Array<String>
}



