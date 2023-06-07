package com.example.typicodebook.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.base.BaseLoginUseCase
import com.example.typicodebook.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: BaseLoginUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Success())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun login(userId: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {

            try {
                _uiState.value = LoginUiState.Fetching(isFetching = true)
                val user = loginUseCase(userId)
                _uiState.value = LoginUiState.Success(loggedUserId = user.id)

            } catch (cancellationException: CancellationException) {
                throw cancellationException
            } catch (exception: Exception) {
                _uiState.value = LoginUiState.Error(getErrorMessage(exception))
            }
        }
    }

    private fun getErrorMessage(exception: Throwable): String {
        //todo: improve
        return "Something went wrong"
    }

    fun userMessageShown() {
        _uiState.value = LoginUiState.Error(null)
    }
}