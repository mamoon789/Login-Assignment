package com.example.assignment.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.databinding.FragmentProductBinding
import com.example.assignment.ui.activity.MainActivity

class ProductFragment : Fragment() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentProductBinding>(
            inflater,
            R.layout.fragment_product,
            container,
            false
        )

        (activity as MainActivity).setToolbarTitle("My Products")
        (activity as MainActivity).showToolbar()
        (activity as MainActivity).enableDrawer()
        (activity as MainActivity).setMainBinding()
        (activity as MainActivity).setNavHeaderBinding()

        binding.rvProducts.adapter = Adapter()

        return binding.root
    }

    class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        }

        override fun getItemCount(): Int {
            return 25
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = ProductFragment()
    }
}