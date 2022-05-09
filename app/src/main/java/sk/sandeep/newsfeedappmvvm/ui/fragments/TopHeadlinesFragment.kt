package sk.sandeep.newsfeedappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import sk.sandeep.newsfeedappmvvm.adapters.NewsAdapter
import sk.sandeep.newsfeedappmvvm.databinding.FragmentTopHeadlinesBinding
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article
import sk.sandeep.newsfeedappmvvm.ui.activity.NewsActivity
import sk.sandeep.newsfeedappmvvm.util_or_constants.Resource
import sk.sandeep.newsfeedappmvvm.view_model.NewsViewModel

class TopHeadlinesFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentTopHeadlinesBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        //by doing This we can use view model instance that we create in newsActivity
        viewModel = (activity as NewsActivity).viewModel
        setUpRecyclerView()

        viewModel.topHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d("TopHeadlines", "onCreateView: An Error Occur $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
            }

        }
        return binding.root
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter { article ->
            listItemClicked(article)
        }

        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun listItemClicked(article: Article) {
        try {
            val action =
                TopHeadlinesFragmentDirections.actionTopHeadlinesFragment2ToArticleNewsFragment(
                    article,
                )
            findNavController().navigate(
                action
            )
        } catch (e: Exception) {
            Log.d("SK", "listItemClicked: ${e.message}")
        }
    }

}