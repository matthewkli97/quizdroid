package edu.washington.mkl.quizdroid

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.AdapterView
import android.util.Log
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1);

        val lv = findViewById(R.id.list) as ListView

        val quizes:Array<String> = QuizApp.instance.getTopics()

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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this@MainActivity, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }
}
