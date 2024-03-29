package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import org.wit.recyclebeev1.databinding.ActivityHomeBinding
import org.wit.recyclebeev1.databinding.ActivityLaunchBinding

class LaunchActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityLaunchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //click userRegBtn Button to bring you to Register page
        binding.userRegBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        //handle click, begin login
        binding.userLoginBtn.setOnClickListener {
            startActivity(Intent(this, LoginMainActivity::class.java))

        }
//old business login textview box - unused
//        binding.busLoginTv.setOnClickListener {
//            startActivity(Intent(this, BusRegister::class.java))
//
//        }

//        binding.mapBtn.setOnClickListener {
//            startActivity(Intent(this, MapsActivity::class.java))
//
//        }
    }
}