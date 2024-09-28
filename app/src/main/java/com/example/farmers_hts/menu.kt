package com.example.farmers_hts;


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.Farmhts.database_entity
import com.example.Farmhts.database_menu
import com.example.Farms_hts.home
import com.example.farmers_hts.R
import org.json.JSONException

class menu : AppCompatActivity() {
    var menulist= arrayListOf<menuData>()
    private val cartItems = mutableSetOf<menuData>()
    lateinit var recycler: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var Adapter: menu_adapter
    lateinit var toolbar: Toolbar
    lateinit var proceedToCartButton :  Button
    lateinit var addbtn :  Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)


        proceedToCartButton = findViewById(R.id.proceedToCartButton)

        recycler = findViewById(R.id.menurcy)
        toolbar=findViewById(R.id.toolbar)
        layoutManager = LinearLayoutManager(this@menu)
        Adapter = menu_adapter(this@menu, menulist)
        recycler.adapter = Adapter
        recycler.layoutManager = layoutManager


        val queue = Volley.newRequestQueue(this@menu)
        val restaurantId = intent.getStringExtra("restaurant_id") ?: "default_id"
        val urll = "http://13.235.250.119/v2/restaurants/fetch_result/$restaurantId"
        actionbar()


        val jsonObjectRequest = object :
            JsonObjectRequest(Request.Method.GET, urll, null, Response.Listener { response->
                try {
                    val dataObject = response.getJSONObject("data")
                    val success = dataObject.getBoolean("success")

                    if (success) {
                        val dataArray = dataObject.getJSONArray("data")


                        for (i in 0 until dataArray.length()) {
                            val item = dataArray.getJSONObject(i)
                            val resObj = menuData(
                                item.getString("id"),
                                item.getString("name"),
                                item.getString("cost_for_one")

                            )
                            menulist.add(resObj)
                            val databaseItem = database_entity(
                                id = item.getString("id"),
                                name = item.getString("name"),
                                cost_for_one = item.getString("cost_for_one")
                            )
                            val checkoder=dbasync(applicationContext,databaseItem,1).execute()
                            val isoder=checkoder.get()

                            if (isoder){
                                proceedToCartButton.text="REMOVE FROM FAV"
                                val remv=ContextCompat.getColor(applicationContext,R.color.FAV)
                                proceedToCartButton.setBackgroundColor(remv)
                            }
                            else{
                                proceedToCartButton.text="ADD TO FAV"
                                val rev=ContextCompat.getColor(applicationContext,R.color.DFAV)
                                proceedToCartButton.setBackgroundColor(rev)
                            }
                            proceedToCartButton.setOnClickListener {

                                if (!dbasync(applicationContext,databaseItem,1).execute().get()){
                                    val assc=dbasync(applicationContext,databaseItem,2).execute()
                                    val res=assc.get()
                                    if (res){
                                        Toast.makeText(this@menu,"ADDED TO FAV",Toast.LENGTH_SHORT).show()
                                        proceedToCartButton.text="REMOVE FROM FAV"
                                        val remv=ContextCompat.getColor(applicationContext,R.color.FAV)
                                        proceedToCartButton.setBackgroundColor(remv)
                                    }
                                    else{
                                        Toast.makeText(this@menu,"ERROR",Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else{
                                    val assc=dbasync(applicationContext,databaseItem,3).execute()
                                    val res=assc.get()
                                    if (res){
                                        Toast.makeText(this@menu,"REMOVED",Toast.LENGTH_SHORT).show()
                                        proceedToCartButton.text="ADD"
                                        val remv=ContextCompat.getColor(applicationContext,R.color.FAV)
                                        proceedToCartButton.setBackgroundColor(remv)
                                    }
                                    else{
                                        Toast.makeText(this@menu,"ERROR",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        Adapter = menu_adapter(this@menu, menulist,)
                        recycler.adapter = Adapter
                        recycler.layoutManager = layoutManager

                        recycler.addItemDecoration(
                            DividerItemDecoration(
                                recycler.context,(layoutManager as LinearLayoutManager).orientation
                            )
                        )

                    } else {
                        Toast.makeText(this@menu, "Error", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this@menu, "An error occurred while processing the data.", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {

            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers= hashMapOf<String,String>()
                headers["Content-type"]="application/json"
                headers["token"]="7e8a7a23e688c7"
                return headers
            }
        }
        val requestQueue = Volley.newRequestQueue(this@menu)
        queue.add(jsonObjectRequest)

    }

    fun openHome(){
        val das=supportFragmentManager.beginTransaction()
        val fra= home()
        das.replace(R.id.frams,fra).commit()
        supportActionBar?.title="Home"
    }
    fun actionbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="MENU"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {

        when (this@menu) {
            !is menu -> openHome()
            else -> super.onBackPressed()
        }
    }
    class dbasync(val context: Context, val databaseEntity: database_entity, val mode: Int) : AsyncTask<Void, Void, Boolean>() {
        val db = Room.databaseBuilder(context, database_menu::class.java, "menu_entity").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            return when (mode) {
                1 -> {
                    val men:database_entity?=db.dao().getmenubyid(databaseEntity.id.toString())
                    db.close()
                    return men !=null
                }
                2 -> {
                    db.dao().insertmenu(databaseEntity)
                    db.close()
                    true
                }
                3 -> {
                    db.dao().delmenu(databaseEntity)
                    db.close()
                    true
                }
                else -> {
                    false
                }
            }
        }


    }

}


