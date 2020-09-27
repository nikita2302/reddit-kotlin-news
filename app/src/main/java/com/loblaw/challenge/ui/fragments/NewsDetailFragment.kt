package com.loblaw.challenge.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.loblaw.challenge.Constants
import com.loblaw.challenge.R
import com.loblaw.challenge.model.ChildData
import com.loblaw.challenge.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_news_detail.view.*

/**
 * A simple [NewsDetailFragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailFragment : Fragment() {

    private lateinit var newsInformation: ChildData
    private lateinit var layout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsInformation = it.getParcelable(Constants.CHILD_NEWS_DATA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_news_detail, container, false)

        layout.news_title.text = newsInformation.title
        if (newsInformation.secureMedia != null) {
            layout.image_thumbnail.visibility = View.VISIBLE
            Glide.with(layout.image_thumbnail.context)
                .load(newsInformation.secureMedia!!.oembed.thumbnailUrl)
                .into(layout.image_thumbnail)
            layout.image_thumbnail.adjustViewBounds = true
        }

        newsInformation.selftext.let {
            //In one JSON Object self text was present but it was empty.
            // Therefore if condition is required to not display empty space
            if (newsInformation.selftext!!.isNotEmpty()) {
                layout.news_details.visibility = View.VISIBLE
                layout.news_details.text = newsInformation.selftext
            }
        }

        newsInformation.url.let {
            layout.url.visibility = View.VISIBLE
            layout.url.text = newsInformation.url
            layout.url.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(newsInformation.url))
                startActivity(browserIntent)
            }
        }

        return layout
    }

    companion object {
        /**
         * Creates a new instance of NewsDetailFragment to display details of the clicked news article
         *
         * @newsInformation ChildData Contains information about the clicked news article.
         * @return A new instance of fragment NewsDetailFragment.
         */
        @JvmStatic
        fun newInstance(newsInformation: ChildData) =
            NewsDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.CHILD_NEWS_DATA, newsInformation)
                }
            }
    }
}
