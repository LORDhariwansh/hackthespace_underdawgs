package com.example.Farms_hts

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmers_hts.R
import com.example.farmers_hts.home_adpter
import com.example.farmers_hts.res_home_data
import org.json.JSONException
import org.json.JSONObject

class home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize arraylist to hold farm data
        var reslist = arrayListOf<res_home_data>()
        lateinit var recycler: RecyclerView
        lateinit var layoutManager: LinearLayoutManager
        lateinit var Adapter: home_adpter

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.home_ui, container, false)

        // Setup RecyclerView
        recycler = view.findViewById(R.id.recylcerview)
        layoutManager = LinearLayoutManager(activity)
        Adapter = home_adpter(activity as Context, reslist)
        recycler.adapter = Adapter
        recycler.layoutManager = layoutManager

        // Add divider decoration
        recycler.addItemDecoration(
            DividerItemDecoration(
                recycler.context, (layoutManager as LinearLayoutManager).orientation
            )
        )

        // Hardcoded JSON data (in place of an API call)
        val jsonData = """
            {
  "status": "success",
  "data": [
    {"city": "Raipur", "grain": "Wheat", "price": 45.50},
    {"city": "Bilaspur", "grain": "Rice", "price": 42.30},
    {"city": "Durg", "grain": "Maize", "price": 38.75},
    {"city": "Korba", "grain": "Barley", "price": 36.40},
    {"city": "Raigarh", "grain": "Sorghum", "price": 39.10},
    {"city": "Jagdalpur", "grain": "Wheat", "price": 47.20},
    {"city": "Ambikapur", "grain": "Rice", "price": 43.90},
    {"city": "Rajnandgaon", "grain": "Maize", "price": 41.50},
    {"city": "Bhilai", "grain": "Barley", "price": 37.80},
    {"city": "Dhamtari", "grain": "Sorghum", "price": 40.60},
    {"city": "Mahasamund", "grain": "Wheat", "price": 46.50},
    {"city": "Balod", "grain": "Rice", "price": 44.30},
    {"city": "Kanker", "grain": "Maize", "price": 39.20},
    {"city": "Champa", "grain": "Barley", "price": 36.80},
    {"city": "Kawardha", "grain": "Sorghum", "price": 39.90},
    {"city": "Bijapur", "grain": "Wheat", "price": 45.80},
    {"city": "Jashpur", "grain": "Rice", "price": 41.90},
    {"city": "Sukma", "grain": "Maize", "price": 38.40},
    {"city": "Gariaband", "grain": "Barley", "price": 37.10},
    {"city": "Bemetara", "grain": "Sorghum", "price": 41.20},
    {"city": "Baloda Bazar", "grain": "Wheat", "price": 44.60},
    {"city": "Narayanpur", "grain": "Rice", "price": 42.50},
    {"city": "Mungeli", "grain": "Maize", "price": 37.75},
    {"city": "Kondagaon", "grain": "Barley", "price": 36.30},
    {"city": "Sakti", "grain": "Sorghum", "price": 40.00},
    {"city": "Surajpur", "grain": "Wheat", "price": 47.10},
    {"city": "Gaurela-Pendra-Marwahi", "grain": "Rice", "price": 43.80},
    {"city": "Balrampur", "grain": "Maize", "price": 38.90},
    {"city": "Dantewada", "grain": "Barley", "price": 36.20},
    {"city": "Sarangarh-Bilaigarh", "grain": "Sorghum", "price": 39.50},
    {"city": "Bastar", "grain": "Wheat", "price": 46.40},
    {"city": "Khairagarh", "grain": "Rice", "price": 43.00},
    {"city": "Patan", "grain": "Maize", "price": 39.80},
    {"city": "Pali", "grain": "Barley", "price": 36.70},
    {"city": "Tilda", "grain": "Sorghum", "price": 41.40},
    {"city": "Raigarh", "grain": "Wheat", "price": 45.90},
    {"city": "Manendragarh", "grain": "Rice", "price": 42.70},
    {"city": "Charama", "grain": "Maize", "price": 38.30},
    {"city": "Pendra", "grain": "Barley", "price": 37.20},
    {"city": "Akaltara", "grain": "Sorghum", "price": 40.90},
    {"city": "Dongargaon", "grain": "Wheat", "price": 46.10},
    {"city": "Saraipali", "grain": "Rice", "price": 43.50},
    {"city": "Pandariya", "grain": "Maize", "price": 38.10},
    {"city": "Katghora", "grain": "Barley", "price": 37.50},
    {"city": "Pathalgaon", "grain": "Sorghum", "price": 40.30},
    {"city": "Deori", "grain": "Wheat", "price": 45.70},
    {"city": "Bagbahara", "grain": "Rice", "price": 42.90},
    {"city": "Nandini", "grain": "Maize", "price": 39.60},
    {"city": "Dongargarh", "grain": "Barley", "price": 36.50},
    {"city": "Bhilai-Charoda", "grain": "Sorghum", "price": 41.00},
    {"city": "Kurud", "grain": "Wheat", "price": 46.00},
    {"city": "Patan", "grain": "Rice", "price": 43.20},
    {"city": "Palari", "grain": "Maize", "price": 38.50},
    {"city": "Gunderdehi", "grain": "Barley", "price": 37.00},
    {"city": "Dalli-Rajhara", "grain": "Sorghum", "price": 40.70},
    {"city": "Chhuikhadan", "grain": "Wheat", "price": 45.60},
    {"city": "Jamgaon", "grain": "Rice", "price": 42.10},
    {"city": "Simga", "grain": "Maize", "price": 39.30},
    {"city": "Tandula", "grain": "Barley", "price": 36.60},
    {"city": "Champa", "grain": "Sorghum", "price": 41.30},
    {"city": "Bhilai", "grain": "Wheat", "price": 45.90},
    {"city": "Tilda", "grain": "Rice", "price": 42.80},
    {"city": "Ambagarh", "grain": "Maize", "price": 38.20},
    {"city": "Saragaon", "grain": "Barley", "price": 37.40},
    {"city": "Pithora", "grain": "Sorghum", "price": 40.10},
    {"city": "Gidam", "grain": "Wheat", "price": 46.30},
    {"city": "Maroda", "grain": "Rice", "price": 43.40},
    {"city": "Navagarh", "grain": "Maize", "price": 38.60},
    {"city": "Kumhari", "grain": "Barley", "price": 37.30},
    {"city": "Nagri", "grain": "Sorghum", "price": 40.50},
    {"city": "Bhilai-3", "grain": "Wheat", "price": 46.20},
    {"city": "Dharamjaigarh", "grain": "Rice", "price": 43.60},
    {"city": "Arang", "grain": "Maize", "price": 38.80},
    {"city": "Kharora", "grain": "Barley", "price": 36.90},
    {"city": "Mohla", "grain": "Sorghum", "price": 41.10},
    {"city": "Antagarh", "grain": "Wheat", "price": 45.40},
    {"city": "Bodla", "grain": "Rice", "price": 42.40},
    {"city": "Kohka", "grain": "Maize", "price": 39.00},
    {"city": "Lormi", "grain": "Barley", "price": 37.60},
    {"city": "Sihawa", "grain": "Sorghum", "price": 40.40},
    {"city": "Pondi", "grain": "Wheat", "price": 45.30},
    {"city": "Pathariya", "grain": "Rice", "price": 42.20}
  ]
}

        """

        try {
            // Parse hardcoded JSON data
            val response = JSONObject(jsonData)
            val success = response.getString("status") == "success"

            if (success) {
                val dataArray = response.getJSONArray("data")

                // Iterate through the array and parse each JSON object
                for (i in 0 until dataArray.length()) {
                    val item = dataArray.getJSONObject(i)

                    // Extract data from JSON object
                    val city = item.getString("city")
                    val grain = item.getString("grain")
                    val price = item.getDouble("price").toString()  // Convert price to string

                    // Create a data object for RecyclerView
                    val resObj = res_home_data(
                        city,
                        grain,
                        price,   // Assuming "cost_for_one" is used for grain price
                        "",      // No image URL needed for grain data
                        ""       // No restaurant ID needed
                    )
                    reslist.add(resObj)  // Add object to list
                }

                // Update adapter with new data
                Adapter = home_adpter(activity as Context, reslist)
                recycler.adapter = Adapter
                recycler.layoutManager = layoutManager

                // Add divider decoration again after data update
                recycler.addItemDecoration(
                    DividerItemDecoration(
                        recycler.context, (layoutManager as LinearLayoutManager).orientation
                    )
                )

            } else {
                // Handle unsuccessful response
                Toast.makeText(activity as Context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        } catch (e: JSONException) {
            // Handle JSON parsing error
            Toast.makeText(activity as Context, "Error parsing data", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}