package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.example.profilecardlayout.ui.theme.LightGreen
import com.example.profilecardlayout.ui.theme.MyTheme
import com.google.accompanist.coil.rememberCoilPainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                Body()
            }
        }
    }
}

@Composable
fun Body() {
    Scaffold(
        topBar = { AppBar() },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn {
                items(userProfileList) { item ->
                    ProfileCard(item)
                }
            }
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.padding(horizontal = 12.dp)
            )
         },
        title = { Text("Messaging Users") }
    )
}

@Composable
fun ProfileCard(user: UserProfile) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(
                align = Alignment.Top
            ),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(
                imageUrl = user.imageUrl,
                userStatus = user.status
            )
            ProfileContent(
                userName = user.name,
                userStatus = user.status
            )
        }
    }
}

@Composable
fun ProfilePicture(imageUrl: String, userStatus: Boolean) {
    val imageBorderColor = if (userStatus) MaterialTheme.colors.LightGreen else Color.Red
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = imageBorderColor
        ),
        modifier = Modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Image(
            painter = rememberCoilPainter(
                request = imageUrl,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = "description",
            modifier = Modifier.size(72.dp),
        )
    }
}

@Composable
fun ProfileContent(userName: String, userStatus: Boolean) {
    val statusText = stringResource(id = if (userStatus) R.string.user_online else R.string.user_offline)
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = userName,
            style = MaterialTheme.typography.h5
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = statusText,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTheme {
        Body()
    }
}