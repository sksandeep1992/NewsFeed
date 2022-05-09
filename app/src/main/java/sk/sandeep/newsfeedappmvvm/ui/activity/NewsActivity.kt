package sk.sandeep.newsfeedappmvvm.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import sk.sandeep.newsfeedappmvvm.R
import sk.sandeep.newsfeedappmvvm.databinding.ActivityNewsBinding
import sk.sandeep.newsfeedappmvvm.db.NewsDatabase
import sk.sandeep.newsfeedappmvvm.repository.NewsRepository
import sk.sandeep.newsfeedappmvvm.view_model.NewsViewModel
import sk.sandeep.newsfeedappmvvm.view_model.NewsViewModelProviderFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        //create view model settings with viewModelProviderFactory
        val newsRepository = NewsRepository(NewsDatabase.getInstance(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()
        /**
        For Adding BottomNavigation functionality
         * */
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}