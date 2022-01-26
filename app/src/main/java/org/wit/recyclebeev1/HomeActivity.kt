package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //Actionbar
    private lateinit var actionBar: ActionBar

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //config actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Home/Profile"

        //init firebase aith
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user not null, user is logged in, get user info
            val email = firebaseUser.email
            //set text view
            binding.emailTV.text = email
        }
        else {
            //user is null user is not loggied in, go to login activity
            startActivity(Intent(this, LoginMainActivity::class.java))
            finish()
        }
    }
}