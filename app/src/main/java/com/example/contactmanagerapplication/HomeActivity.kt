package com.example.contactmanagerapplication

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeActivity : AppCompatActivity() {
    lateinit var database:DatabaseReference
    lateinit var alertDialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)


        //var for uid  //from login activity
        val pUid=intent.getStringExtra(LogInActivity.key)
        //home activity variables
        val cName=findViewById<TextInputEditText>(R.id.etHomeName)
        val cMail=findViewById<TextInputEditText>(R.id.etHomeMail)
        val cPhone=findViewById<TextInputEditText>(R.id.etHomeNo)
        val btnAdd=findViewById<Button>(R.id.btnAdd)
        //database
        btnAdd.setOnClickListener {
            val i=pUid.toString()
            val n=cName.text.toString()
            val m=cMail.text.toString()
            val p=cPhone.text.toString()
            if(n.isEmpty()||m.isEmpty()||p.isEmpty()){
                Toast.makeText(this,"Enter All Details",Toast.LENGTH_SHORT).show()
            }
            else{
                addContact(i,n,m,p)
            }
        }

        //alert dialog view
        alertDialog=Dialog(this)
        alertDialog.setContentView(R.layout.alert_dialog)
        alertDialog.window?.setBackgroundDrawable(getDrawable(R.drawable.alert_background))    //to set alert dialog background
        //accesing button inside dialog "OK"
        val alertOk=alertDialog.findViewById<Button>(R.id.btnAlert)
        alertOk.setOnClickListener {
            alertDialog.dismiss()
        }


    }

    private fun addContact(i: String, n: String, m: String, p: String) {
        database=FirebaseDatabase.getInstance().getReference("Contacts")
        val cont=contact(m,n,p)
        database.child(i).child(p).setValue(cont).addOnSuccessListener {
            //opening customized alert dialog
            alertDialog.show()

        }.addOnFailureListener {
            Toast.makeText(this,"Technical Error Please Try Again",Toast.LENGTH_SHORT).show()
        }
    }
}