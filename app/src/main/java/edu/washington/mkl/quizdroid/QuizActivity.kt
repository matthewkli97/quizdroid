package edu.washington.mkl.quizdroid

import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.fragment_summary.*

class QuizActivity : AppCompatActivity() , SummaryFragment.OnFragmentInteractionListener, QuestionFragment.OnFragmentInteractionListener,
        AnswerFragment.OnFragmentInteractionListener {

    var summaryFragment:SummaryFragment? = null
    var questionFragment:QuestionFragment? = null
    var answerFragment:AnswerFragment? = null
    var currFragment:Fragment? = null
    var quiz:Quiz? = null;
    var correct = 0
    var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val intent = intent
        val quizName = intent.getStringExtra("quizName")
        quiz = QuizApp.instance.getQuiz(intent.getStringExtra("quizName"))
        val quizSummary = quiz?.longDesc.toString()

        summaryFragment = SummaryFragment.newInstance(intent.getStringExtra("quizName"), quizSummary)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_layout, SummaryFragment.newInstance(intent.getStringExtra("quizName"), quizSummary), "SummaryFrag")
                    .commit()
        }
    }

    override fun onSummaryButtonInteraction() {
        val question = quiz!!.questions!![0]
        questionFragment = QuestionFragment.newInstance(question.question, question.choices[0], question.choices[1], question.choices[2], question.choices[3])

        val currFrag = supportFragmentManager.findFragmentByTag("SummaryFrag")
        supportFragmentManager
                .beginTransaction()
                .remove(currFrag)
                .add(R.id.root_layout, questionFragment, "QuestionFrag")
                .commit()
    }

    override fun onQuestionButtonInteraction(selected:Int) {
        val question = quiz!!.questions!!.get(current)
        if(question.choices[selected] == question.choices[question.answer].toString()) {
            correct++
        }

        answerFragment = AnswerFragment.newInstance(question.question, question.choices[question.answer - 1].toString(), question.choices[selected], correct, quiz!!.questions!!.size, current + 1 >= quiz!!.questions!!.size)
        val currFrag = supportFragmentManager.findFragmentByTag("QuestionFrag")

        supportFragmentManager
                .beginTransaction()
                .remove(currFrag)
                .add(R.id.root_layout, answerFragment, "AnswerFrag")
                .commit()
    }

    override fun onAnswerButtonInteraction() {
        val currFrag = supportFragmentManager.findFragmentByTag("AnswerFrag")

        current++
        if(current < quiz!!.questions!!.size) {
            val question = quiz!!.questions!![current]
            questionFragment = QuestionFragment.newInstance(question.question, question.choices[0], question.choices[1], question.choices[2], question.choices[3])

            supportFragmentManager
                    .beginTransaction()
                    .remove(currFrag)
                    .add(R.id.root_layout, questionFragment, "QuestionFrag")
                    .commit()

        } else {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
    }
}

