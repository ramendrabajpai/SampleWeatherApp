package kr.co.ramendra.android.weatherapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.co.ramendra.android.weatherapp.DAO.WeatherParametersDataClass
import kr.co.ramendra.android.weatherapp.R

class WeatherParametersAdapter(val mainContext : Context, private val weatherDetails :ArrayList<WeatherParametersDataClass>)
    :RecyclerView.Adapter<WeatherParametersAdapter.WeatherDetailsViewHolder>(){

        class WeatherDetailsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
            val cityParam: TextView? = itemView.findViewById<TextView>(R.id.city_param)
            val tempParam: TextView? = itemView.findViewById<TextView>(R.id.temp_param)
            val minTemp: TextView? = itemView.findViewById<TextView>(R.id.min_temp)
            val maxTemp: TextView? = itemView.findViewById<TextView>(R.id.max_temp)
            val weather: TextView? = itemView.findViewById<TextView>(R.id.weather_param)
            val pressure: TextView? = itemView.findViewById<TextView>(R.id.pressure_param)
            val humidity: TextView? = itemView.findViewById<TextView>(R.id.humidity_param)
            val windSpeed: TextView? = itemView.findViewById<TextView>(R.id.windSpeed_param)
            val windDegree: TextView? = itemView.findViewById<TextView>(R.id.windDegree_param)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_parameters,parent,false)
        return WeatherDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherDetailsViewHolder, position: Int) {
        holder.cityParam?.text="${weatherDetails[position].cityName}"
        holder.tempParam?.text="Temperature: ${weatherDetails[position].temperature}°C"
        holder.minTemp?.text="Min Temp: ${weatherDetails[position].minTemp}°C"
        holder.maxTemp?.text="Max Temp: ${weatherDetails[position].maxTemp}°C"
        holder.weather?.text="Weather: ${weatherDetails[position].weather}"
        holder.pressure?.text="Pressure: ${weatherDetails[position].pressure}"
        holder.humidity?.text="Humidity: ${weatherDetails[position].humidity}%"
        holder.windSpeed?.text="Wind Speed: ${weatherDetails[position].windSpeed}"
        holder.windDegree?.text="Wind Degree: ${weatherDetails[position].windDegree}°"
    }

    override fun getItemCount(): Int {
      return weatherDetails.size
    }
}