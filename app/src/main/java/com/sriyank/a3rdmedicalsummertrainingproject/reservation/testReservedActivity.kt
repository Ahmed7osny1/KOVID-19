package com.sriyank.a3rdmedicalsummertrainingproject.reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.*
import com.sriyank.a3rdmedicalsummertrainingproject.service.VideoCall.JoinActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_test_reserved.*
import kotlinx.android.synthetic.main.activity_video_call.*
import kotlinx.android.synthetic.main.testitems.*
import org.json.JSONObject

class testReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_reserved)


            var list = ArrayList<testData>()

            // send request
            val queue = Volley.newRequestQueue(this)

            val request = MyRequestArray(
                this,
                Request.Method.GET,
                "/get-tests-reserved",
                null,
                { response ->

                    Log.d("mytag", "$response")

                    if(response.length() > 0) {

                        noTest.visibility = View.GONE

                        for (i in 0 until response.length()) {

                            val data = response.getJSONObject(i)

                            list.add(
                                testData(
                                    data.getString("test_name"),
                                    data.getString("pat_test_date"),
                                    data.getString("pat_test_time"),
                                    data.getString("hc_name")
                                )
                            )

                            recTestReserved.layoutManager = LinearLayoutManager(
                                this,
                                RecyclerView.VERTICAL, false
                            )

                            val testAdapter = TestAdapter(list)

                            recTestReserved.adapter = testAdapter

                            testAdapter.setonItemClickListener(object: TestAdapter.onItemClickListener{

                                override fun deleteAction(position: Int) {

                                    deleteTest(
                                        response.getJSONObject(position).getString("res_id")
                                    )

                                    Log.d("mytag",
                                        response.getJSONObject(position).getString("res_id")
                                    )

                                }
                            })
                        }
                    }
                    Log.d("mytag", "$list")

                },
                { error ->
                    Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
                }
            )

            queue.add(request)
        }

    private fun deleteTest(testId: String){

        val params = JSONObject()
        params.put("res_id", testId)

        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.DELETE,
            "/delete-test-reservation",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                if(response.getString("msg") == "deleted successfully"){
                    Toast.makeText(this,"deleted successfully",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,ReservedActivity::class.java))
                }else{
                    Toast.makeText(this,"deleted unsuccessful",Toast.LENGTH_LONG).show()
                }

                // if there is an error (wrong email or password)
                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this, errorMesssage, Toast.LENGTH_SHORT).show()

                }
            },
            { error ->
                Log.e(
                    "mytag",
                    "Error: $error - Status Code = ${error.networkResponse?.statusCode}"
                )
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

}