package com.example.farmers_hts;


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmers_hts.R

class home_adpter(val context: Context, val arrayList: ArrayList<res_home_data>) : RecyclerView.Adapter<home_adpter.homeViewholder>() {

    class homeViewholder(view: View) : RecyclerView.ViewHolder(view) {
        // Keeping original names for TextViews, assuming res_name -> city, Rating -> grain, Price -> price
        val name: TextView = view.findViewById(R.id.res_name)
            val rating:TextView=view.findViewById(R.id.Rating)
    val Price:TextView=view.findViewById(R.id.Price)// This will display the city name
        // This will display the grain type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): homeViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_view, parent, false)
        return homeViewholder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: homeViewholder, position: Int) {
        val data = arrayList[position]
        holder.name.text = data.name
        holder.rating.text=data.cost_for_one
        holder.Price.text=data.rating
    }
}