package org.wit.recyclebeev1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityLoginMainBinding

class LoginMainActivity : AppCompatActivity() {

    //viewbinding
    private lateinit var binding:ActivityLoginMainBinding

    //Actionbar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog:ProgressDialog


    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "login"

        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("Logging in..")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //click noAccount TextView to bring you to Register page
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        //handle click, begin login
        binding.loginBtn.setOnClickListener {
            //before logging in, validate data
            validateData()

        }

    }

    private fun validateData() {
        //get data
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.etEmail.error= "Invlaid email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.etPassword.error = "Please enter pw"
        }
        else {
            //data us valid, begin lofgin
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()

                //open homepage
                //could i put an if statement here, if user is in "accounts", "businessUSers" on database, show bushomepage?
                //if currentuser in databasereference https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/BusinessUsers show busHome
                //or use two login screens and point to 2 different homepages or checkbox (not secure)
                //befire we go here: if statement, is user Bus or Users?
                startActivity(Intent(this,HomeActivity::class.java))
                finish()

                //if email in busDB user returns as bus user go bushome, else go home

            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in g oto profile acitivty
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }


}