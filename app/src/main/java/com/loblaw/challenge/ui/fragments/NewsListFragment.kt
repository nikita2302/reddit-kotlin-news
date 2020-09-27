package com.loblaw.challenge.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.loblaw.challenge.Constants

import com.loblaw.challenge.R
import com.loblaw.challenge.model.ChildData
import com.loblaw.challenge.ui.SimpleDividerItemDecoration
import com.loblaw.challenge.ui.activities.MainActivity
import com.loblaw.challenge.ui.adapters.NewsItemClickListener
import com.loblaw.challenge.ui.adapters.NewsPagedListAdapter
import com.loblaw.challenge.ui.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.view.*

/**
 * A simple NewsListFragment subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNCHECKED_CAST")
class NewsListFragment : Fragment(), NewsItemClickListener {
    private lateinit var newsListViewModel: NewsListViewModel
    private lateinit var layout: View
    private lateinit var _context: Context
    private lateinit var adapter: NewsPagedListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsListViewModel() as T
            }
        }).get(NewsListViewModel::class.java)

        adapter = NewsPagedListAdapter(_context, this)

        //Override the onBackPressed Callback to finish the activity
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as MainActivity).finish()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_news_list, container, false)

        layout.apply {
            news_list_recycler_view.adapter = adapter

            //add divider line between each recyclerView row
            news_list_recycler_view.addItemDecoration(
                SimpleDividerItemDecoration(
                    _context
                )
            )

            refresh_layout.setOnRefreshListener {
                newsListViewModel.refresh()
            }
        }

        setObservers()

        return layout
    }

    /**
     * Function to set all live data observers
     */
    private fun setObservers() {
        newsListViewModel.newsPagedList.observe(viewLifecycleOwner, Observer {
            layout.news_list_progress.visibility = View.GONE
            layout.news_list_recycler_view.visibility = View.VISIBLE

            if (refresh_layout.isRefreshing) {
                refresh_layout.isRefreshing = false
            }

            adapter.submitList(it)
        })

        newsListViewModel.newsPagedListError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                news_list_recycler_view,
                getString(R.string.try_again),
                Snackbar.LENGTH_LONG
            ).show()
        })
    }

    companion object {
        /**
         * Creates new instance of NewsListFragment which display list of Kotlin News
         *
         * @return A new instance of fragment NewsListFragment.
         */
        @JvmStatic
        fun newInstance() =
            NewsListFragment()
    }

    override fun onNewsItemClicked(newsInformation: ChildData) {
        (activity as MainActivity).addNewFragment(
            NewsDetailFragment.newInstance(newsInformation),
            Constants.NEWS_DETAIL_FRAGMENT
        )
    }
}
