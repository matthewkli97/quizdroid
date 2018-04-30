package edu.washington.mkl.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import edu.washington.mkl.quizdroid.QuizLibrary


class QuestionActivity : AppCompatActivity() {

    var selected = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)


        val tv_title = findViewById(R.id.textView_question) as TextView
        val btn_submit = findViewById(R.id.btn_submit) as Button
        val layout = findViewById(R.id.linearLayout) as LinearLayout

        updateButton()

        val quiz = QuizApp.instance.getQuiz(intent.getStringExtra("quiz"))
        val question:Question = quiz!!.questions[intent.getIntExtra("index", 0)]

        val rg_answers = RadioGroup(applicationContext)
        rg_answers.orientation = RadioGroup.VERTICAL

        tv_title.setText(question.question)

        val options = question.choices

        for(option in options){
            val radioButton = RadioButton(this)
            radioButton.text = option
            rg_answers.addView(radioButton)
        }

        layout.addView(rg_answers)

        rg_answers.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            selected  = rg_answers.indexOfChild(findViewById(rg_answers.getCheckedRadioButtonId()))
            updateButton()
        })

        btn_submit.setOnClickListener { view ->
            val nextIntent = Intent(this, AnswerActivity::class.java)
            nextIntent.putExtra("index", intent.getIntExtra("index", 0))
            nextIntent.putExtra("quiz", intent.getStringExtra("quiz"))
            nextIntent.putExtra("selected", options[selected])
            nextIntent.putExtra("correct", intent.getIntExtra("correct", 0))
            startActivity(nextIntent)
        }
    }

    fun updateButton() {
        val btn_submit = findViewById(R.id.btn_submit) as Button
        btn_submit.visibility = if(selected != -1) View.VISIBLE else View.INVISIBLE
    }
}
