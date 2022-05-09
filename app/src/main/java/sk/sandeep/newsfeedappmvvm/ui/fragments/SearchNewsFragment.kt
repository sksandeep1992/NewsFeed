package sk.sandeep.newsfeedappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sk.sandeep.newsfeedappmvvm.adapters.NewsAdapter
import sk.sandeep.newsfeedappmvvm.databinding.FragmentSearchNewsBinding
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article
import sk.sandeep.newsfeedappmvvm.ui.activity.NewsActivity
import sk.sandeep.newsfeedappmvvm.util_or_constants.Constants.Companion.SEARCH_DELAY
import sk.sandeep.newsfeedappmvvm.util_or_constants.Resource
import sk.sandeep.newsfeedappmvvm.view_model.NewsViewModel

class SearchNewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        //by doing This we can use view model instance that we create in newsActivity
        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView()

        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
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

        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun listItemClicked(article: Article) {

        try {
            val action =
                SearchNewsFragmentDirections.actionSearchNewsFragment2ToArticleNewsFragment(
                    article
                )
            findNavController().navigate(
                action
            )
        } catch (e: Exception) {
            Log.d("SK", "listItemClicked: ${e.message}")
        }
    }
}