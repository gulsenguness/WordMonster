package com.gulsengunes.wordmonster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.gulsengunes.wordmonster.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.wordFragment->{
                    navController.navigate(R.id.wordFragment)
                true
                }
                R.id.learnedFragment->{
                    navController.navigate(R.id.learnedFragment)
                    true
                }
                R.id.addWordFragment->{
                    navController.navigate(R.id.addWordFragment)
                    true
                }
                R.id.gameFragment->{
                    navController.navigate(R.id.gameFragment)
                    true
                }
                R.id.scoreFragment->{
                    navController.navigate(R.id.scoreFragment)
                    true
                }

                else -> false
            }
        }
    }
}