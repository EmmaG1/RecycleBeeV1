package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityHomeBinding
import org.wit.recyclebeev1.fragments.HomeFragment
import org.wit.recyclebeev1.fragments.MapFragment
import org.wit.recyclebeev1.fragments.UserFragment

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

        val homeFragment = HomeFragment()
        val mapFragment = MapFragment()
        val userFragment = UserFragment()

        makeCurrentFragment(homeFragment)

       // binding.bottom_navigation.setOnNavigationItemSelectedListener {
       // binding.bottomNavigation.setOnNa
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
           when(it.itemId) {
               R.id.ic_home -> makeCurrentFragment(homeFragment)
               R.id.ic_map -> makeCurrentFragment(mapFragment)
               R.id.ic_user -> makeCurrentFragment(userFragment)
           }
            true
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

        private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}



