package org.wit.recyclebeev1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.recyclebeev1.databinding.ActivityReadStoreDataBinding

class ReadStoreData : AppCompatActivity() {

    private lateinit var binding : ActivityReadStoreDataBinding
    private lateinit var database3 : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_read_store_data)

        binding = ActivityReadStoreDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readdataBtn.setOnClickListener {

            val storeName : String = binding.etusername.text.toString()
            if  (storeName.isNotEmpty()){

                readData(storeName)

            }else{

                Toast.makeText(this,"PLease enter the Username",Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun readData(storeName: String) {

        //database = FirebaseDatabase.getInstance().getReference("stores")
        database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference
        //database3.child(storeName).get().addOnSuccessListener {
        database3.child("stores").child(storeName).get().addOnSuccessListener {

            if (it.exists()){

                val storeName = it.child("storeName").value
                val storeEmail = it.child("storeEmail").value
                val storeAddress = it.child("storeAddress").value
                Toast.makeText(this,"Successfuly Read",Toast.LENGTH_SHORT).show()
                binding.etusername.text.clear()
                binding.tvStoreName.text = storeName.toString()
                binding.tvStoreEmail.text = storeEmail.toString()
                binding.tvStoreAddress.text = storeAddress.toString()

            }else{

                Toast.makeText(this,"Store Doesn't Exist",Toast.LENGTH_SHORT).show()

            }

        }.addOnFailureListener{

            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            
        }
    }
}