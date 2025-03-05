package com.sample.todohome.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.todocore.presentation.AppBar
import com.sample.todocore.presentation.UiEvent
import com.sample.todocore.presentation.utils.asString
import com.sample.todohome.R
import kotlinx.coroutines.flow.collectLatest


@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(
        hiltViewModel<com.sample.todocore.presentation.SharedViewModel>(),
        onNavigation = {},
        onSnackBarMessage = {})
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    sharedViewModel: com.sample.todocore.presentation.SharedViewModel,
    onNavigation: (String) -> Unit,
    onSnackBarMessage:(String)->Unit
) {
    var viewModel = hiltViewModel<HomeTodoViewModel>()
    var  context = LocalContext.current
    AlertDialog(sharedViewModel)
    LaunchedEffect(key1 = true) {
        viewModel.getList()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    onSnackBarMessage(event.uiText.asString(context))
                }
                else -> {}
            }

        }
    }
    Scaffold(
        topBar = {
            TopBarView(viewModel)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigation("CreateTask")
                },
                containerColor = Color(0xFF7CAE60)
                ,
                shape = CircleShape
//                Modifier.background(Color(0xFF396803))
            ) {
                Icon(Icons.Filled.Add,"")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier =Modifier.fillMaxWidth().padding(8.dp)){
                if (viewModel.loadingState.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),

                        strokeWidth = 2.dp
                    )
                }
            }

            ShowTodoList(viewModel)
        }
    }
}
@Composable
fun TopBarView(viewModel: HomeTodoViewModel){
    val showSearch = viewModel.topBarState.value
    if(!showSearch) {
        AppBar(
            title = stringResource(id = R.string.app_bar_title),
            searchClick = {
                viewModel.onSearchEvent(SearchEvent.TopSearchSelected(true))
            },
            backClick = {},
            isSearchEnable = true
        )
    }else{
        SearchBar(
            Modifier.padding(horizontal = 16.dp),
            onSearchTextEntered = {
                viewModel.onSearchEvent(SearchEvent.OnSearchQuery(it))
            },
            onSearchStart = {
                viewModel.onSearchEvent(SearchEvent.OnSearchStart(it))
            },
            onFocusChange = {
                viewModel.onSearchEvent((SearchEvent.OnFocusChange(it)))
            },
            onBackPressed = {
                viewModel.onSearchEvent(SearchEvent.OnSearchQuery(""))
                viewModel.onSearchEvent(SearchEvent.OnClearPressed)
            },
            onClearPressed = {
                viewModel.onSearchEvent(SearchEvent.OnClearPressed)
            },
            viewModel.searchQuery.value,
            viewModel.focusState.value,
          //  isLoading = viewModel.loadingState.value // 🔹 Pass loading state here

        )
    }
}
@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp), // Adjust size
            color = Color.Blue, // Change color if needed
            strokeWidth = 4.dp // Adjust thickness
        )
    }
}

@Composable
fun ShowTodoList(viewModel: HomeTodoViewModel){

    val todoList = viewModel.mutableList.value
    if(todoList.size>0) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        )
        {
            items(todoList.size) { item ->
                ListItem(todoList.get(item))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Press the + button to add a TODO item"
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview(){
    ListItem(
        com.sample.todocore.domain.model.ToDoDomain(
            title = "Hello",
            description = "Description"
        )
    )
}
@Composable
fun ListItem(task: com.sample.todocore.domain.model.ToDoDomain){

    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(60.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            task.title?.let {
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = it,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
fun AlertDialog(viewModel: com.sample.todocore.presentation.SharedViewModel) {
    var shouldShowDialog = remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.messageState.value){
        if(viewModel.messageState.value == "Exception") {
            shouldShowDialog.value = true
        }
    }
    if (shouldShowDialog.value) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Fail") },
            text = { Text(text = "Failed to add TODO") },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        viewModel.messageState.value = ""
                    }
                ) {
                    Text(
                        text = "OK",
                        color = Color.White
                    )
                }
            }
        )
    }
}
