package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityHomeBinding
import org.wit.recyclebeev1.fragments.HomeFragment
import org.wit.recyclebeev1.fragments.MapFragment
import org.wit.recyclebeev1.fragments.MapsFragment
import org.wit.recyclebeev1.fragments.UserFragment

class HomeActivity : AppCompatActivity() {

    //lateinit var toggle:ActionBarDrawerToggle //side nav bar

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

        //------side nav start ----------
//        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout) //side nav
//        val navView : NavigationView = findViewById(R.id.nav_view) //side nav
//
//        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) //side nav
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//
//        navView.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.ic_home -> Toast.makeText(applicationContext, "Clicked home", Toast.LENGTH_SHORT).show()
//                R.id.ic_map -> Toast.makeText(applicationContext, "Clicked map", Toast.LENGTH_SHORT).show()
//                R.id.ic_user -> Toast.makeText(applicationContext, "Clicked user", Toast.LENGTH_SHORT).show()
//
//            }
//            true
//        }
        //--side nav end ------- (also nav_menu slightly changed (toold part) and homepage layout changed (see notes)

        //config actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Home/Profile"

        //init firebase aith
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        //UNCOMMENT THUIS FOR OLD NAV
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        val homeFragment = HomeFragment()
        val mapsFragment = MapsFragment() //changed
        val userFragment = UserFragment()
        val newmap = MapsActivity()

        //makeCurrentFragment(homeFragment)

        //UNCOMMENT THUIS FOR OLD NAV
//        binding.bottomNavigation.setOnNavigationItemSelectedListener {
//           when(it.itemId) {
//               R.id.ic_home -> makeCurrentFragment(homeFragment)
//              // R.id.ic_map -> makeCurrentFragment(mapFragment)
//              // R.id.ic_map -> makeCurrentFragment(mapsFragment) //changed
//               R.id.ic_map -> (MapsActivity)
//               R.id.ic_user -> makeCurrentFragment(userFragment)
//           }
//            true
//        }

        binding.mapBtn.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))

        }

        binding.storeListInputBtn.setOnClickListener {
            startActivity(Intent(this, StoreListInput::class.java))

        }

        binding.storeListBtn.setOnClickListener {
            startActivity(Intent(this, ReadStoreData::class.java))

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
            //user is null user is not logged in, go to login activity
            startActivity(Intent(this, LoginMainActivity::class.java))
            finish()
        }
    }

//        private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_wrapper, fragment)
//            commit()
//        }

}



