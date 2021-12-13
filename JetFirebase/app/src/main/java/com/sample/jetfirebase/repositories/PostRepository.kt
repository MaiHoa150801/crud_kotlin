package com.sample.jetfirebase.repositories

import com.google.firebase.firestore.CollectionReference
import com.sample.jetfirebase.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository
@Inject
constructor(
    private val PostList: CollectionReference
){

    fun addNewPost(post: Post) {
        try {
            PostList.document(post.id).set(post)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getPostList() : Flow<Result<List<Post>>> = flow {
        try {
            emit(Result.Loading<List<Post>>())

            val PostList = PostList.get().await().map{ document ->
                document.toObject(Post::class.java)
            }

            emit(Result.Success<List<Post>>(data = PostList))

        } catch (e: Exception) {
            emit(Result.Error<List<Post>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun getPostById(PostId: String): Flow<Result<Post>> = flow {
        try {
            emit(Result.Loading<Post>())

            val Post = PostList
                .whereGreaterThanOrEqualTo("id", PostId)
                .get()
                .await()
                .toObjects(Post::class.java)
                .first()


            emit(Result.Success<Post>(data = Post))

        } catch (e: Exception) {
            emit(Result.Error<Post>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun updatePost(PostId: String, post: Post) {
        try {
            val map = mapOf(
                "title" to post.title,
                "content" to post.content
            )

            PostList.document(PostId).update(map)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deletePost(PostId: String) {
        try {
            PostList.document(PostId).delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}






















