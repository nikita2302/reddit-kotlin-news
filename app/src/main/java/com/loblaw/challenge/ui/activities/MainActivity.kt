package com.loblaw.challenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.loblaw.challenge.Constants
import com.loblaw.challenge.R
import com.loblaw.challenge.ui.fragments.NewsListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.view.*

class MainActivity : AppCompatActivity(){

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        toolbar = tool_bar.toolbar

        addNewFragment(NewsListFragment.newInstance(), Constants.NEWS_LIST_FRAGMENT)
    }

    /**
     * Function to set the title of the app toolbar
     */
    fun setToolBarTitle(title: String) {
        toolbar.top_bar_title.text = title
    }

    /**
     * Function to add a new fragment and insert the old fragment on back stack
     */
    fun addNewFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}
