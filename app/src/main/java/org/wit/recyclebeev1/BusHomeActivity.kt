package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityBusHomeBinding
import org.wit.recyclebeev1.databinding.ActivityHomeBinding

class BusHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusHomeBinding

    //Actionbar
    private lateinit var actionBar: ActionBar

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //config actionbar
        actionBar = supportActionBar!!


        //init firebase aith
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        binding.mapBtn.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))

        }
// old store list submission button
//        binding.storeListInputBtn.setOnClickListener {
//            startActivity(Intent(this, StoreListInput::class.java))
//
//        }

        binding.storeListBtn.setOnClickListener {
            startActivity(Intent(this, ReadStoreData::class.java))

        }

        binding.busAccountDisplayBtn.setOnClickListener {
            startActivity(Intent(this, BusAccountDisplayActivity::class.java))

        }

        binding.storeListNewBtn.setOnClickListener {
            startActivity(Intent(this, StoreListActivity::class.java))

        }
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


}