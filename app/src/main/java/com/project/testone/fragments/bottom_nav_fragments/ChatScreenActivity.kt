package com.project.testone.fragments.bottom_nav_fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.testone.R
import com.project.testone.adapters.ChatAdapter
import com.project.testone.dataClass.Chat
import kotlinx.android.synthetic.main.activity_chat_screen.*

class ChatScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var chatList = ArrayList<String>()
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        userName = intent.getStringExtra("userName").toString()
//        tvUserName.text = userName

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.title = userName
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#E7F0E7")))
        window.statusBarColor = Color.parseColor("#247F28")

        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()


        chatRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        auth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference =
            firebaseDatabase.getReference("Users").child(auth.currentUser!!.uid).child("chats")



        btnSendMessage.setOnClickListener {
            var message = etMessage.text.toString()

            if (message.isEmpty()) {
                Toast.makeText(applicationContext, "Message is empty", Toast.LENGTH_SHORT).show()
            } else {
                sendMessage(message)
                chatList.add(message)
                val chatAdapter = ChatAdapter(this@ChatScreenActivity, chatList)
                chatRecyclerView.adapter = chatAdapter
                etMessage.setText("")
//                getData()
            }
        }
//        getData()
    }

    private fun sendMessage(message: String) {

        var hashMap: HashMap<String, String> = HashMap()

        hashMap["message"] = message


        databaseReference.child("message").push().setValue(message)
    }

    private fun getData() {

        var databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
                .child("chats").child("message")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()

                val chat = snapshot.getValue(Chat::class.java)

                chatList.add(chat.toString())
                Log.d("checkingList", chat.toString())


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}