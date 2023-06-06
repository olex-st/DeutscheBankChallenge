package com.example.typicodebook.state

import com.example.typicodebook.model.PostUi

sealed class PostsUiState {
    data class Success(val posts: List<PostUi> = emptyList()) : PostsUiState()
    data class Error(val errorMessage: String? = null) : PostsUiState()
    data class Fetching(val isFetching: Boolean = false) : PostsUiState()
}

enum class FavoriteButtonsUiState {
    ALL_POSTS, FAV_POSTS_ONLY
}

