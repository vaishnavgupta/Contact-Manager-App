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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {
    companion object{
        const val key="com.example.contactmanagerapplication.LogInActivity.key"
    }
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        val uid=findViewById<TextInputEditText>(R.id.etLogUid)
        val pass=findViewById<TextInputEditText>(R.id.etLogPass)
        val btnLog=findViewById<Button>(R.id.btnLogin)
        val tvsignup=findViewById<TextView>(R.id.tVsignup)

        tvsignup.setOnClickListener {
            intent= Intent(applicationContext,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLog.setOnClickListener {
            val pLogUid=uid.text.toString()
            val pLogPass=pass.text.toString()
            if(pLogUid.isEmpty()){
                Toast.makeText(this,"Please Enter User ID",Toast.LENGTH_SHORT).show()
            }
            else if (pLogPass.isEmpty()){
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show()
            }
            else{
                checkData(pLogUid,pLogPass)
            }
        }
    }

    private fun checkData(pLogUid: String, pLogPass: String) {
        database=FirebaseDatabase.getInstance().getReference("Users")
        database.child(pLogUid).get().addOnSuccessListener {
            if(it.exists()){
                if(it.child("password").value==pLogPass){
                    Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_LONG).show()
                    intent=Intent(this,HomeActivity::class.java)
                    intent.putExtra(key,pLogUid)
                    startActivity(intent)

                }
                else{
                    Toast.makeText(this,"Incorrect Password",Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this,"User Does Not Exists",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Technical Error Please Try Again",Toast.LENGTH_SHORT).show()
        }
    }
}