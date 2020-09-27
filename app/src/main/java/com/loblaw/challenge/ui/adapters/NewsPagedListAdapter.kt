package com.loblaw.challenge.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loblaw.challenge.R
import com.loblaw.challenge.model.ChildData
import com.loblaw.challenge.model.Children
import kotlinx.android.synthetic.main.news_list_row.view.*

class NewsPagedListAdapter(private val context: Context, private val newsItemClickedListener: NewsItemClickListener): PagedListAdapter<Children, NewsViewHolder>(NewsDiffCallBack())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val inflatedView = inflater.inflate(R.layout.news_list_row, parent, false)
        return NewsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = getItem(position)

        holder.apply {
            if(newsItem != null) {
                val newsInformation = newsItem.childData
                tvNewsTitle.text = HtmlCompat.fromHtml(newsInformation.title, HtmlCompat.FROM_HTML_MODE_LEGACY)

                if(newsInformation.secureMedia != null) {
                    ivNewsThumbnail.visibility = View.VISIBLE
                    Glide.with(ivNewsThumbnail.context).load(newsInformation.secureMedia.oembed.thumbnailUrl)
                        .into(ivNewsThumbnail)
                    ivNewsThumbnail.adjustViewBounds = true
                } else {
                    ivNewsThumbnail.visibility = View.GONE
                }

                clNewsLayout.setOnClickListener {
                    newsItemClickedListener.onNewsItemClicked(newsInformation)
                }
            }
        }
    }
}

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNewsTitle = view.news_title
    val ivNewsThumbnail = view.news_thumbnail
    val clNewsLayout = view.news_row_layout
}

class NewsDiffCallBack : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem.childData.id == newItem.childData.id
    }

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem.childData.id == newItem.childData.id
    }
}

interface NewsItemClickListener {
    fun onNewsItemClicked(newsInformation: ChildData)
}