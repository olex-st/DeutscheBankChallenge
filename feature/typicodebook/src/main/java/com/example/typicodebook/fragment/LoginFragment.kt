package com.example.typicodebook.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.typicodebook.R
import com.example.typicodebook.databinding.FLoginBinding
import com.example.typicodebook.state.LoginUiState
import com.example.typicodebook.vm.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FLoginBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        observeViewModel()
    }

    private fun setupUi() {
        binding.tietLogin.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                login()
            }
            false
        }

        binding.mbLogin.setOnClickListener {
            login()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is LoginUiState.Success -> uiState.loggedUserId?.let {
                            navigateToPostsFragment(uiState.loggedUserId)
                        }

                        is LoginUiState.Fetching -> showProgress()
                        is LoginUiState.Error -> uiState.errorMessage?.let {
                            hideProgress()
                            Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
                            viewModel.userMessageShown()
                        }
                    }
                }
            }
        }
    }

    private fun login() {
        val userId = binding.tietLogin.text
        if (userId.isNullOrBlank()) {
            Toast.makeText(requireContext(), getString(R.string.please_enter_usedid), Toast.LENGTH_LONG).show()
        } else {
            viewModel.login(userId.toString())
        }
    }

    private fun showProgress() {
        binding.includeProgress.lpiProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.includeProgress.lpiProgress.visibility = View.INVISIBLE
    }

    private fun navigateToPostsFragment(userId: Int) {
        val action = LoginFragmentDirections.actionLoginFragmentToPostsFragment(userId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}