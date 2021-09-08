package com.example.assignment.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.assignment.R
import com.example.assignment.ui.vm.UserViewModel
import com.example.assignment.databinding.FragmentLoginBinding
import com.example.assignment.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    val TAG = "logs"

    private val viewModel: UserViewModel by viewModels()
    private var number = "03234564778"
    private var password = "mamoon"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding.apply {
            number = this@LoginFragment.number
            password = this@LoginFragment.password
            viewModel = this@LoginFragment.viewModel
            activity = this@LoginFragment.activity as MainActivity?
        }

        viewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "looging get user")
            when (it) {
                is UserViewModel.Response.Loading -> binding.progressBar.visibility = View.VISIBLE
                is UserViewModel.Response.Error -> {
                    Log.d(TAG, "Error")
                    binding.progressBar.visibility = View.GONE
                    if (!it.message.isNullOrEmpty())
                        Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                }
                else -> {
                    Log.d(TAG, "Success")
                    binding.progressBar.visibility = View.GONE
                    if (it.data != null) {
                        (activity as MainActivity).user = it.data
                        (activity as MainActivity).replaceFragment(ProductFragment.newInstance())
                    }
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}