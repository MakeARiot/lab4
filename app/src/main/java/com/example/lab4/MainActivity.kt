package com.example.lab4

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getJson(): JSONObject? {
        var res: JSONObject? = null

        val log = "4707login"
        val pas = "4707pass"
        val url = "http://media.ifmo.ru/api_get_current_song.php"

        val params = RequestParams()
        params.put("login", log)
        params.put("password", pas)

        val cl = AsyncHttpClient()

        cl[url, params, object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                responseBody?.let { String(it) }?.let { Log.d("MyLog", it) }
                res = JSONObject(responseBody.toString())
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
            }
        }]

        return res
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        val btn = findViewById<Button>(R.id.button)
        val tv = findViewById<TextView>(R.id.textView)

        btn.setOnClickListener {
            val json = getJson()
            tv.text = json!!.getString("info")
        }
    }
}
