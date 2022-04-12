package org.wit.recyclebeev1

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.recyclebeev1.databinding.ActivityAccountDisplayBinding
import org.wit.recyclebeev1.databinding.ActivityBusAccountDisplayBinding
import java.io.File

class BusAccountDisplayActivity : AppCompatActivity() {


    private lateinit var binding: ActivityBusAccountDisplayBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog
    private lateinit var user: User
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusAccountDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()

        //databaseReference= FirebaseDatabase.getInstance().getReference("accounts")
        val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference //new
        databaseReference = database3.child("accounts").child("BusinessUsers") //added users code here
        if(uid.isNotEmpty()){
            getUserData()
        }

        binding.userAccountBtn.setOnClickListener {
            startActivity(Intent(this, BusAccountActivity::class.java))

        }

        binding.deleteAccountBtn.setOnClickListener {

            databaseReference.child(uid).removeValue().addOnSuccessListener {
                Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Account deletion failed", Toast.LENGTH_SHORT).show()
            }

//            val user = Firebase.auth.currentUser!!
//
//            user.delete()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        Log.d(TAG, "User account deleted.")
//                    }
//                }

            startActivity(Intent(this, LaunchActivity::class.java))

        }
    }

    private fun getUserData() {


        showProgressBar()
        //database3.child(uid).addValueEventListener(object: ValueEventListener{
        databaseReference.child(uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvBusinessName.setText(user.businessName) //firstName
                binding.tvBusinessBio.setText(user.businessBio) //eircode
                getUserProfilePic()
                //Toast.makeText(this@AccountDisplayActivity, "Successfully recieved user profile data", Toast.LENGTH_SHORT)
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
                Toast.makeText(this@BusAccountDisplayActivity, "Failed to get user profile data", Toast.LENGTH_SHORT)
            }
        })
    }

    private fun showProgressBar(){
        dialog = Dialog(this@BusAccountDisplayActivity)
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
            Toast.makeText(this@BusAccountDisplayActivity, "Failed to retrieve image", Toast.LENGTH_SHORT)
        }



    }
}
