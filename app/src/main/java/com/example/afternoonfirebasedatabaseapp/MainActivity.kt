package com.example.afternoonfirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIdNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEditName)
        edtEmail = findViewById(R.id.mEditEmail)
        edtIdNumber= findViewById(R.id.mEditNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("saving")
        progressDialog.setMessage("please wait...")
        btnSave.setOnClickListener {
              // Receive data from the user
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
            // Check if the user is submitting empty fields
            if (name.isEmpty()){
                edtName.setError("Please fill this field")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("Please fill this field")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()){
                edtIdNumber.setError("Please fill this field")
                edtIdNumber.requestFocus()
            }else{
                // Proceed to save data
                var user = User(name, email, idNumber, id)
                // Create a reference to FirebaseDatabase
                var ref = FirebaseDatabase.getInstance().
                        getReference().child("Users/"+id)
                // Start saving
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener{
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "User saved succesfully",
                                        Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "User saving failed",
                                       Toast.LENGTH_LONG).show()
                    }
                }


            }
        }
        btnView.setOnClickListener {
            var intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }

    }
}