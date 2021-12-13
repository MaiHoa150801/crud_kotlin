package com.sample.jetfirebase.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class Destination(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object PostList : Destination("postList", emptyList())
    object PostDetail: Destination(
        route = "postDetail",
        arguments = listOf(
            navArgument("postId"){
                nullable = true
            }
        )
    )
}
