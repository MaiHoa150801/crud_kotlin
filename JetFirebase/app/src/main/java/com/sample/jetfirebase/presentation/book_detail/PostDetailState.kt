package com.sample.jetfirebase.presentation.post_detail

import com.sample.jetfirebase.model.Post

data class PostDetailState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val error: String = ""
)
