package com.example.farmers_hts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommodityAdapter(private val commodities: List<Commodity>) : RecyclerView.Adapter<CommodityAdapter.CommodityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommodityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_commodity, parent, false)
        return CommodityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommodityViewHolder, position: Int) {
        val commodity = commodities[position]
        holder.bind(commodity)
    }

    override fun getItemCount(): Int {
        return commodities.size
    }

    class CommodityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stateTextView: TextView = itemView.findViewById(R.id.stateTextView)
        private val districtTextView: TextView = itemView.findViewById(R.id.districtTextView)
        private val marketTextView: TextView = itemView.findViewById(R.id.marketTextView)
        private val commodityTextView: TextView = itemView.findViewById(R.id.commodityTextView)
        private val varietyTextView: TextView = itemView.findViewById(R.id.varietyTextView)
        private val gradeTextView: TextView = itemView.findViewById(R.id.gradeTextView)
        private val arrivalDateTextView: TextView = itemView.findViewById(R.id.arrivalDateTextView)
        private val minPriceTextView: TextView = itemView.findViewById(R.id.minPriceTextView)
        private val maxPriceTextView: TextView = itemView.findViewById(R.id.maxPriceTextView)
        private val modalPriceTextView: TextView = itemView.findViewById(R.id.modalPriceTextView)
        private val commodityCodeTextView: TextView = itemView.findViewById(R.id.commodityCodeTextView)

        fun bind(commodity: Commodity) {
            stateTextView.text = commodity.State
            districtTextView.text = commodity.District
            marketTextView.text = commodity.Market
            commodityTextView.text = commodity.Commodity
            varietyTextView.text = commodity.Variety
            gradeTextView.text = commodity.Grade
            arrivalDateTextView.text = commodity.Arrival_Date
            minPriceTextView.text = "Min: ₹${commodity.Min_Price}"
            maxPriceTextView.text = "Max: ₹${commodity.Max_Price}"
            modalPriceTextView.text = "Modal: ₹${commodity.Modal_Price}"
            commodityCodeTextView.text = "Code: ${commodity.Commodity_Code}"
        }
    }
}
