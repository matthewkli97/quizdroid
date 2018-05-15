package edu.washington.mkl.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_preference.*

class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)
        setSupportActionBar(toolbar)


        val btn_save = findViewById(R.id.btn_save) as Button
        val btn_cancel = findViewById(R.id.btn_cancel) as Button

        val et_url = findViewById(R.id.et_url) as EditText
        val et_time = findViewById(R.id.et_time) as EditText
        var url = "http://tednewardsandbox.site44.com/questions.json"
        var time = 1

        btn_save.setOnClickListener { view ->
            val nextIntent = Intent(this, Main2Activity::class.java)
            startActivity(nextIntent)
        }

        btn_cancel.setOnClickListener { view ->
            val nextIntent = Intent(this, Main2Activity::class.java)
            startActivity(nextIntent)
        }

        et_url.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                url = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        et_url.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                time = s.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

}
