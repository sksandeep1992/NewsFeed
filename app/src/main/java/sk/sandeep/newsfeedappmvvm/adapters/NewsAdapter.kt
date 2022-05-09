package sk.sandeep.newsfeedappmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import sk.sandeep.newsfeedappmvvm.R
import sk.sandeep.newsfeedappmvvm.databinding.ItemArticlePreviewBinding
import sk.sandeep.newsfeedappmvvm.dto_or_models.Article


/**
 * This class implements a [RecyclerView] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class NewsAdapter(private val clickListener: (Article) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /**
     * The NewsViewHolder constructor takes the binding variable from the associated
     * ViewItem, which nicely gives it access to the full [Article] information.
     */
    inner class NewsViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                article.urlToImage?.let {
                    val imgUri = article.urlToImage.toUri().buildUpon().scheme("https").build()
                    ivArticleImage.load(imgUri) {
                        placeholder(R.drawable.loading_animation)
                        error(R.drawable.ic_broken_image)
                    }
                }
                tvSource.text = article.source?.name
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
            }

            binding.root.setOnClickListener {
                clickListener(article)
            }
        }
    }


    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Article] has been updated.
     */
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * */
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}