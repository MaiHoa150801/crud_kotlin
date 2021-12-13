package com.sample.jetfirebase.presentation.post_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.jetfirebase.model.Post
import com.sample.jetfirebase.repositories.PostRepository
import com.sample.jetfirebase.repositories.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel
@Inject
constructor(
    private val PostRepository: PostRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state: MutableState<PostDetailState> = mutableStateOf(PostDetailState())
    val state: State<PostDetailState>
        get() = _state

    init {
        Log.d("PostDetailViewModel", "SavedStateHandle...")
        savedStateHandle.get<String>("PostId")?.let { PostId ->
            Log.d("PostDetailViewModel", "PostId: $PostId")
            getPost(PostId)
        }
    }

    fun addNewPost(title: String, content: String, image: String, address: String, phone: String) {
        val post = Post(
            id = UUID.randomUUID().toString(),
            image = image,
            title = title,
            content = content,
            address = address,
            phone = phone
        )

        PostRepository.addNewPost(post)
    }

    private fun getPost(PostId: String) {
        PostRepository.getPostById(PostId).onEach { result ->
           when(result){
               is Result.Error -> {
                   _state.value = PostDetailState(error = result.message ?: "Unexpected error")
               }
               is Result.Loading -> {
                   _state.value = PostDetailState(isLoading = true)
               }
               is Result.Success -> {
                   _state.value = PostDetailState(post = result.data)
               }
           }
        }.launchIn(viewModelScope)
    }

    fun updatePost(newTitle: String, newContent: String, newImage: String, newAddress: String, newPhone: String) {
        if(state.value.post == null){
            _state.value = PostDetailState(error = "Post is null")
            return
        }

        val postEdited = state.value.post!!.copy(
            title = newTitle,
            content = newContent,
            image = newImage,
            address = newAddress,
            phone = newPhone
        )


        PostRepository.updatePost(postEdited.id, postEdited)
    }

}









































