package com.chernyshev.newsletterapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chernyshev.newsletterapp.databinding.ItemNewsCardBinding
import com.chernyshev.newsletterapp.domain.entity.NewsItem
import com.chernyshev.newsletterapp.presentation.utils.extensions.loadImage

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val items: MutableList<NewsItem> = mutableListOf()
    private var onItemClick: ((NewsItem) -> Unit)? = null

    fun updateItems(newItems: List<NewsItem>) {
        items.apply {
            clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClick(block: (NewsItem) -> Unit) {
        onItemClick = block
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemNewsCardBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemNewsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsItem) = with(binding) {
            title.text = item.title.tripToFitTitleLine()
            subtitle.text = item.description.trimToFitDescriptionLength()
            image.loadImage(item.image)
            binding.root.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}

private fun String.tripToFitTitleLine(): String {
    return if (this.length >= 47) {
        this.substring(0, 37) + "..."
    } else this
}

private fun String.trimToFitDescriptionLength(): String {
    return if (this.length >= 67) {
        this.substring(0, 47) + "..."
    } else this
}