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
import com.example.assignment.databinding.FragmentRegisterBinding
import com.example.assignment.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    val TAG = "logs"

    private val viewModel: UserViewModel by viewModels()
    private var name = "mamoon"
    private var number = "03234564778"
    private var password = "mamoon"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        binding.apply {
            name = this@RegisterFragment.name
            number = this@RegisterFragment.number
            password = this@RegisterFragment.password
            viewModel = this@RegisterFragment.viewModel
            activity = this@RegisterFragment.activity as MainActivity?
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
                    (activity as MainActivity).replaceFragment(LoginFragment.newInstance())
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}