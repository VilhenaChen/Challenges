package pt.vilhena.topmusicalbunschallenge.data.model.dto


// Wrapper for network error handling
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(
        val message: String,
        val code: Int? = null
    ) : NetworkResult<Nothing>()

    object Loading : NetworkResult<Nothing>()
}