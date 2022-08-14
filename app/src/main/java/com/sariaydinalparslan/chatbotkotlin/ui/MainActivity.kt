package com.sariaydinalparslan.chatbotkotlin.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sariaydinalparslan.chatbotkotlin.R
import com.sariaydinalparslan.chatbotkotlin.utils.BotResponse
import com.sariaydinalparslan.chatbotkotlin.utils.Constants.OPEN_GOOGLE
import com.sariaydinalparslan.chatbotkotlin.utils.Constants.OPEN_SEARCH
import com.sariaydinalparslan.chatbotkotlin.utils.Constants.RECEIVE_ID
import com.sariaydinalparslan.chatbotkotlin.utils.Constants.SEND_ID
import com.sariaydinalparslan.chatbotkotlin.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : MessagingAdapter
    private val botList = listOf("Peter","Francesca","Luigi","Igor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        clickEvents()

        val random = (0..3).random()
        customMessage("Hello! Today You are speaking with ${botList[random]}, how may ı help")

    }
    private fun clickEvents(){
        btn_send.setOnClickListener {
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }

    private fun recyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }
    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp= Time.timeStamp()

        if (message.isNotEmpty()){
            et_message.setText("")
            adapter.insertMessage(com.sariaydinalparslan.chatbotkotlin.data.Message(message, SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount -1 )
            botResponse(message)
        }
    }
    private fun botResponse(message : String){
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponse(message)
                adapter.insertMessage(com.sariaydinalparslan.chatbotkotlin.data.Message(response, RECEIVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)

                when (response){
                    OPEN_GOOGLE ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm : String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message: String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(com.sariaydinalparslan.chatbotkotlin.data.Message(message.toString(),
                    RECEIVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

}