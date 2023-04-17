package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

/**
 * This is a recyclerview adapter set up to work with DiffUtil, which handles calculating differences
 * between versions of lists and updating data appropriately.
 *
 * There is also an onItemClickListener and corresponding function to handle actions when the data
 * items in the recyclerview are clicked.
 */

// adapter inherits from Recyclerview.Adapter and is passed a ViewHolder
class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    // ViewHolder defined here - simplest implementation
    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // "object" declaration = anonymous class
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            // normally we return oldItem.id, but our api doesn't return an id by default so we use url
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        // normally we'd return datasoure size, but we instead use the list stored in the differ object
        return differ.currentList.size
    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        // instead of having a reference to our datasource, we use the list contained in the differ object
        val article = differ.currentList[position]

        holder.itemView.apply {
            // Glide plugin loads an image from an url, "into" an ImageView
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            // function says that if the listener isn't null, apply it to the current article
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}