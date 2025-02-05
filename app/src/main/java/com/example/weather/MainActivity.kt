package com.example.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather.ui.theme.WeatherTheme
import org.json.JSONObject

const val API_KEY = "1a86c614dfa6db6e65f778d79fe2e131"
const val UNITS = "metric"
const val LANGUAGE = "ru"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        name = "Cheboksary",
                        context = this
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, context: Context) {
    val state = remember {
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
            ){
            Text("Temp in $name = ${state.value} C")
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(onClick = {
                getResult(name, state, context)
            },
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                ){
                Text("Refresh")
            }

        }

    }
}

private fun getResult(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${API_KEY}&units=${UNITS}&lang=${LANGUAGE}"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
            response ->
            val obj  = JSONObject(response)
            try{
                state.value = obj.getJSONObject("main").getString("temp")
            }
            catch (e: Exception){
                Log.e("MyLog", "$e")
            }
        },
        {
            error ->
            Log.e("MyLog", "$error")
        }


    )
    queue.add(stringRequest)


}