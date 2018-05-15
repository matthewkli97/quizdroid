package edu.washington.mkl.quizdroid

import android.Manifest
import android.app.DownloadManager
import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*

import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_summary.*
import org.json.JSONArray
import java.io.*
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Main2Activity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val downloadIntent = Intent(this, MyService::class.java)

        val mTopToolbar = findViewById(R.id.toolbar) as Toolbar;
        setSupportActionBar(mTopToolbar);

        setSupportActionBar(toolbar)

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1);


        val intent = intent

        var url = intent.getStringExtra("url")
        val time = intent.getIntExtra("time", 1)

        url = "http://tednewardsandbox.site44.com/questions.json"
        downloadIntent.putExtra("url", url)
        downloadIntent.putExtra("time", time)

        startService(downloadIntent)

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
                val intent = Intent(this, PreferenceActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}


class MyService : IntentService("MyService") {

    var stopped:Boolean
    var mHandler: Handler
    var reference:Long = -1

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

                val jsonFile : File = File(sdcard, "questions2.json")


                try {
                    val fos : FileOutputStream = FileOutputStream(jsonFile)
                    val pw : PrintWriter = PrintWriter(fos)

                    pw.print(jsonInput)

                    pw.flush()
                    pw.close()
                    fos.close()
                }
                catch (e : FileNotFoundException){
                    mHandler.post(DisplayToast(this, "Download Failed"))
                    e.printStackTrace()
                }
                catch (e : IOException){
                    mHandler.post(DisplayToast(this, "Download Failed"))
                    e.printStackTrace()
                }
                catch (e : Exception){
                    mHandler.post(DisplayToast(this, "Download Failed"))
                }

                Thread.sleep((60000 * time).toLong())
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            } catch (e: Exception) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopped = true
        Log.i("MyService", "Thread Destroy")
    }

    private val downloadReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            //check if the broadcast message is for our Enqueued download
            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (referenceId == reference) {

            }
        }
    }
}

class DisplayToast(private val mContext: Context, internal var mText: String) : Runnable {

    override fun run() {
        Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show()
    }
}
