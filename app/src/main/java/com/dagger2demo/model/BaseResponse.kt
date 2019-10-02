package com.dagger2demo.model


open class BaseResponse {
    var responseCode = 0
    var responseMessages = arrayListOf<String>()

    fun getCustomMessage(): String {
        var allMessage = ""
        for (model in responseMessages) {
            allMessage = "$model \n"
        }
        if (allMessage.endsWith("\n")) {
            allMessage = allMessage.substring(0, allMessage.length - 1)
        }
        return allMessage
    }
}