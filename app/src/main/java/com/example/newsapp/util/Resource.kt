package com.example.newsapp.util

/**
 * Sealed classes are like abstract classes, but allow us to define classes that are allowed to
 * inherit from the sealed class.
 *
 * This generic Resource class lets us define our three types of network responses (success, failure,
 * and loading). We'll use this class when we set up error handling for our network calls.
 */

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    // data not nullable for success, because in that case we're sure we have a non-null response
    class Success<T>(data: T) : Resource<T>(data)
    // data is null here, because sometimes the Body is not empty when we get an error message
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}