package com.example.farmers_hts;


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.farmers_hts.R

class resetpass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resetpass)
        val pass=findViewById<EditText>(R.id.etphonerr)
        val conpass=findViewById<EditText>(R.id.emailrr)
        val otp=findViewById<EditText>(R.id.otp)

        val btnxt=findViewById<Button>(R.id.btn_nxtt)

        btnxt.setOnClickListener {


            val passone = pass.text.toString().trim()
            val conpasso = conpass.text.toString().trim()
            val ot=otp.text.toString().trim()

            val url="http://13.235.250.119/v2/reset_password/fetch_result"
            if (passone.isEmpty() || conpasso.isEmpty() || passone.toString()!=conpasso|| ot.isEmpty()) {
                Toast.makeText(this, "All fields must be filled out & check password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val requestQueue = Volley.newRequestQueue(this)
            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->
                    if (response.contains("success")) {
                        Toast.makeText(this, "otp sent to mail", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed: $response", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error: ${error.localizedMessage}", Toast.LENGTH_SHORT)
                        .show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params = mutableMapOf<String, String>()
                    params["password"] = passone
                    params["confirm password"] = conpasso
                    params["otp"]=ot
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }



    }
}