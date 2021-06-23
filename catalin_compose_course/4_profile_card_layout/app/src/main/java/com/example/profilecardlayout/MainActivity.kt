package com.example.profilecardlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.example.profilecardlayout.ui.theme.LightGreen
import com.example.profilecardlayout.ui.theme.MyTheme
import com.google.accompanist.coil.rememberCoilPainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                UsersApplication()
            }
        }
    }
}

@Composable
fun UsersApplication(profiles: List<UserProfile> = userProfileList) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "users_list") {
        // this NavGraphBuilder allow us to control how to build a navGraph
        composable(route = "users_list") {
            UserListScreen(profiles, navController)
        }
        composable(
            route = "users_details/{userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            UserDetailsScreen(
                userId = navBackStackEntry.arguments!!.getInt("userId", 0),
                navController = navController
            )
        }
    }
}

@Composable
fun UserListScreen(profiles: List<UserProfile>, navController: NavController? = null) {
    Scaffold(
        topBar = { AppBar(
            icon = Icons.Default.Home,
            title = "users"
        ) },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            LazyColumn {
                items(profiles) { item ->
                    ProfileCard(item) {
                        navController?.navigate(
                            route = "users_details/${item.id}",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(title: String, icon: ImageVector, onBackClick: (() -> Unit)? = null) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Home",
                modifier = Modifier.padding(horizontal = 12.dp)
                    .clickable { onBackClick?.invoke() }
            )
        },
        title = { Text(title) },
    )
}

@Composable
fun ProfileCard(user: UserProfile, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable { onClick() },
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
                userStatus = user.status,
                imageSize = 72.dp
            )
            ProfileContent(
                userName = user.name,
                userStatus = user.status,
                alignment = Alignment.Start
            )
        }
    }
}

@Composable
fun ProfilePicture(imageUrl: String, userStatus: Boolean, imageSize: Dp) {
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
            modifier = Modifier.size(imageSize),
        )
    }
}

@Composable
fun ProfileContent(userName: String, userStatus: Boolean, alignment: Alignment.Horizontal) {
    val statusText =
        stringResource(id = if (userStatus) R.string.user_online else R.string.user_offline)
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = alignment
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
        UserListScreen(userProfileList)
    }
}

@Composable
fun UserDetailsScreen(userId: Int = 0, navController: NavController? = null) {
    val user = userProfileList.first { userId == it.id }
    Scaffold(
        topBar = { AppBar(
            title = user.name,
            icon = Icons.Default.ArrowBack,
            onBackClick = {
                navController?.navigateUp()
            }
        ) },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            UserDetailsBody(user)
        }
    }
}

@Composable
fun UserDetailsBody(user: UserProfile = userProfileList[0]) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ProfilePicture(
            imageUrl = user.imageUrl,
            userStatus = user.status,
            imageSize = 240.dp
        )
        ProfileContent(
            userName = user.name,
            userStatus = user.status,
            alignment = Alignment.CenterHorizontally,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    MyTheme {
        UserDetailsScreen()
    }
}