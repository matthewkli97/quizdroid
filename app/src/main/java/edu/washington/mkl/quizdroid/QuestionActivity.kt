package edu.washington.mkl.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.RadioButton
import android.widget.RadioGroup
import edu.washington.mkl.quizdroid.QuizLibrary


class QuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)



        val tv_title = findViewById(R.id.textView_question) as TextView
        val rg_answers = findViewById(R.id.radioGroup_answers) as RadioGroup

        val options = arrayOf("Option 2", "Option 3", "Option 4", "Option 5")
        for(option in options){
            val radioButton = RadioButton(this)
            radioButton.text = option
            rg_answers.addView(radioButton)
        }
    }
}
