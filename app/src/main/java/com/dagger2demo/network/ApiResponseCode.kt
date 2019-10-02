package com.dagger2demo.network

object ApiResponseCode  {
    const val OPERATION_SUCCESSFUL = 100
    const val RECORD_NOT_FOUNT = 101
    const val INVALID_ARGUMENT = 103
    const val INVALID_TRANSACTION_PASSWORD = 104
    const val SECURITY_ERROR = 106
    const val DATABASE_ERROR = 110
    const val RUNTIME_ERROR = 500
    const val REMOTE_ERROR = 502
    const val INTERNAL_CONNECTION_ERROR = 503
    const val AUTHENTICATION_FAILED = 1102
}