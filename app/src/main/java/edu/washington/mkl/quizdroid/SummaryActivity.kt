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

        val tv = findViewById(R.id.textView) as TextView

        Log.i("QuestionActivity", intent.getStringExtra("test"))

        tv.setText(intent.getStringExtra("test"))

        val tv_title = findViewById(R.id.textView_title) as TextView
        val tv_description = findViewById(R.id.textView_description) as TextView
        val btn_start = findViewById(R.id.btn_submit) as Button


        btn_start.setOnClickListener { view ->

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }


    }
}
