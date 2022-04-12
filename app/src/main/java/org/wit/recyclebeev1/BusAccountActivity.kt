package org.wit.recyclebeev1

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.recyclebeev1.databinding.ActivityAccountBinding
import org.wit.recyclebeev1.databinding.ActivityBusAccountBinding

class BusAccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBusAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog : Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusAccountBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid

        databaseReference = FirebaseDatabase.getInstance().getReference("accounts").child("BusinessUsers") //new users code here
        binding.saveBtn.setOnClickListener {

            showProgressBar()


            val businessName = binding.etBusinessName.text.toString()
            val businessAddress = binding.etBusinessAddress.text.toString()
            val businessBio = binding.etBusinessBio.text.toString()



            val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference //new


            //uncomment this for old way
            // val account = Account(firstName, lastname, eircode)
            val user = User( businessName, businessAddress, businessBio)
            if (uid != null) {
                val objRef = database3.child("accounts").child("BusinessUsers") //added users code here
                objRef.child(uid).child("businessName").setValue(businessName)
                objRef.child(uid).child("businessAddress").setValue(businessAddress)
                objRef.child(uid).child("businessBio").setValue(businessBio)
                uploadProfilePic()

            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.pic}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid+".jpg")
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this@BusAccountActivity, "profile successfully updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            hideProgressBar()
            Toast.makeText(this@BusAccountActivity, "Failed to upload image", Toast.LENGTH_SHORT).show()

        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this@BusAccountActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}