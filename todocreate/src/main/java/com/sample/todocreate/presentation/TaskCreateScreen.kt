package com.sample.todocreate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.todocore.presentation.AppBar
import com.sample.todocore.presentation.SharedViewModel
import com.sample.todocore.presentation.UiEvent
import com.sample.todocore.presentation.events.FieldStatus
import com.sample.todocore.presentation.utils.asString
import com.sample.todocreate.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun TaskCreateScreenPreview() {
    TaskCreateScreen(
        hiltViewModel<SharedViewModel>(),
        onNavigation = {},onSnackBarMessage={

        })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    sharedViewModel: SharedViewModel,
    onNavigation: (String) -> Unit,
    onSnackBarMessage:(String)->Unit
) {
    val viewModel = hiltViewModel<TodoViewModel>()
    val context = LocalContext.current
    ProgressDialogBox(viewModel = viewModel)
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateUp -> {
                    sharedViewModel.messageState.value = event.message
                    onNavigation("Back")
                }
                is UiEvent.ShowSnackBar -> {
                    onSnackBarMessage(event.uiText.asString(context))
                }
                is UiEvent.Message -> {
//                    sharedViewModel.messageState.value = event.message
                }
                else -> {}
            }

        }
    }
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.add_todo_title),
                isSearchEnable = false,
                searchClick = {
                },
                backClick = {

                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.titleState.value.text,
                onValueChange = {
                    viewModel.onEvent(TaskEvent.EnteredTitle(it))
                },

                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),
                maxLines = 1,
                label = {
                    Text(
                        text = "Title",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,

                        )
                },
                supportingText = {

                    if (viewModel.titleState.value.error != FieldStatus.FieldFilled)
                    {
                        val errorMessage:String = when(viewModel.titleState.value.error){
                            is FieldStatus.FieldEmpty-> "Title Required"
                            is FieldStatus.InputTooShort-> "Title Minimum 10 Characters length"
                            else -> {""}
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.descState.value.text,
                onValueChange = {
                    viewModel.onEvent(TaskEvent.EnteredDescription(it))
                },
                maxLines= 1,
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    fontSize = with(LocalDensity.current) { 14.sp }

                ),
                label = {
                    Text(
                        text = "Description",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,

                        )
                },
                supportingText = {
                    if (viewModel.descState.value.error != FieldStatus.FieldFilled)
                    {
                        val errorMessage:String = when(viewModel.descState.value.error){
                            is FieldStatus.FieldEmpty-> "Description Required"
                            is FieldStatus.InputTooShort-> "Description Too Short"
                            else -> {""}
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                },
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    onClick = {
                        viewModel.onEvent(com.sample.todocreate.presentation.TaskEvent.AddTask)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7CAE60)
                    )
                ) {
                    Text(text = stringResource(id =  R.string.add_todo_title))
                }
            }
        }

    }
}
@Composable
fun ProgressDialogBox(viewModel: TodoViewModel){
    var showDialog = viewModel.dialogState.value

    if (showDialog) {
        DialogTime(viewModel)
        Dialog(
            onDismissRequest = { showDialog = false },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
@Composable
fun DialogTime(viewModel: TodoViewModel) {
    var timeLeft by remember { mutableStateOf(3) }

    LaunchedEffect(key1 = timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            if (timeLeft == 0) {
                viewModel.onEvent(TaskEvent.DialogueEvent(false))
            }
        }
    }
}