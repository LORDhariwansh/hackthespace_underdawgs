package com.example.farmers_hts;


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farmers_hts.R


class menu_adapter(
    val context: Context
    , val arrayList: ArrayList<menuData>,
    ):RecyclerView.Adapter<menu_adapter.menuViewHolder>(){

    class menuViewHolder(view: View):RecyclerView.ViewHolder(view){
        val name: TextView =view.findViewById(R.id.dish_name)
        var price: TextView =view.findViewById(R.id.Price)
        val cartButton: Button = itemView.findViewById(R.id.addbutton)


        fun bind(menuItem: menuData, position: Int) {
            name.text = menuItem.name
            price.text=menuItem.cost_for_one

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.menu_view,parent,false)
        return menu_adapter.menuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: menuViewHolder, position: Int) {
        val data=arrayList[position]
        holder.bind(data, position)
        holder.name.text=data.name
        holder.price.text= data.cost_for_one

    }

}