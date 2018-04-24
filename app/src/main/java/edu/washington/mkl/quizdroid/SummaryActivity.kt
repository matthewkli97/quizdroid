package edu.washington.mkl.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val intent = intent

        val ql = QuizLibrary();


        val quiz = ql.Quizes.get(intent.getStringExtra("quiz"))


        val tv_title = findViewById(R.id.textView_title) as TextView
        val tv_description = findViewById(R.id.textView_description) as TextView
        //val btn_start = findViewById(R.id.btn_submit) as Button

        tv_title.setText(quiz?.title.toString())
        tv_description.setText(quiz?.description.toString())


        val btn_start = findViewById(R.id.button3) as Button

        btn_start.setOnClickListener { view ->

            val nextIntent = Intent(this, QuestionActivity::class.java)
            nextIntent.putExtra("index", 0)
            nextIntent.putExtra("quiz", intent.getStringExtra("quiz"))
            nextIntent.putExtra("correct", 0)
            startActivity(nextIntent)
        }
    }
}
