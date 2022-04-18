package org.wit.recyclebeev1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StoreListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var storeRecyclerView : RecyclerView
    private lateinit var storeArrayList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_list)

        storeRecyclerView = findViewById(R.id.storeList)
        storeRecyclerView.layoutManager = LinearLayoutManager(this)
        storeRecyclerView.setHasFixedSize(true)

        storeArrayList = arrayListOf<User>()
        getStoreData()

    }

    private fun getStoreData() {
        dbref = Firebase.database("https://recyclebeev1-default-rtdb.europe-west1.firebasedatabase.app/").reference
      //  dbref = FirebaseDatabase.getInstance().getReference("accounts") //or BusinessUsers?
        dbref.child("accounts").child("BusinessUsers").addValueEventListener(object : ValueEventListener{
        //dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(storeSnapshot in snapshot.children){
                        val store = storeSnapshot.getValue(User::class.java)
                        storeArrayList.add(store!!)

                    }

                    storeRecyclerView.adapter = MyAdapter(storeArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}