package edu.washington.mkl.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivity : AppCompatActivity() {

    var values: Array<String> = arrayOf("Android List View", "Adapter implementation",
            "Simple List View In Android", "Create List View Android", "Android Example",
            "List View Source Code", "List View Array Adapter", "Android Example List View")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById(R.id.list) as ListView

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values)

        lv.adapter = adapter
    }
}
