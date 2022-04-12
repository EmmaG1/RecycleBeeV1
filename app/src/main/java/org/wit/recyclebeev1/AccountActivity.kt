package org.wit.recyclebeev1

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.recyclebeev1.databinding.ActivityAccountBinding
import com.google.firebase.ktx.Firebase




class AccountActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAccountBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri: Uri
    private lateinit var dialog : Dialog

    //new
    //private var firebaseAuth = FirebaseAuth.getInstance()


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
        databaseReference = FirebaseDatabase.getInstance().getReference("accounts").child("Users") //new users code here
        binding.saveBtn.setOnClickListener {

            showProgressBar()
          //  pushToDb()

            //converted to kotlin
            //val objRef: Firebase = m_objFireBaseRef.child("accounts")
            //objRef.child(uid).child("firstName").setValue(firstName)



            //original java
            //Firebase objRef = m_objFireBaseRef.child("accounts");
            //objRef.child(uid).child("firstName").setValue(firstName);



            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val eircode = binding.etEircode.text.toString()

            //-----------------------
           // val username = databaseReference.get("username")
            //val username =
           // val firebaseUser = firebaseAuth.currentUser
            //val username = firebaseUser!!

            //val username = databaseReference.child(uid).addValueEventListener(object: ValueEventListener{
            //    override fun onDataChange(snapshot: DataSnapshot) {
             //       user = snapshot.getValue(User::class.java)!!

            //https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/mYucdOoBvZgUpizNxm3ORbbRCyo2
            //https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/mYucdOoBvZgUpizNxm3ORbbRCyo2/username
           // val username = Firebase.database("recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/accounts/mYucdOoBvZgUpizNxm3ORbbRCyo2/username/").reference.toString()


              //  }
            //})
            //--------------------

                val database3 = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference //new


            //uncomment this for old way
           // val account = Account(firstName, lastname, eircode)
            val user = User( firstName, lastName, eircode)
            if (uid != null) {
                val objRef = database3.child("accounts").child("Users") //added users code here
                objRef.child(uid).child("firstName").setValue(firstName)
                objRef.child(uid).child("lastName").setValue(lastName)
                objRef.child(uid).child("eircode").setValue(eircode)
                uploadProfilePic()

                //databaseReference.child(uid).setValue(account).addOnCompleteListener { //old
               // database3.child("accounts").child(uid).updateChildren(user).addOnCompleteListener {
                //database3.child("accounts").child(uid).setValue(account).addOnCompleteListener { //working

//                database3.child("accounts").child(uid).setValue(user).addOnCompleteListener {
//
//
//                    if (it.isSuccessful){
//                        uploadProfilePic()
//                    }
//                    else {
//                        hideProgressBar()
//                        Toast.makeText(this@AccountActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
//                    }
//                }
            }
        }

    }


    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.pic}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid+".jpg")
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





