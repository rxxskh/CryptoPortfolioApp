package com.rxs.cryptoportfolioapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        applicationContext.getSharedPreferences(Constants.SHARED_KEY, 0).edit().clear().apply()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomMenu()
    }

    private fun setupBottomMenu() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()
        if (navController != null) {
            binding.bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.portfolioFragment -> {
                        navController.navigate(R.id.portfolioFragment)
                    }

                    R.id.newAssetCoinFragment -> {
                        navController.navigate(R.id.newAssetCoinFragment)
                    }

                    R.id.rankingFragment -> {
                        navController.navigate(R.id.rankingFragment)
                    }
                }
                true
            }
        }
    }
}