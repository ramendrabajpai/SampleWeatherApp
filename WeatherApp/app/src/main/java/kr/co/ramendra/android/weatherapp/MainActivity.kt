package kr.co.ramendra.android.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kr.co.ramendra.android.weatherapp.Adapter.WeatherParametersAdapter
import kr.co.ramendra.android.weatherapp.DAO.WeatherParametersDataClass
import kr.co.ramendra.android.weatherapp.R
import org.json.JSONObject
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    lateinit var mCityEt:EditText
    lateinit var mSubmitBt:Button
    lateinit var mCityName:String
    var apikey:String = "682fdd90b4d8bcc4c8f324a6767097c2"
//    val urlAddress = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}"
    var listCityConditions = arrayListOf<WeatherParametersDataClass>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById<RecyclerView>(R.id.recyclerview)
        mCityEt = findViewById<EditText>(R.id.cityEt)
        mSubmitBt = findViewById<Button>(R.id.submitBt)
        listCityConditions=ArrayList<WeatherParametersDataClass>()
        //Initialize Adapter
        recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter= WeatherParametersAdapter(this,listCityConditions)
        mSubmitBt.setOnClickListener(onSubmitQuery)
    }

    private val onSubmitQuery= View.OnClickListener { view ->
        when (view.id) {
            R.id.submitBt -> {
                //Get City Name
                mCityName=mCityEt.text.toString().trim()
                getVolleyRequest(mCityName,apikey)
            }
        }
    }

    private fun getVolleyRequest(mCityName: String, apikey: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${mCityName}&appid=${apikey}"

        val jsonRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                setClimateValues(response)
                Log.d("Test","Response = ${response.toString()}")
            },
            {er->
                val networkResponse : Int? =(er?.networkResponse?.statusCode)
                Log.d("Test","Error= $networkResponse")
                val resultResponseString =if(networkResponse == null){ "No Network Connection "}
                            else if(networkResponse == 404){"Please enter valid city"}
                            else if(networkResponse == 400){"Please provide us city name to get weather details"}
                            else {"Unable to process request"}

                Toast.makeText(applicationContext,resultResponseString,Toast.LENGTH_SHORT).show()
            },)

        queue.add(jsonRequest)
    }

    private fun setClimateValues(response: JSONObject?) {
        val df = DecimalFormat("#.##")
        val city=response?.getString("name")
        val temperature= response?.getJSONObject("main")?.getString("temp")?.toFloat()?.minus(273.15)
        val minTemperature=response?.getJSONObject("main")?.getString("temp_min")?.toFloat()?.minus(273.15)
        val maxTemperature=response?.getJSONObject("main")?.getString("temp_max")?.toFloat()?.minus(273.15)
        val weather=response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")
        val pressure = response?.getJSONObject("main")?.getString("pressure")
        val humidity = response?.getJSONObject("main")?.getString("humidity")
        val windSpeed = response?.getJSONObject("wind")?.getString("speed")
        val windDegree = response?.getJSONObject("wind")?.getString("deg")
        listCityConditions.add(WeatherParametersDataClass(city,df.format(temperature).toString(),df.format(minTemperature).toString(),df.format(maxTemperature).toString(),weather,pressure,humidity,windSpeed, windDegree))
        recyclerView.adapter?.notifyDataSetChanged()
    }


}