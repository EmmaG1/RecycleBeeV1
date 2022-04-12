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
    private var storeCounty = ""


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
            startActivity(Intent(this, BusHomeActivity::class.java))

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

        }
    }

    fun pushToDb2() {
        val database2 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference

        val store = Store(storeEmail, storeName, storeAddress)
        database2.child("stores").child(storeName).setValue(store) //new

    }


}