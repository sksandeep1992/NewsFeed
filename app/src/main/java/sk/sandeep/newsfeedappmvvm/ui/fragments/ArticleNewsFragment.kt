package sk.sandeep.newsfeedappmvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import sk.sandeep.newsfeedappmvvm.databinding.FragmentArticleNewsBinding
import sk.sandeep.newsfeedappmvvm.ui.activity.NewsActivity
import sk.sandeep.newsfeedappmvvm.view_model.NewsViewModel

class ArticleNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentArticleNewsBinding

    private val args by navArgs<ArticleNewsFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleNewsBinding.inflate(inflater, container, false)
        //by doing This we can use view model instance that we create in newsActivity
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(binding.root, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }

        return binding.root
    }
}