package edu.washington.mkl.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.AdapterView
import android.util.Log
import android.content.Intent




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById(R.id.list) as ListView

        val ql = QuizLibrary();
        val quizes:Array<String> = ql.Quizes.keys.toTypedArray()

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, quizes);

        lv.adapter = adapter

        lv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            // do something...
            Log.i("My Activity", position.toString())

            val intent = Intent(this, QuizActivity::class.java)

            intent.putExtra("quizName", quizes[position])

            startActivity(intent)
        }
    }
}
