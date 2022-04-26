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
import org.wit.recyclebeev1.databinding.ActivityBusRegisterBinding.inflate
import org.wit.recyclebeev1.databinding.ActivityMainBinding

class BusRegister : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityBusRegisterBinding

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
    private var busName =""
    //private var busAddress=""
    private var businessAddress=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
       //setContentView(R.layout.activity_bus_register)
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

        //handle click begin signin (this should bring to homepage)
        binding.signUpBtn.setOnClickListener {
            //validate data
            validateData()
            pushToDb() //this is the error
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
        email = binding.etBusEmail2.text.toString().trim()
        password = binding.etBusPassword2.text.toString().trim()
        username = binding.etBusUsername.text.toString().trim()
        busName = binding.etBusName.text.toString().trim()
        businessAddress = binding.etBusAdd.text.toString().trim()



        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.etBusEmail2.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //password isnt entered
            binding.etBusPassword2.error = "Please enter password"

        } else if (password.length < 6) {
            //pw length is < 6
            binding.etBusPassword2.error = "Pw must be 6 chars long"
        } else if (TextUtils.isEmpty(busName)) {
            //name isnt entered
            binding.etBusName.error = "Please enter name"
        } else if (TextUtils.isEmpty(businessAddress)) {
            //address isnt entered
            binding.etBusAdd.error = "Please enter address"
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

                //open profile
                startActivity(Intent(this, HomeActivity::class.java))
                database.setValue(User(username, email,password)) //new
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

        val user = User(username, email, password, busName, businessAddress)
        database2.child("accounts").child(username).setValue(user) //new

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



