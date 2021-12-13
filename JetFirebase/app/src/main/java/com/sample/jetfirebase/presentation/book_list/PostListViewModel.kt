package com.sample.jetfirebase.presentation.post_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.jetfirebase.repositories.PostRepository
import com.sample.jetfirebase.repositories.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PostListViewModel
@Inject
constructor(
    private val postRepository: PostRepository
): ViewModel() {

    private val _state: MutableState<PostListState> = mutableStateOf(PostListState())
    val state: State<PostListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getBookList()
    }

    fun getBookList() {
        postRepository.getPostList().onEach { result ->
            when(result) {
                is Result.Error -> {
                    _state.value = PostListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = PostListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = PostListState(posts = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteBook(bookId: String) {
        postRepository.deletePost(bookId)
    }

}




























