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
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Body()
        }
    }
}

@Composable
fun GreetingList(names: List<String>, textFieldValue: String, buttonClick: () -> Unit, onValueChange: (newName: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        names.forEach { name ->
            Greeting(name = name)
        }

        TextField(
            value = textFieldValue,
            onValueChange = { newName ->
                onValueChange(newName)
            }
        )

        Button(
            onClick = buttonClick
        ) {
            Text("ClickMe")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name",
        style = MaterialTheme.typography.h5
    )
}

@Composable
fun Body() {
    val greetingListState = remember { mutableStateListOf<String>("John") }

    val textFieldStateContent = remember {
        mutableStateOf("")
    }

    GreetingList(
        names = greetingListState,
        textFieldValue = textFieldStateContent.value,
        buttonClick = {
            greetingListState.add(textFieldStateContent.value)
        },
        onValueChange = { newName ->
            textFieldStateContent.value = newName
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Body()
}