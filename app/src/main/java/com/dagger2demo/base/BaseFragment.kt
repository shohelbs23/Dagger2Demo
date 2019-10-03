package com.dagger2demo.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dagger2demo.callback.CommunicatorFragmentInterface
import dagger.android.support.AndroidSupportInjection


open class BaseFragment : Fragment() {

    fun gotoNewActivityWithClearActivity(activityClass: Class<*>) {
        activity?.let {
            if (activity?.javaClass?.simpleName != activityClass.simpleName) {
                val intent = Intent(activity, activityClass)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }

    protected fun gotoNewActivityWithOutChecking(activityClass: Class<*>, bundle: Bundle) {
        activity?.let {
            val intent = Intent(activity, activityClass)
            intent.putExtras(bundle)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    protected fun gotoNewActivityWithClearActivity(activityClass: Class<*>, bundle: Bundle) {
        activity?.let {
            if (activity!!.javaClass != activityClass) {
                val intent = Intent(activity, activityClass)
                intent.putExtras(bundle)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }


    var myCommunicator: CommunicatorFragmentInterface? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        try {
            myCommunicator = context as CommunicatorFragmentInterface?
        } catch (e: ClassCastException) {
//            throw ClassCastException(context?.toString() + " must implement OnFragmentInteractionListener")
        }

    }



}