package org.wit.recyclebeev1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val storeList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = storeList[position]

        holder.busName.text = currentitem.businessName
        holder.busEmail.text = currentitem.email
        holder.busAddress.text = currentitem.businessAddress
        holder.busBio.text = currentitem.businessBio

    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val busName : TextView = itemView.findViewById(R.id.tvBusName)
        val busEmail : TextView = itemView.findViewById(R.id.tvBusEmail)
        val busAddress : TextView = itemView.findViewById(R.id.tvBusAdd)
        val busBio : TextView = itemView.findViewById(R.id.tvBio)

    }
}