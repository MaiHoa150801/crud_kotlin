package com.sample.jetfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.jetfirebase.navigation.Destination
import com.sample.jetfirebase.presentation.post_detail.PostDetailScreen
import com.sample.jetfirebase.presentation.post_detail.PostDetailViewModel
import com.sample.jetfirebase.presentation.post_list.PostListScreen
import com.sample.jetfirebase.presentation.post_list.PostListViewModel
import com.sample.jetfirebase.ui.theme.JetFirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetFirebaseTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Destination.PostList.route,
                ){
                    addPostList(navController)

                    addBookDetail()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.addPostList(
    navController: NavController
){
    composable(
        route = Destination.PostList.route
    ){

        val viewModel: PostListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        PostListScreen(
            state = state,
            navigateToPostDetail = {
                navController.navigate(Destination.PostDetail.route)
            },
            isRefreshing = isRefreshing.value,
            refreshData = viewModel::getBookList,
            onItemClick = { postId ->
                navController.navigate(
                    Destination.PostDetail.route + "?bookId=$postId"
                )
            },
            deletePost = viewModel::deleteBook
        )
    }
}

fun NavGraphBuilder.addBookDetail() {
    composable(
        route = Destination.PostDetail.route + "?bookId={bookId}"
    ){

        val viewModel: PostDetailViewModel = hiltViewModel()
        val state = viewModel.state.value

        PostDetailScreen(
            state = state,
            addNewPost = viewModel::addNewPost,
            updatePost = viewModel::updatePost
        )
    }
}