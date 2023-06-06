package com.example.typicodebook.state

import com.example.typicodebook.model.TextUi

sealed class CommentsUiState {
    data class Success(val comments: List<TextUi> = emptyList()) : CommentsUiState()
    data class Error(val errorMessage: String? = null) : CommentsUiState()
    data class Fetching(val isFetching: Boolean = false) : CommentsUiState()
}