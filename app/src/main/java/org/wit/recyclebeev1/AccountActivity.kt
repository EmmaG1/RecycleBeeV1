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

class AccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog : Dialog

    //new DB
//    val firstName = binding.etFirstName.text.toString()
//    val lastname = binding.etLastName.text.toString()
//    val eircode = binding.etEircode.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
      //  setContentView(R.layout.activity_account)
        databaseReference = FirebaseDatabase.getInstance().getReference("accounts")
        binding.saveBtn.setOnClickListener {

            showProgressBar()
          //  pushToDb()
            val firstName = binding.etFirstName.text.toString()
            val lastname = binding.etLastName.text.toString()
            val eircode = binding.etEircode.text.toString()

            val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference //new

            val account = Account(firstName, lastname, eircode)
            if (uid != null) {

                //databaseReference.child(uid).setValue(account).addOnCompleteListener { //old
                database3.child("accounts").child(uid).setValue(account).addOnCompleteListener {

                    if (it.isSuccessful){
                        uploadProfilePic()
                    }
                    else {
                        hideProgressBar()
                        Toast.makeText(this@AccountActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }


    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.pic}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this@AccountActivity, "profile successfully updated", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {

            hideProgressBar()
            Toast.makeText(this@AccountActivity, "Failed to upload image", Toast.LENGTH_SHORT).show()

        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this@AccountActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}

