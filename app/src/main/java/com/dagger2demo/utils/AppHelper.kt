package com.dagger2demo.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object AppHelper {
    val DETAILS_KEY = "DETAILS_KEY"
    var USER_NAME = ""
    val EXTRA_TYPE_SEND_RECEIVE = "sendReceive"
    val EXTRA_TYPE_CASH_IN_OUT = "cashInOut"
    val EXTRA_ADDRESS = "addressLine1"
    val EXTRA_SUCCESS_HEADER = "successHeader"
    var TOKEN: String = ""
    var KYC_STATUS=0
    var USER_PHONE: String = ""
    var USER_IMAGE: String? = null
    const val PROFILE_COMPLETE=1

    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

    fun objectToString(list: List<Any>): ArrayList<String> {
        val result = ArrayList<String>()
        for (item in list) {
            result.add(item.toString())
        }
        return result
    }

    fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }


    fun getLocalIpAddress(): String {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return ""
    }

    const val SECOND_TIME_LOGIN="SECOND_TIME_LOGIN"
    const val LOGIN_PHONE="LOGIN_PHONE"
    const val MOBILE_TOKEN="MOBILE_TOKEN"
    const val PIN_USER_NAME = "PIN_USER_NAME"
    const val LOGIN_TYPE = "LOGIN_TYPE"
    const val LAST_LOGIN = "LAST_LOGIN"
    const val IS_FORGET_PIN = "IS_FORGET_PIN"
    const val MOBILE_NUMBER_LENGTH =11

}