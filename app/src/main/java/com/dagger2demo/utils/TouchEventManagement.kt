package com.dagger2demo.utils

import android.app.Activity
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TouchEventManagement(appCompatActivity: AppCompatActivity, vararg parentView: View) {
    var context: AppCompatActivity? = appCompatActivity
    var parentView: List<View> = mutableListOf()

    init {
        this.parentView = Arrays.asList(*parentView)
    }

    fun setViewList(vararg parentView: View) {
        this.parentView = Arrays.asList(*parentView)
    }

    fun dispatchTouchEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = context?.currentFocus
            if (v is EditText) {
                clearEdittextFocus(v, event)
            }
        }
        hideSoftKeyboard(context)
    }

    private fun hideSoftKeyboard(activity: Activity?) {
        try {
            val inputMethodManager = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.hideSoftInputFromWindow(getActivityContentView(activity).windowToken, 0)

        } catch (ex: Exception) {
            ///   ex.printStackTrace();
        }

    }

    private fun getActivityContentView(actvity:Activity?): View {
        return actvity?.window?.decorView?.findViewById(android.R.id.content)!!
    }

    private fun clearEdittextFocus(v: View, event: MotionEvent) {
        if (!isToucheEventInViewBoundary( event) && !onButtonTouched(event)) {
            v.clearFocus()
            hideSoftKeyboard(context)
        }
    }

    private fun isToucheEventInViewBoundary(event: MotionEvent): Boolean {
        if (parentView != null) {
            val outRect = Rect()
            for(view in parentView!!) {
                view?.getGlobalVisibleRect(outRect)
                if( outRect.contains(event.rawX.toInt(), event.rawY.toInt()))
                    return true
            }
            return false
        } else
            return false
    }


    private fun onButtonTouched(event: MotionEvent): Boolean {
        return if (isToucheEventInViewBoundary( event)) true else false

    }
}