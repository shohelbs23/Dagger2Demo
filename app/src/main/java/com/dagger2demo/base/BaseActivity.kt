package com.dagger2demo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.app.Dialog
import android.content.Intent
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.dagger2demo.R
import com.dagger2demo.utils.TouchEventManagement
import dagger.android.AndroidInjection


open class BaseActivity : AppCompatActivity() {

    protected var touchEventManagement: TouchEventManagement? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }

    private fun getActivityContentView(actvity: Activity?): View {
        return actvity?.window?.decorView?.findViewById(android.R.id.content)!!

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        touchEventManagement = TouchEventManagement(this, getActivityContentView(this))
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        touchEventManagement?.dispatchTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    protected fun gotoNewActivity(activityClass: Class<*>) {
        if (this.javaClass != activityClass) {
            val intent = Intent(this, activityClass)
            startActivity(intent)
        }

    }

    fun gotoNewActivityWithClearActivity(activityClass: Class<*>) {
        if (this.javaClass != activityClass) {
            val intent = Intent(this, activityClass)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
//            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
        }
    }

    protected fun gotoNewActivityWithClearActivity(activityClass: Class<*>, bundle: Bundle) {
        if (this.javaClass != activityClass) {
            val intent = Intent(this, activityClass)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

    fun getIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress)
    }

    companion object {
        fun get(activity: Activity): BaseActivity = activity as BaseActivity
    }

    fun clearBackStackInclusive(tag: String) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun showExitDialog() {
        val dialogs = Dialog(this)
        dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogs.setCancelable(false)
        dialogs.setContentView(R.layout.exit_dialogue)
        val body = dialogs.findViewById(R.id.body) as TextView
        body.text = getString(R.string.are_you_want_exit)
        val yesBtn = dialogs.findViewById(R.id.yesBtn) as Button
        val noBtn = dialogs.findViewById(R.id.noBtn) as TextView
        yesBtn.setOnClickListener {
            dialogs.dismiss()
            finish()
        }
        noBtn.setOnClickListener {
            dialogs.dismiss()
        }
        dialogs.show()

    }
}