package org.wit.recyclebeev1

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.recyclebeev1.databinding.ActivityMainBinding

public class MainActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityMainBinding

   // private lateinit var database: DatabaseReference

    //actionbar
    private lateinit var actionBar: ActionBar

    //progressdialog
    private lateinit var progressDialog: ProgressDialog

    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""
    private var username = ""
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//-------------------

       // val ref2 = datbase2.getReference("messages")
      //  ref2.setValue("hello")
        //var database = FirebaseDatabase.getInstance().reference

//        binding.signUpBtn.setOnClickListener{
//            var email = binding.etEmail2.text.toString()
//            var password = binding.etEmail2.text.toString()
//        }

       // database.setValue(User(email,password))

//--------------------

        //config actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("creating account ..")
        progressDialog.setCanceledOnTouchOutside(false)

        //firebase auth init
        firebaseAuth = FirebaseAuth.getInstance()

        //UID


        //handle click begin signin (this should bring to homepage)
        binding.signUpBtn.setOnClickListener {
            //validate data
            validateData()
            //pushToDb() //this is the error
            startActivity(Intent(this, HomeActivity::class.java))

        }

        //click SignIn Textview to bring you to login page
        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this, LoginMainActivity::class.java))
        }
    }

    //maybe database code goes in here?
    private fun validateData() {
        //get data
        email = binding.etEmail2.text.toString().trim()
        password = binding.etPassword2.text.toString().trim()
        username = binding.etUsername.text.toString().trim()


        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.etEmail2.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //password isnt entered
            binding.etPassword2.error = "Please enter password"

        } else if (password.length < 6) {
            //pw length is < 6
            binding.etPassword2.error = "Pw must be 6 chars long"
        } else {
            //data is valid, continue signup
            firebaseSignUp()
        }
    }

    //probably from here i should do pushtodb
    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                var database = FirebaseDatabase.getInstance().reference //new
                //get current user

                val firebaseUser = firebaseAuth.currentUser

                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                pushToDb()
                //open profile
                startActivity(Intent(this, HomeActivity::class.java))
               database.setValue(User(username, email,password))
                finish()

            }
            .addOnFailureListener { e ->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "Signup failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    //probbly should have the screen from here
    fun pushToDb() {

        val database2 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference

       //new
        uid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        val user = User(username, email, password)
        database2.child("accounts").child(uid).setValue(user)
        //database2.child("accounts").child(uid).setValue(user) //new

       // database2.child("accounts").child(username).setValue(user).addOnSuccessListener {
            //binding.etEmail2.text.clear()
          //  binding.etPassword2.text.clear()

           // Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()

           // startActivity(Intent(this, HomeActivity::class.java))

       // }.addOnFailureListener {
       //     Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
      //  }
    }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed() //go back to previous activity when back button of actionbar pressed
            return super.onSupportNavigateUp()
        }
    }

