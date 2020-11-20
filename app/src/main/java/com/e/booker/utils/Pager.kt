package com.e.booker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class Pager: ViewPager {
    private var swipable = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        swipable = true
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (swipable) {
            super.onTouchEvent(event)
        } else false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (swipable) super.onInterceptTouchEvent(ev) else false
    }

    fun setSwipable(swipe: Boolean) {
        swipable = swipe
    }

}