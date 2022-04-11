package org.wit.recyclebeev1

import android.app.Dialog
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.recyclebeev1.databinding.ActivityAccountDisplayBinding
import java.io.File

class AccountDisplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountDisplayBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var user: User
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        databaseReference= FirebaseDatabase.getInstance().getReference("accounts")
        if(uid.isNotEmpty()){
            getUserData()
        }
    }

    private fun getUserData() {

       // showProgressBar()
        databaseReference.child(uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvFullName.setText(user.firstName + "" + user.lastName)
                binding.tvEircode.setText(user.eircode)
                getUserProfilePic()
            }

            override fun onCancelled(error: DatabaseError) {
               // hideProgressBar()
                Toast.makeText(this@AccountDisplayActivity, "Failed to get user profile data", Toast.LENGTH_SHORT)
            }
        })
    }

    private fun showProgressBar(){
        dialog = Dialog(this@AccountDisplayActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }

    private fun getUserProfilePic(){
        storageReference = FirebaseStorage.getInstance().reference.child("Users/$uid.jpg")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImage.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(this@AccountDisplayActivity, "Failed to retrieve image", Toast.LENGTH_SHORT)
        }



    }
}