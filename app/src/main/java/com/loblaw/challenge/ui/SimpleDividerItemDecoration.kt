package com.loblaw.challenge.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.loblaw.challenge.R

/**
 * Helper class to create live division between each recylerView row items
 */
class SimpleDividerItemDecoration(context: Context?) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable? = null

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params =
                child.getLayoutParams() as RecyclerView.LayoutParams
            val top: Int = child.getBottom() + params.bottomMargin
            val bottom = top + (mDivider?.intrinsicHeight ?: 1)
            mDivider?.setBounds(left, top, right, bottom)
            mDivider?.draw(c)
        }
    }

    init {
        mDivider = ContextCompat.getDrawable(context!!,
            R.drawable.line_divider
        )
    }
}