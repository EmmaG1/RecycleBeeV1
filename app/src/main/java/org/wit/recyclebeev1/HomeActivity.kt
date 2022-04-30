package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

    // navigation menu
   // lateinit var toggle: ActionBarDrawerToggle //side nav bar
    //lateinit var drawerLayout: DrawerLayout

    //viewBinding
    private lateinit var binding: ActivityHomeBinding

    //Actionbar
    private lateinit var actionBar: ActionBar

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root) //old button


        //------side nav start ----------
//        drawerLayout = findViewById(R.id.drawerLayout) //side nav
//        val navView: NavigationView = findViewById(R.id.nav_view) //side nav
//
//        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) //side nav
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//
//        navView.setNavigationItemSelectedListener {
//
//            it.isChecked=true
//
//            when (it.itemId) {
//                R.id.ic_home -> replaceFragment(HomeFragment(), it.title.toString())
//                R.id.ic_map -> replaceFragment(MapFragment(), it.title.toString())
//                R.id.ic_user -> replaceFragment(UserFragment(), it.title.toString())
//
//            }
//            true
//        }
        //--side nav end ------- (also nav_menu slightly changed (toold part) and homepage layout changed (see notes)

        //config actionbar
        //actionBar = supportActionBar!! //uncomment this for old button menu
        //actionBar.title = "Home/Profile" //and this

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        //uncomment this when logoutBtn implented into new home! ------------
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
//------------------end of old home buttom menu, beginning of fragments (unused)
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


        //------------------------------old button menu
        binding.mapBtn.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))

        }

        //old button submission menu
//        binding.storeListInputBtn.setOnClickListener {
//            startActivity(Intent(this, StoreListInput::class.java))
//
//        }

        binding.storeListBtn.setOnClickListener {
            startActivity(Intent(this, ReadStoreData::class.java))

        }



        binding.accountDisplayBtn.setOnClickListener {
            startActivity(Intent(this, AccountDisplayActivity::class.java))

        }

        binding.storeListNewBtn.setOnClickListener {
            startActivity(Intent(this, StoreListActivity::class.java))

        }

        binding.recyclingBtn.setOnClickListener {
            startActivity(Intent(this, RecycleMenuTwo::class.java))

        }
//-------------------------------end of button menu
    }



    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {

        }
        else {
            //user is null user is not logged in, go to login activity
            startActivity(Intent(this, LoginMainActivity::class.java))
            finish()
        }
    }


    //old bottom nav drawer
//        private fun makeCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_wrapper, fragment)
//            commit()
//        }

    //uncomment this for new side bar
//    private fun replaceFragment(fragment: Fragment, title : String){
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frameLayout,fragment)
//        fragmentTransaction.commit()
//        drawerLayout.closeDrawers()
//        setTitle(title)
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if(toggle.onOptionsItemSelected(item)){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

}



