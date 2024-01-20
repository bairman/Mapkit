package com.example.mapkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mapkit.ui.theme.MapKitTheme
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class MainActivity : ComponentActivity() {
private lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("02813d8c-6a80-4d82-8557-6b7db5e30c83")
        MapKitFactory.initialize(this)
        mapView = MapView(this)
        val mapKit: MapKit = MapKitFactory.getInstance()
        val probki = mapKit.createTrafficLayer(mapView.mapWindow)
        var probIndicator = false
        setContent {
            MapKitTheme {
                Box(modifier = Modifier.fillMaxSize()) {

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = {
                            mapView

                        }){
                        mapView.map.move(
                            CameraPosition(
                                Point(46.310974, 44.268196),
                                15.0f, 0.0f, 0.0f),
                            Animation(Animation.Type.SMOOTH, 3f), null
                        )



                    }
                    FloatingActionButton(onClick = {
                        probIndicator = !probIndicator
                        probki.isTrafficVisible = probIndicator
                    },
                        containerColor = if (probIndicator) Color.Red else Color.Green,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(56.dp)
                                .align(Alignment.TopEnd),
                    ) {
                        Text(
                            text = if (probIndicator) "Пробки" else "Пробки",
                            color = Color.White
                        )

                    }
                    MyApp()

                }
            }
        }
    }
    @Composable
    fun ButtonWithCallback(onClickCallback: () -> Unit) {
        val buttonText = remember { mutableStateOf("Нажми меня") }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                buttonText.value = "Кнопка нажата"
                onClickCallback() // вызов обратного вызова при нажатии на кнопку
            }) {
                Text(text = buttonText.value, fontSize = 20.sp)
            }
        }
    }

    @Composable
    fun MyApp() {
        ButtonWithCallback {
            println("Кнопка нажата!")
        }
    }



    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


}
