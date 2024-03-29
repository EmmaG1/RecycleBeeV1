package org.wit.recyclebeev1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapterTwo (private val recycleList : ArrayList<Recycle>) : RecyclerView.Adapter<MyAdapterTwo.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterTwo.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
//activity_recycle_menu
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapterTwo.MyViewHolder, position: Int) {
        val currentItem = recycleList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
        holder.briefNews.text = currentItem.briefNews

        val isVisible : Boolean = currentItem.visibility
        holder.constraintLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        holder.tvHeading.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }
    }

    override fun getItemCount():Int {
        return recycleList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val titleImage: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading: TextView = itemView.findViewById(R.id.tvHeading)
        val briefNews : TextView = itemView.findViewById(R.id.briefNews)
        val constraintLayout : ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
    }

}
