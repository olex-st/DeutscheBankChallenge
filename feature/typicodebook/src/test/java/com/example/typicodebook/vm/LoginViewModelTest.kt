package com.example.typicodebook.vm

import com.example.domain.usecase.base.BaseLoginUseCase
import com.example.typicodebook.fake.TestLoginUseCaseCorrectUser
import com.example.typicodebook.state.LoginUiState
import com.google.samples.apps.nowinandroid.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var testLoginUseCase: BaseLoginUseCase

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        testLoginUseCase = TestLoginUseCaseCorrectUser()

        loginViewModel = LoginViewModel(
            loginUseCase = testLoginUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @org.junit.Test
    fun login_whenLoginCorrectUser_stateAndUserIdAreSuccess() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { loginViewModel.uiState.collect() }

        loginViewModel.login("0")
        val uiState = loginViewModel.uiState.value

        assertEquals(true, uiState is LoginUiState.Success)
        var loggedUserId = 0
        if (uiState is LoginUiState.Success) {
            loggedUserId = uiState.loggedUserId!!
        }
        assertEquals(9, loggedUserId)

        collectJob.cancel()
    }
}