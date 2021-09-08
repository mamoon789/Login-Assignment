package com.example.assignment.ui.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.assignment.ui.fragment.LoginFragment
import com.example.assignment.ui.fragment.ProductFragment
import com.example.assignment.R
import com.example.assignment.ui.vm.UserViewModel
import com.example.assignment.data.User
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.databinding.NavHeaderBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val TAG = "logs"

    lateinit var binding: ActivityMainBinding
    lateinit var navHeaderBinding: NavHeaderBinding
    lateinit var user: User
    private val viewModel: UserViewModel by viewModels()

    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    lateinit var nav: NavigationView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawer = binding.drawer
        toolbar = binding.toolbar
        nav = binding.nav

        navHeaderBinding = NavHeaderBinding.bind(nav.getHeaderView(0))

        viewModel.checkUserLoggedIn()
        viewModel.user.observe(this) {
            Log.d(TAG, "check if user is already logged in")
            when (it) {
                is UserViewModel.Response.Error -> {
                    Log.d(TAG, "Error")
                    replaceFragment(LoginFragment.newInstance())
                }
                else -> {
                    Log.d(TAG, "Success")
                    replaceFragment(ProductFragment.newInstance())
                    if (it.data != null)
                        user = it.data
                }
            }


            toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
            drawer.addDrawerListener(toggle)
            toggle.syncState()

            setSupportActionBar(toolbar)

            hideToolbar()
            disableDrawer()

            nav.setNavigationItemSelectedListener {

                drawer.closeDrawer(Gravity.RIGHT)
                true

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT)
        } else {
            drawer.openDrawer(Gravity.RIGHT)
        }

        return super.onOptionsItemSelected(item)
    }

    fun disableDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    fun enableDrawer() {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    fun setToolbarTitle(title: String) {
        toolbar.title = title
    }

    fun setNavHeaderBinding() {
        navHeaderBinding.user = user
    }

    fun setMainBinding() {
        binding.viewModel = viewModel
        binding.user = user
    }

    fun replaceFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction().replace(R.id.container, fragment)
        transaction.commit()
    }
}