package com.sample.jetretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.jetretrofit.ui.theme.JetRetrofitTheme
import com.sample.jetretrofit.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {

  private val viewModel by viewModels<TodoViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetRetrofitTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          TodoView(viewModel)
        }
      }
    }
  }
}

@Composable
fun TodoView(viewModel: TodoViewModel) {

  LaunchedEffect(Unit, block = {
    viewModel.getTodoList()
  })

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row {
            Text("Todos")
          }
        })
    },
    content = {
      if (viewModel.errorMessage.isEmpty()) {
        Column(modifier = Modifier.padding(16.dp)) {
          LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(viewModel.todoList) { todo ->
              Column {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Box(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(0.dp, 0.dp, 16.dp, 0.dp)
                  ) {
                    Text(
                      todo.title,
                      maxLines = 1,
                      overflow = TextOverflow.Ellipsis
                    )
                  }
                  Spacer(modifier = Modifier.width(16.dp))
                  Checkbox(checked = todo.completed, onCheckedChange = null)
                }
                Divider()
              }
            }
          }
        }
      } else {
        Text(viewModel.errorMessage)
      }
    }
  )
}
