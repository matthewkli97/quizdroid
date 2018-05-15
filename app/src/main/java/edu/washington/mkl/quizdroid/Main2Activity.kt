package edu.washington.mkl.quizdroid

import android.Manifest
import android.app.AlertDialog
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*

import kotlinx.android.synthetic.main.activity_main2.*
import org.json.JSONArray
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Main2Activity : AppCompatActivity() {

    companion object {
        var url = "http://tednewardsandbox.site44.com/questions.json"
        var time:Int = 1
        var downloadIntent:Intent? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        if(checkAirplaneMode()) {
            val builder = AlertDialog.Builder(this@Main2Activity)

            // Set the alert dialog title
            builder.setTitle("Airplane Mode")

            // Display a message on alert dialog
            builder.setMessage("Looks like airplane mode is on, some features may not work properly. Do you want to turn airplane mode off?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES"){dialog, which ->
                val intent = Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                startActivity(intent)
            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("No"){dialog,which ->

            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        } else if(!checkNetworkConnection()) {

            Toast.makeText(this@Main2Activity, "No Internet Connection.", Toast.LENGTH_SHORT).show()

        } else {
            if (downloadIntent == null) {
                startDownloadIntent()
            } else {
                updateDownloadIntent()
            }

            val mTopToolbar = findViewById(R.id.toolbar) as Toolbar;
            setSupportActionBar(mTopToolbar);

            setSupportActionBar(toolbar)

            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1);

            val lv = findViewById(R.id.list) as ListView

            val quizes: Array<String> = QuizApp.instance.getTopics()

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

    fun updateDownloadIntent() {
        val currTime = intent.getIntExtra("time", -1)
        val currUrl = intent.getStringExtra("url")

        Log.i("MainActivity", "currTime: " + currTime)
        Log.i("MainActivity", "currUrl: " + currUrl)

        if((currUrl != null && currUrl != url) || (currTime != time && currTime != -1)) {
            url = currUrl
            time = currTime
            stopDownloadIntent()
            startDownloadIntent()
        }
    }

    fun startDownloadIntent() {
        downloadIntent = Intent(this, MyService::class.java)
        downloadIntent?.putExtra("url", url)
        downloadIntent?.putExtra("time", time)
        startService(downloadIntent)
    }

    fun stopDownloadIntent() {
        Log.i("Main2Activity", "Service Stopped")
        stopService(downloadIntent)
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
                    Toast.makeText(this@Main2Activity, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Code adapted from: https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.Menu -> {
                val prefIntent = Intent(this, PreferenceActivity::class.java)
                prefIntent.putExtra("url", url)
                prefIntent.putExtra("time", time)
                startActivity(prefIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun checkAirplaneMode(): Boolean {
        return Settings.Global.getInt(this.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON, 0) !== 0
    }

    fun checkNetworkConnection() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}


class MyService : IntentService("MyService") {

    var stopped:Boolean
    var mHandler: Handler

    init {
        mHandler = Handler();
        stopped = false

    }

    override fun onHandleIntent(intent: Intent?) {
        Log.i("MyService", "Thread Start")
        val url = intent!!.getStringExtra("url")
        val time = intent!!.getIntExtra("time", 1)

        while(!stopped) {
            try {
                Log.i("Thread", "hello")

                val connection = URL(url).openConnection() as HttpURLConnection

                var input : String = ""

                try {
                    connection.connect()
                    input = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
                }
                catch (e : IOException){
                    mHandler.post(DisplayToast(this, "Download Failed"))
                }
                finally {
                    connection.disconnect()
                }

                val jsonInput = JSONArray(input)

                val sdcard : File = Environment.getExternalStorageDirectory()

                val jsonFile : File = File(sdcard, "questions.json")

                // Attempt to overwrite existing question.json file
                try {
                    val fos : FileOutputStream = FileOutputStream(jsonFile)
                    val pw : PrintWriter = PrintWriter(fos)

                    pw.print(jsonInput)

                    pw.flush()
                    pw.close()
                    fos.close()

                    // Update Library
                    QuizApp.instance.updateLibrary()
                }
                catch (e : Exception){
                    mHandler.post(DisplayToast(this, "Download Failed"))
                }
            } catch (e: Exception) {
                mHandler.post(DisplayToast(this, "Download Failed"))
            }
            Thread.sleep((60000 * time).toLong())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopped = true
        Log.i("MyService", "Thread Destroy")
    }
}

class DisplayToast(private val mContext: Context, internal var mText: String) : Runnable {

    override fun run() {
        Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show()
    }
}
