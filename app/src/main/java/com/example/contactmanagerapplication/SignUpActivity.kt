package com.example.contactmanagerapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val name=findViewById<TextInputEditText>(R.id.etName)
        val email=findViewById<TextInputEditText>(R.id.etMail)
        val uid=findViewById<TextInputEditText>(R.id.etUID)
        val password=findViewById<TextInputEditText>(R.id.etPass)
        val bsignup=findViewById<Button>(R.id.btnSignUp)
        val tvLogin=findViewById<TextView>(R.id.tVlogin)

        tvLogin.setOnClickListener {
            intent=Intent(applicationContext,LogInActivity::class.java)
            startActivity(intent)
        }

        bsignup.setOnClickListener {
            val pname=name.text.toString()
            val pmail=email.text.toString()
            val puid=uid.text.toString()
            val ppassword=password.text.toString()

            val user=User(pname,pmail,puid,ppassword)

            database=FirebaseDatabase.getInstance().getReference("Users")

            database.child(puid).setValue(user).addOnSuccessListener {
                name.text?.clear()
                email.text?.clear()
                uid.text?.clear()
                password.text?.clear()
                Toast.makeText(this,"User Added Successfully Login to Continue",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this,"Technical Error Please Try Again",Toast.LENGTH_SHORT).show()

            }


        }

    }
}