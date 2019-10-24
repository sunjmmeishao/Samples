package com.example.myapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.*
import kotlinx.android.synthetic.main.activity_main.*
//import com.squareup.moshi.JsonAdapter
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.Types
import java.util.Collections
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.*
import java.io.*;
import android.R.string
import java.util.Date
import java.text.SimpleDateFormat

import com.github.salomonbrys.kotson.*
import com.google.gson.*;
import  pl.droidsonroids.gif.*;
import pl.droidsonroids.gif.GifDrawable
//import android.R
import pl.droidsonroids.gif.GifImageButton
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import sun.jvm.hotspot.utilities.IntArray
import android.widget.MediaController
import com.facebook.drawee.backends.pipeline.Fresco

data class A1(
    val l1: String? ,
    val l2: Int?
)

class MainActivity : AppCompatActivity(){

    init{
       // AppCompatActivity();
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        try {
            // 初始化 OkHttpClient
            var client =  OkHttpClient.Builder().build();
            val request = Request.Builder().get().url("https://www.baidu.com").build()
            val call = client.newCall(request)


           // var res = call.execute();
            call.enqueue( object :Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("ddddd")
                    println("Hello, world!")
                }

                override fun onResponse(call: Call, response: Response) {
                    // 此处 response 是正确的.
                  //  co.resume(response)
                    println("ddddd")
                    println("Hello, world!")
                    println(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
                }
            }

            )

            // 得到返回数据
           // Log.i(TAG, "body="+response.body().string());
        }
        catch (e:IOException) {
            e.printStackTrace();
        }

        val obj: JsonObject = jsonObject(
            "name" to "kotson",
            "creation" to Date().getTime(),
            "files" to 4
        )

        val jsonString = "{\"l1\":\"demo\",\"l2\":2}"
        val jsonArrayString = "[{\"l1\":\"demo\",\"l2\":2},{\"l1\":\"demo\",\"l2\":2}]"

        try {
            var s: A1 = Gson().fromJson(jsonString, A1::class.java)

            println("s = $s")
        } catch (e: Exception) {
            println(e)
        }


        //asset file
        var gifFromAssets :GifDrawable  = GifDrawable( getAssets(), "1.gif" );


        val gib = GifImageButton(this)
        setContentView(gib)
        gib.setImageDrawable(gifFromAssets);
       // gib.setImageResource(R.drawable.sample)
//        val mc = MediaController(this)
//        mc.setMediaPlayer(gib.drawable as GifDrawable)
//        mc.setAnchorView(gib)
//        gib.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View) {
//                mc.show()
//            }
//        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
