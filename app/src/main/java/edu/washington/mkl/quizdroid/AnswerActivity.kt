package edu.washington.mkl.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val index = intent.getIntExtra("index", 0)
        val ql = QuizLibrary();
        val quiz = ql.Quizes.get(intent.getStringExtra("quiz"))
        val question:Question = quiz!!.questions[index]

        val tv_user = findViewById(R.id.textView_userChoice) as TextView
        val tv_answer = findViewById(R.id.textView_answer) as TextView
        val tv_title = findViewById(R.id.textView_title) as TextView
        val tv_progress = findViewById(R.id.textView_progress) as TextView
        val btn_next = findViewById(R.id.btn_next) as Button

        val userSelection = intent.getStringExtra("selected")

        val correct = if (userSelection == question.answer) intent.getIntExtra("correct", 0) + 1 else intent.getIntExtra("correct", 0)

        tv_title.setText(question.question)
        tv_answer.setText(question.answer)
        tv_user.setText(intent.getStringExtra("selected"))
        tv_progress.setText(getString(R.string.progress_message, correct, quiz.questions.size))

        if(index + 1 < quiz.questions.size) {
            btn_next.setOnClickListener { view ->
                val nextIntent = Intent(this, QuestionActivity::class.java)
                nextIntent.putExtra("index", intent.getIntExtra("index", 0) + 1)
                nextIntent.putExtra("quiz", intent.getStringExtra("quiz"))
                nextIntent.putExtra("correct", correct)
                startActivity(nextIntent)
            }
        } else {
            btn_next.setText("Finish")
            btn_next.setOnClickListener { view ->
                val nextIntent = Intent(this, MainActivity::class.java)
                startActivity(nextIntent)
            }
        }

    }
}
