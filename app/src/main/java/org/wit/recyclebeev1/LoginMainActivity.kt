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
import com.google.android.gms.common.util.ArrayUtils.contains
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String

    private lateinit var user: User

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
        //checkUser()

        //---new login page ----------
        //if user in DBref
//        firebaseAuth = FirebaseAuth.getInstance()
//        uid = firebaseAuth.currentUser?.uid.toString()
//        val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/BusinessUsers/").reference //new
//        databaseReference = database3.child("accounts").child("BusinessUsers")
//        if(uid.isNotEmpty()){
//           // getUserData()
//        }
        //------------

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
               // val dbRef = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/BusinessUsers/").reference

                //userLogin()
                //getUserData()

                //old way ----
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
                //-----



            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

   // private fun userLogin() {


//        if (databaseReference.child("BusinessUser").orderByChild("email").equalTo(user.email))
//        // .equalTo(user.email).once("value", snapshot => {
//        {
//            //open Bushome?
//        } else {
//            //open Home
//
//        }
//        databaseReference.child("BusinessUser").addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                user = snapshot.getValue(User(email)::class.java)!!
//                //if(user("email").equals(email)){
//                if (user.equals(email)) {
//                    //open Bushome
//                    startActivity(Intent(this@LoginMainActivity,BusHomeActivity::class.java))
//                } else {
//                    //open Home
//                    startActivity(Intent(this@LoginMainActivity,HomeActivity::class.java))
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                //hideProgressBar()
//                Toast.makeText(
//                    this@LoginMainActivity,
//                    "Failed to get user profile data",
//                    Toast.LENGTH_SHORT
//                )
//            }
//        })

    }



    //private fun getUserData() {


        //showProgressBar()
        //database3.child(uid).addValueEventListener(object: ValueEventListener{
        //databaseReference.child(uid).addValueEventListener(object: ValueEventListener {
        //databaseReference.child("BusinessUser").orderByChild("email").equalTo(user.email).once("value", snapshot => {


//            databaseReference.child("BusinessUser").addValueEventListener(object: ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    user = snapshot.getValue(User::class.java)!!
//                    binding.tvFullName.setText(user.firstName + " " + user.lastName)
//                    binding.tvEircode.setText(user.eircode)
//                    //getUserProfilePic()
//                    //Toast.makeText(this@AccountDisplayActivity, "Successfully recieved user profile data", Toast.LENGTH_SHORT)
//                }
//                override fun onCancelled(error: DatabaseError) {
//                    //hideProgressBar()
//                    Toast.makeText(this@LoginMainActivity, "Failed to get user profile data", Toast.LENGTH_SHORT)
//                }
//        })



   // }

    //need to change this to two homepages
//    private fun checkUser() {
//        //if user is already logged in g oto profile acitivty
//        //get current user
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            //user is already logged in
//            startActivity(Intent(this,HomeActivity::class.java))
//            finish()
//        }
//    }
//}



