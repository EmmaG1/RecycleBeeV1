package org.wit.recyclebeev1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecycleMenuActivity : AppCompatActivity() {

    private lateinit var newRecyclerview : RecyclerView
    private lateinit var newArrayList : ArrayList<Recycle>

    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var briefNews : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_item)

        imageId = arrayOf(
            R.drawable.ic_home,
            R.drawable.ic_map
        )

        heading = arrayOf("Paper", "plastic")

        briefNews = arrayOf(
            getString(R.string.new1),
            getString(R.string.new2)
        )

        newRecyclerview=findViewById(R.id.recyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)

        newArrayList = arrayListOf<Recycle>()
        getUserData()

    }

    private fun getUserData() {

        for(i in imageId.indices){
            val recycle = Recycle(imageId[i], heading[i], briefNews[i])
            newArrayList.add(recycle)

        }
        val adapter = MyAdapterTwo(newArrayList)
        newRecyclerview.adapter = adapter

    }
}