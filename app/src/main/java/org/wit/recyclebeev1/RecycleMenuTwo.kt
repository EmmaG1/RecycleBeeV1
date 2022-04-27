package org.wit.recyclebeev1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.recyclebeev1.databinding.ActivityRecycleMenuTwoBinding.inflate
import org.wit.recyclebeev1.databinding.ActivityRecycleMenuTwoBinding

class RecycleMenuTwo : AppCompatActivity() {

    private lateinit var binding: ActivityRecycleMenuTwoBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleMenuTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // setContentView(R.layout.activity_recycle_menu_two)




        binding.glassBtn.setOnClickListener {
            startActivity(Intent(this, GlassActivity::class.java))

        }

        binding.tinCanBtn.setOnClickListener {
            startActivity(Intent(this, TinCanActivity::class.java))

        }

        binding.paperBtn.setOnClickListener {
            startActivity(Intent(this, PaperActivity::class.java))

        }

        binding.plasticBtn.setOnClickListener {
            startActivity(Intent(this, PlasticActivity::class.java))

        }

        binding.batteryBtn.setOnClickListener {
            startActivity(Intent(this, BatteryActivity::class.java))

        }

        binding.textilesBtn.setOnClickListener {
            startActivity(Intent(this, TextileActivity::class.java))

        }


    }



}