package com.example.farmers_hts;


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.farmers_hts.R

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.txt_name)
        val etEmail = findViewById<EditText>(R.id.txt_email)
        val etMobile = findViewById<EditText>(R.id.PHONE)
        val etPassword = findViewById<EditText>(R.id.et_passl)
        val btnRegister = findViewById<Button>(R.id.btn_reg)
        val etaddress = findViewById<EditText>(R.id.txt_addre)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val mobile = etMobile.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val adress=etaddress.text.toString().trim()


            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() ||  password.isEmpty()||adress.isEmpty() ) {
                Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val requestQueue = Volley.newRequestQueue(this)

            val url = "http://13.235.250.119/v2/register/fetch_result"

            val stringRequest = object :StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->

                    if (response.contains("success")) {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, login::class.java)
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
                    params["name"] = name
                    params["email"] = email
                    params["mobile"] = mobile
                    params["address"]=adress
                    params["password"] = password
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }
    }
}
