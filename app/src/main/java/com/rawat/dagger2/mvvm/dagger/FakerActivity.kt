package com.rawat.dagger2.mvvm.dagger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rawat.dagger2.R
import com.rawat.dagger2.databinding.ActivityFakerBinding
import com.rawat.dagger2.mvvm.dagger.viewmodels.MainViewModel
import com.rawat.dagger2.mvvm.dagger.viewmodels.MainViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment

class FakerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFakerBinding

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
        mainViewModelFactory
    }
//    private lateinit var mainViewModel: MainViewModel
//    private val viewModel: MainViewModel by viewModels<MainViewModel>(

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFakerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        // val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val appComponent = (application as FakerApplication).applicationComponent
        appComponent.inject(this)
        //mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        //mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //mainViewModel.fetchProductWithStateFlow()
        //setupStateFlowObserver()
        //mainViewModel.fetchProductWithLiveData()
        //setupLiveDataObserver()
    }

    private fun setupStateFlowObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            Log.e("Faker", "Loading")
                        }

                        is UiState.Success -> {
                            Log.e("Faker", "Success")
                            it.data
                            //products.text = it.data?.joinToString { x -> x.title + "\n\n" }
                        }

                        is UiState.Error<*> -> {
                            Log.e("Faker", "Failure")
                            //products.text = it.message.toString()
                        }
                    }
                }
            }
        }
    }

    private fun setupLiveDataObserver() {
        mainViewModel.productLiveData.observe(this) {
            when (it) {
                is UiState.Loading -> {
                    Log.e("Faker Live Data", "Loading")
                }

                is UiState.Success -> {
                    Log.e("Faker Live Data", "Success")
                    it.data
                    //products.text = it.data?.joinToString { x -> x.title + "\n\n" }
                }

                is UiState.Error<*> -> {
                    Log.e("Faker", "Failure")
                    //products.text = it.message.toString()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}