package com.example.farmers_hts;

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.Farms_hts.home
import com.example.farmers_hts.R

import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var frameLayout: FrameLayout
    lateinit var navgationview: NavigationView

    lateinit var toolbar: Toolbar
    lateinit var coordinatorLayout: CoordinatorLayout
    var previous: MenuItem?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawer)
        frameLayout=findViewById(R.id.frams)
        navgationview=findViewById(R.id.navg)
        toolbar=findViewById(R.id.toolbar)
        coordinatorLayout=findViewById(R.id.coordinator)
        openHome()
        actionbar()

        navgationview.setNavigationItemSelectedListener {
            if (previous!=null){
                previous?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previous=it


            when(it.itemId){
                R.id.home_menu->{
                    Toast.makeText(this@MainActivity,"HOME", Toast.LENGTH_LONG).show()
                    openHome()
                }

                R.id.my_profile_menu->{
                    Toast.makeText(this@MainActivity,"FAV", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.frams,ImageRecognitionFragment()).addToBackStack("Home").commit()
                    supportActionBar?.title="IMAGE REC"
                    navgationview.setCheckedItem(R.id.my_profile_menu)
                    drawerLayout.closeDrawers()

                }
                R.id.favm -> {
                    Toast.makeText(this, "commodity", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frams, CommodityListFragment())  // Replace with your favourite fragment
                        .addToBackStack("Home")
                        .commit()
                    supportActionBar?.title = "Commodity"
                    navgationview.setCheckedItem(R.id.favm)
                    drawerLayout.closeDrawers()
                }

            }
            return@setNavigationItemSelectedListener true
        }
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity, drawerLayout,
            R.string.Open_drawer,
            R.string.Close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
    }


    fun actionbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="HOME"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun openHome(){
        val das=supportFragmentManager.beginTransaction()
        val fra= home()
        das.replace(R.id.frams,fra).commit()
        supportActionBar?.title="Home"
        navgationview.setCheckedItem(R.id.home_menu)
    }
    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frams)
        when(frag){
            !is home->openHome()
            else->super.onBackPressed()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if (id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

}