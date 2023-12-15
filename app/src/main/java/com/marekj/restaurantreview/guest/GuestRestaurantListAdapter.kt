package com.marekj.restaurantreview.guest

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.restaurant.RestaurantView
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel

class GuestRestaurantListAdapter(private val imageModelArrayList: MutableList<RecyclerViewModel>) :
    RecyclerView.Adapter<GuestRestaurantListAdapter.ViewHolder>() {

    /*
     * Inflate our views using the layout defined in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.restaurantlist_row_layout, parent, false)

        return ViewHolder(v)
    }

    /*
     * Bind the data to the child views of the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        holder.imgView.setImageResource(info.getImages())
        holder.txtMsg.text = info.getNames()
        holder.description.text = info.getDescription()
        holder.restaurantId = info.getId().toInt()
    }

    /*
     * Get the maximum size of the
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    /*
     * The parent class that handles layout inflation and child view use
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var imgView = itemView.findViewById<View>(R.id.restaurantPicture) as ImageView
        var txtMsg = itemView.findViewById<View>(R.id.restaurantDetails) as TextView
        var description = itemView.findViewById<View>(R.id.restaurantDescription) as TextView
        var restaurantId = -1

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            var intent = Intent(itemView.context, GuestRestaurantView::class.java)
            intent.putExtra("id", this.restaurantId.toString())
            itemView.context.startActivity(intent)
        }
    }
}


//var intent = Intent(itemView.context, TeamDetail::class.java)
//itemView.context.startActivity(intent)