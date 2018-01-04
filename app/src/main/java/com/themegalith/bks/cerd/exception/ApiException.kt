package com.themegalith.bks.cerd.exception

/**
 * Created by allan on 04/01/18.
 */
class ApiException(message: String?) : Exception(message) {

    private var statusCode : Int? = null

    constructor(statusCode: Int, message: String?) : this(message) {
        this.statusCode = statusCode
    }
}