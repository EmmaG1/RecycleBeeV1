package org.wit.recyclebeev1

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

        //databaseReference= FirebaseDatabase.getInstance().getReference("accounts")
        val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference //new
        databaseReference = database3.child("accounts").child("Users") //added users code here
        if(uid.isNotEmpty()){
            getUserData()
        }

        binding.userAccountBtn.setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))

        }

        binding.deleteAccountBtn.setOnClickListener {
           // val user = Firebase.auth.currentUser!!
           // val user = auth.currentUser!!
            databaseReference.child(uid).removeValue()
           // deleteUser()
            startActivity(Intent(this, LaunchActivity::class.java))
           // user.delete()


            //Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()

//getting rifd of the success/fail listeners brought it to launch page
//            databaseReference.child(uid).removeValue().addOnSuccessListener {
//                Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show()
//
//
//                //should take it out of Auth db
//                val user = Firebase.auth.currentUser!!
//
//                user.delete()
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Log.d("business account", "User account deleted.")
//                        }
//                    }
//
//                startActivity(Intent(this, LaunchActivity::class.java))
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "Account deletion failed", Toast.LENGTH_SHORT).show()
//            }


            //}

        }
    }

    private fun deleteUser() {
        val user = Firebase.auth.currentUser!!

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider

            .getCredential("bob3@n.com", "1234567")

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener { Log.d("", "User re-authenticated.")

            }
                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("", "User account deleted.")
                        }
                    }

    }


    private fun getUserData() {


         showProgressBar()
        //database3.child(uid).addValueEventListener(object: ValueEventListener{
        databaseReference.child(uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.tvFullName.setText(user.firstName + " " + user.lastName)
                binding.tvEircode.setText(user.eircode)
                getUserProfilePic()
                //Toast.makeText(this@AccountDisplayActivity, "Successfully recieved user profile data", Toast.LENGTH_SHORT)
            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressBar()
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