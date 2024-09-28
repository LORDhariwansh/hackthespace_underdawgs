package com.example.Farms_hts

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.farmers_hts.R
import com.example.farmers_hts.resetpass
import org.json.JSONException
import org.json.JSONObject

class forgotpass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)

        val phone = findViewById<EditText>(R.id.etphoner)
        val email = findViewById<EditText>(R.id.emailr)
        val btnNext = findViewById<Button>(R.id.btn_nxt)


        btnNext.setOnClickListener {
            val phoneText = phone.text.toString().trim()
            val emailText = email.text.toString().trim()


            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phoneText.length < 10) {
                Toast.makeText(this, "Phone number should be at least 10 digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phoneText.isEmpty() || emailText.isEmpty()) {
                Toast.makeText(this, "All fields must be filled out", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val requestQueue = Volley.newRequestQueue(this)
            val url = "http://13.235.250.119/v2/forgot_password/fetch_result"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                Response.Listener { response ->

                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.optBoolean("success", false)

                        if (success) {
                            Toast.makeText(this, "OTP sent to your email", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, resetpass::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val errorMessage = jsonResponse.optString("message", "Operation failed")
                            Toast.makeText(this, "Failed: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(this, "Invalid response format", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    handleError(error)
                }
            )
            {
                override fun getParams(): Map<String, String> {
                    val params = mutableMapOf<String, String>()
                    params["email"] = emailText
                    params["mobile_number"] = phoneText
                    return params
                }
            }

            requestQueue.add(stringRequest)
        }
    }

    private fun handleError(error: VolleyError) {
        val errorMessage = when (error.networkResponse?.statusCode) {
            400 -> "Bad Request"
            404 -> "Endpoint not found"
            500 -> "Server error"
            else -> "Network error: ${error.localizedMessage}"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}
