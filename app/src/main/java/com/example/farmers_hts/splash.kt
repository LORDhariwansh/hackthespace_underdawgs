package com.example.farmers_hts;


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.farmers_hts.R

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        lateinit var top: Animation
        lateinit var bottom: Animation
        lateinit var logo: ImageView

        logo=findViewById(R.id.logo)
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation)

        logo.startAnimation(top)
        Handler().postDelayed({
            val splsh= Intent(this@splash,login::class.java)
            startActivity(splsh)
            finish()
        },2000)


    }
}