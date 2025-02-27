package com.sample.singlepointtask.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.singlepointtask.R
import kotlinx.coroutines.delay


@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        Modifier.padding(horizontal = 16.dp),
        onSearchTextEntered = {
        },
        onSearchStart = {
        },
        onFocusChange = {
        },
        onBackPressed = {
        },
        onClearPressed = {
        },
        "1234",
        true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchTextEntered: (String) -> Unit,
    onSearchStart: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onBackPressed: () -> Unit,
    onClearPressed: () -> Unit,
    searchQuery: String,
    onFocusState: Boolean
) {
    val textState = searchQuery
    val focusState = onFocusState
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    StartSearchAfterDelay(onSearchStart,searchQuery)
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(

    ) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn((searchBoxHeight +4).dp)
                .onFocusChanged {
                    Log.d("SearchBar", "value = " + focusState)
                    onFocusChange(it.isFocused)
                }
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(0.dp),
            value = textState,
            onValueChange = {
                onSearchTextEntered(it)
            },
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Light,
                fontSize = with(LocalDensity.current) { 16.sp }

            ),

            trailingIcon = {
                IconButton(
                    onClick = {
                        onSearchTextEntered("")
                        onClearPressed()
                        onBackPressed()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFF598626),
                containerColor = Color(0xFF598626),
                focusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF598626)

            ),
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.placeholder_search),
                    style = TextStyle(
                        color = Color.White
                    ),

                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = 16.sp
                )
            }
        )
        Column(
            modifier = Modifier
                .heightIn(searchBoxHeight.dp)
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .requiredHeight(2.dp)
                    .background(Color.White)
                    .offset(y = (-1).dp)
            )
        }
    }
}
@Composable
fun StartSearchAfterDelay(
    onSearchStart: (String) -> Unit,
    searchQuery: String,
) {
    LaunchedEffect(searchQuery) {
        delay(2000)
        onSearchStart(searchQuery)
    }
}

