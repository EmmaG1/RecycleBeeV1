package org.wit.recyclebeev1

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.CheckBox
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

    //variables
    private var email = ""
    private var password = ""
    private var username = ""
    private var firstName=""
    private var lastName = ""
    private var eircode = ""
    private var businessName = ""
    private var businessAddress = ""
    private var businessBio = ""


    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //config actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("creating account ..")
        progressDialog.setCanceledOnTouchOutside(false)

        //firebase auth init
        firebaseAuth = FirebaseAuth.getInstance()


        //handle click begin reguster (this should bring to homepage)
        binding.signUpBtn.setOnClickListener {

            //validate data
            validateData()
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
            binding.etPassword2.error = "Password must be 6 characters long"
        } else {
            //data is valid, continue signup
            firebaseSignUp()
        }
    }


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

                val chk = findViewById<CheckBox>(R.id.userCheckBox)
                val chk2 = findViewById<CheckBox>(R.id.busCheckbox)

                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()


                if(chk.isChecked){
                    pushToDb()
                    startActivity(Intent(this, HomeActivity::class.java))
                }

                if(chk2.isChecked){
                    pushToDb2()
                    startActivity(Intent(this,BusHomeActivity::class.java))
                }

               database.setValue(User(username, email,password, firstName, lastName, eircode)) //new details
                finish()

            }
            .addOnFailureListener { e ->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "Signup failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }


    fun pushToDb() {

        val database2 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference

       //new
        uid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        val user = User(username, email, password, firstName, lastName, eircode)
        database2.child("accounts").child("Users").child(uid).setValue(user)


    }

    fun pushToDb2() {

        val database2 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference

        //new
        uid = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        val user = User(username, email, password, businessName, businessAddress, businessBio)
        database2.child("accounts").child("BusinessUsers").child(uid).setValue(user)


    }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed() //go back to previous activity when back button of actionbar pressed
            return super.onSupportNavigateUp()
        }
    }



