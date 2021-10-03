package com.project.music.app.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.project.music.app.R
import com.project.music.app.base.BaseActivity
import com.project.music.app.databinding.ActivityMainBinding
import com.project.music.app.utils.hideActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var navController: NavController
    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun subscribeCallbackFromSuccessStatus() {
        getResponse().observe(this, Observer {
            renderSuccessResponse(it)
        })
    }

    private fun renderSuccessResponse(response: Any?) {
        when (response) {

        }
    }

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        this@MainActivity.hideActionBar()
        super.onCreate(savedInstanceState)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

}