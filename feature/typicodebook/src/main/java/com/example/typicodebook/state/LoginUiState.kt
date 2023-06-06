package com.example.typicodebook.state

sealed class LoginUiState {
    data class Success(val loggedUserId: Int? = null) : LoginUiState()
    data class Error(val errorMessage: String? = null) : LoginUiState()
    data class Fetching(val isFetching: Boolean = false) : LoginUiState()
}