package org.wit.recyclebeev1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.recyclebeev1.databinding.ActivityBusRegisterBinding
import org.wit.recyclebeev1.databinding.ActivityStoreListInputBinding
import org.wit.recyclebeev1.fragments.Store

class StoreListInput : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityStoreListInputBinding

    //actionbar
    private lateinit var actionBar: ActionBar

    //progressdialog
    private lateinit var progressDialog: ProgressDialog

    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth
    private var storeEmail = ""
    private var storeName =""
    private var storeAddress=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_store_list_input)
        binding = ActivityStoreListInputBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_bus_register)
        //config actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Store submission"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //firebase auth init
        firebaseAuth = FirebaseAuth.getInstance()


        binding.submitStoreBtn.setOnClickListener {
            validateData2()
            pushToDb2() //this is the error
            startActivity(Intent(this, HomeActivity::class.java))

        }



    }

    private fun validateData2() {
        //get data
        storeEmail = binding.etStoreEmail.text.toString().trim()
        storeName = binding.etStoreName.text.toString().trim()
        storeAddress = binding.etStoreAdd.text.toString().trim()



        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(storeEmail).matches()) {
            //invalid email format
            binding.etStoreEmail.error = "Invalid email format"
        }  else if (TextUtils.isEmpty(storeName)) {
            //name isnt entered
            binding.etStoreName.error = "Please enter name"
        } else if (TextUtils.isEmpty(storeAddress)) {
            //address isnt entered
            binding.etStoreAdd.error = "Please enter address"
        } else {
            //data is valid, continue signup
            //firebaseSignUp()
            //pushToDB2()
        }
    }

//    private fun firebaseSignUp() {
//        //show progress
//        progressDialog.show()
//
//        //create account
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//            .addOnSuccessListener {
//                //signup success
//                progressDialog.dismiss()
//                var database = FirebaseDatabase.getInstance().reference //new
//                //get current user
//                val firebaseUser = firebaseAuth.currentUser
//                val email = firebaseUser!!.email
//                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
//
//                //open profile
//                startActivity(Intent(this, HomeActivity::class.java))
//                database.setValue(User(username, email,password)) //new
//                finish()
//
//            }
//            .addOnFailureListener { e ->
//                //signup failed
//                progressDialog.dismiss()
//                Toast.makeText(this, "Signup failed due to ${e.message}", Toast.LENGTH_SHORT).show()
//
//            }
//    }

    fun pushToDb2() {
        val database2 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference

        val store = Store(storeEmail, storeName, storeAddress)
        database2.child("stores").child(storeName).setValue(store) //new

        // database2.child("accounts").child(username).setValue(user).addOnSuccessListener {
        //binding.etEmail2.text.clear()
        //  binding.etPassword2.text.clear()

        // Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()

        // startActivity(Intent(this, HomeActivity::class.java))

        // }.addOnFailureListener {
        //     Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        //  }
    }


}