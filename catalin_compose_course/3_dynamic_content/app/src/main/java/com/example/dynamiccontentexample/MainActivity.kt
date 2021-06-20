package com.example.dynamiccontentexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

val namesList: ArrayList<String> = arrayListOf("John", "Michael", "Andrew", "Danna")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Body()
        }
    }
}

@Composable
fun GreetingList(names: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        names.forEach { name ->
            Greeting(name = name)
        }

        Button(
            onClick = {
                namesList.add("Murillo")
            }
        ) {
            Text("ClickMe")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name",
        style = MaterialTheme.typography.h4
    )
}

@Composable
fun Body() {
    GreetingList(names = namesList)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Body()
}