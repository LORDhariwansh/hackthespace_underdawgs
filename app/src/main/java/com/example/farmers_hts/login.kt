package com.example.farmers_hts;


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.Farms_hts.forgotpass
import com.example.farmers_hts.R


class login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val ePhone = findViewById<EditText>(R.id.et_email)
        val pass = findViewById<EditText>(R.id.pass)
        val forg = findViewById<TextView>(R.id.txt_forg)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val reg=findViewById<TextView>(R.id.txt_nt)

        reg.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
            finish()
        }
        forg.setOnClickListener {
            val intent = Intent(this, forgotpass::class.java)
            startActivity(intent)
            finish()
        }



        btnLogin.setOnClickListener {

            val mobile = ePhone.text.toString().trim()
            val password = pass.text.toString().trim()

            if (mobile.isEmpty() ||  password.isEmpty()) {
                Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val requestQueue = Volley.newRequestQueue(this)

            val url = "http://13.235.250.119/v2/login/fetch_result"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->

                    if (response.contains("success")) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed: $response", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->

                    Toast.makeText(this, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params = mutableMapOf<String, String>()
                    params["mobile_number"] = mobile
                    params["password"] = password
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }


        }


}