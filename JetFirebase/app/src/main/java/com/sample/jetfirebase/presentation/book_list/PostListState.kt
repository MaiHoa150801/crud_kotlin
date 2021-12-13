package com.sample.jetfirebase.presentation.post_list

import com.sample.jetfirebase.model.Post

data class PostListState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val error: String = ""
)
