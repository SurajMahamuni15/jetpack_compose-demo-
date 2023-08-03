package com.example.demojetpackcompose

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Tab
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Tab
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.demojetpackcompose.model.UserModel
import com.example.demojetpackcompose.ui.theme.DemoJetpackComposeTheme
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DemoJetpackComposeTheme {
                TabDemo()
            }
        }
    }

    @Composable
    fun TabDemo() {
        var state by remember { mutableStateOf(0) }
        val titles = listOf("Home", "Profile", "Setting")
        val icon = listOf(Icons.Default.Home, Icons.Default.Person, Icons.Default.Settings)
        Column {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .background(Color.LightGray)
                    .weight(1f),
                contentAlignment = Alignment.TopCenter
            ) {
                TopAppBarDemo(
                    this@MainActivity,
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                navHostController = rememberNavController()
                SetUpNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navHostController = navHostController
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.BottomStart
            ) {
                TabRow(
                   selectedTabIndex = state
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = {
                                state = index
                            },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            icon = {
                                Icon(icon[index], contentDescription = null)
                            }
                        )
                    }
                }
            }
        }
    }


    @Composable
    @Preview(showBackground = true)
    fun Preview() {
        TabDemo()
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun TextFieldWithButton() {
        val constraintSet = ConstraintSet() {
            val textFieldBox = createRefFor("textFieldBox")
            val buttonBox = createRefFor("buttonBox")
            constrain(textFieldBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(buttonBox) {
                top.linkTo(textFieldBox.bottom)
                end.linkTo(parent.end)
            }

        }
        ConstraintLayout(constraintSet = constraintSet) {
            val text = mutableStateOf("")
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .layoutId("textFieldBox")

            ) {
                TextFieldDemo(callback = {
                    text.value = it
                })
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .layoutId("buttonBox")
            ) {
                ButtonToastMsg(buttonText = "Submit", toastMsg = text.value)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TextFieldDemo(callback: (String) -> Unit) {
        var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(""))
        }
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter Name") })
        callback(text.text)
    }


    @Composable
    fun ScreenNavigation() {
        Column {
            Box(
                modifier = Modifier
                    .layoutId("topBarBox")
                    .height(56.dp)
                    .weight(1f)
            ) {
                TopAppBarDemo(
                    this@MainActivity,
                )
            }
            Box(
                modifier = Modifier
                    .layoutId("lazyColumnBox")
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                navHostController = rememberNavController()
                SetUpNavHost(navHostController = navHostController)
            }
        }
    }


    @Composable
    fun ButtonToastMsg(modifier: Modifier = Modifier, buttonText: String, toastMsg: String) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = modifier) {
                Button(onClick = {
                    Toast.makeText(this@MainActivity, toastMsg, Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = buttonText)
                }
            }
        }
    }

    @Composable
    fun TopBarWithLazyColumn() {
//                val constraintSet = ConstraintSet {
//                    val topBarBox = createRefFor("topBarBox")
//                    val lazyColumnBox = createRefFor("lazyColumnBox")
//
//                    constrain(topBarBox) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//                    constrain(lazyColumnBox) {
//                        top.linkTo(topBarBox.bottom)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//                }
//                ConstraintLayout(constraintSet) {
        Column {
            Box(
                modifier = Modifier
                    .layoutId("topBarBox")
                    .height(56.dp)
                    .weight(1f)
            ) {
                TopAppBarDemo(
                    this@MainActivity,
                )
            }
            Box(
                modifier = Modifier
                    .layoutId("lazyColumnBox")
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                LazyUserColumn(
                    context = this@MainActivity, Modifier
                        .background(MaterialTheme.colorScheme.background),
                    ArrayList<UserModel>().apply {
                        for (i in 0..20) {
                            add(
                                UserModel(
                                    R.drawable.men_img,
                                    resources.getString(R.string.loram)
                                )
                            )
                        }
                    }
                )
            }
        }

//                }
    }


    @Composable
    fun LazyUserColumn(
        context: Context,
        modifier: Modifier = Modifier,
        data: ArrayList<UserModel>
    ) {
        LazyColumn(
            modifier = modifier
        ) {
            itemsIndexed(data) { index, usermodel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    usermodel.name,
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    val constraintSet = ConstraintSet {
                        val firstBox = createRefFor("firstBox")
                        val secondBox = createRefFor("secondBox")
                        constrain(firstBox) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        constrain(secondBox) {
                            top.linkTo(parent.top)
                            start.linkTo(firstBox.end)
                        }
                    }
                    ConstraintLayout(constraintSet) {
                        Box(
                            modifier = Modifier
                                .height(90.dp)
                                .fillMaxWidth(0.15f)
                                .padding(8.dp)
                                .layoutId("firstBox")
                        ) {
                            Image(
                                painterResource(id = usermodel.image),
                                contentDescription = usermodel.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .padding(8.dp)
                                .layoutId("secondBox")
                        ) {
                            Text(text = usermodel.name, maxLines = 3)
                        }
                    }
                }
            }
        }
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarDemo(context: Context, modifier: Modifier = Modifier) {
        DemoJetpackComposeTheme {
            Scaffold(topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                    navigationIcon = {
                        IconButton(onClick = {
                            Toast.makeText(context, "Menu Coming soon!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.Search, contentDescription = "search")
                        }
                    }, modifier = modifier
                )
            }) {

            }
        }
    }


    @Composable
    fun ConstraintDemo() {
        val constraintSet = ConstraintSet {
            val firstBox = createRefFor("first")
            val secondBox = createRefFor("second")

            constrain(firstBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }
            constrain(secondBox) {
                top.linkTo(firstBox.bottom)
                start.linkTo(parent.start)
                width = Dimension.value(100.dp)
                height = Dimension.value(100.dp)
            }
        }
        ConstraintLayout(constraintSet = constraintSet, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .background(Color.Blue)
                    .layoutId("first")
            )
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .layoutId("second")
            )
        }
    }

    //ItemList(context = this, ArrayList<String>().apply {
//    add("Suraj")
//    add("Vivek")
//    add("Ajay")
//    add("Dev")
//    add("Rahul")
//})
    @Composable
    fun ItemList(context: Context, list: ArrayList<String>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(
                list
            ) { index, string ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            Toast
                                .makeText(context, string, Toast.LENGTH_SHORT)
                                .show()
                        },
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = string,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


    @Composable
    fun ColorBox(callback: (Color) -> Unit, modifier: Modifier = Modifier) {
        Box(modifier = modifier) {
            val color by remember { mutableStateOf(Color.Yellow) }
            Box(modifier = Modifier
                .background(color)
                .fillMaxSize()
                .clickable {
//                color =
                    callback(
                        Color(
                            Random.nextFloat(),
                            Random.nextFloat(),
                            Random.nextFloat(),
                            1f
                        )
                    )
                })
        }
    }

    @Composable
    fun TextColor(modifier: Modifier = Modifier) {
        Box(modifier = modifier) {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Green, fontSize = 30.sp)
                ) {
                    append("S")
                }
                append("uraj ")
                withStyle(
                    style = SpanStyle(color = Color.Green, fontSize = 30.sp)
                ) {
                    append("M")
                }
                append("ahamuni")
            }, color = Color.White, textAlign = TextAlign.Center, fontSize = 24.sp)
        }
    }
}