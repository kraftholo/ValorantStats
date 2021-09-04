package com.sk.core.util

class Logger(
    private val tag: String,
    private val isDebug: Boolean = true
) {

    companion object Factory {

        fun buildDebug(tag: String): Logger {
            return Logger(tag, true)
        }

        fun buildRelease(tag: String): Logger {
            return Logger(tag, false)
        }
    }


    fun log(msg: String) {
        if (isDebug) {
            printLogD(tag, msg);
        } else {
            //Production logging -> Use crashlytics
        }
    }

    fun printLogD(tag: String, message: String) {
        println("$tag: $message");
    }


}