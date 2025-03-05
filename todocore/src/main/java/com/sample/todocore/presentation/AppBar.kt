package com.sample.todocore.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


const val searchBoxHeight = 60f
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    searchClick: () -> Unit,
    backClick: () -> Unit,
    isSearchEnable: Boolean
) {
    TopAppBar(
        modifier = modifier
            .heightIn(searchBoxHeight.dp),

//            navigationIcon = {
//                IconButton(onClick = backClick) {
//                    Icon(
//                        imageVector = Icons.Filled.ArrowBack,
//                        contentDescription = "",
//                        tint = White
//                    )
//                }
//            },
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = title,
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    color = White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 16.sp },

                    )
            }
        },

        colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF598626)),
        actions = {
            if(isSearchEnable) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = searchClick) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "",
                            tint = White
                        )
                    }
                }
            }
        }


    )
}

@Preview
@Composable
fun HomeAppBarPreview() {
    AppBar(
        title = "Romantic Comedy",
        searchClick = { },
        backClick = { },
        isSearchEnable = false
    )
}