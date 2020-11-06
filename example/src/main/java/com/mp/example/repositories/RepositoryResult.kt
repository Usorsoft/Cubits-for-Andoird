package com.mp.example.repositories


/**
 * Represents an repository result. Please check if result is of type [Success] or [Failure].
 */
sealed class RepositoryResult<VALUE>(val statusCode: Int)
class Success<V>(statusCode: Int, val value: V): RepositoryResult<V>(statusCode)
class Failure<V>(statusCode: Int, val errorMessage: String? = null): RepositoryResult<V>(statusCode)