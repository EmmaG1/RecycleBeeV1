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
import org.wit.recyclebeev1.databinding.ActivityMainBinding

public class MainActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityMainBinding


    //actionbar
    private lateinit var actionBar: ActionBar


    //progressdialog
    private lateinit var progressDialog: ProgressDialog


    //firebaseauth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //config actionbar, enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Sign up"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("creating account ..")
        progressDialog.setCanceledOnTouchOutside(false)

        //firebase auth init
        firebaseAuth = FirebaseAuth.getInstance()

        //handle click begin signin
        binding.signUpBtn.setOnClickListener {
            //validate data
            validateData()
        }
    }

    private fun validateData() {
        //get data
        email = binding.etEmail2.text.toString().trim()
        password = binding.etPassword2.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.etEmail2.error = "Invaid email format"
        } else if (TextUtils.isEmpty(password)) {
            //password isnt entered
            binding.etPassword2.error = "Pls enter pw"

        } else if (password.length < 6) {
            //pw length is < 6
            binding.etPassword2.error = "Pw must be 6 chars long"
        } else {
            //data is valid, cintinue signup
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
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                //open profile
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }
            .addOnFailureListener { e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "Signup failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() //go back to previous activity when back button of actionbar pressed
        return super.onSupportNavigateUp()
    }
    }
