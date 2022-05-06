package imagetrack.app.trackobject.sealeds

sealed class VIewState< out T> {

    /***OnLoading **/
     object Loading : VIewState<Nothing>()

    /** ONException **/
    data class Error<T>(val throwable : Throwable) :VIewState<Nothing>()

    /*** ON Success ***/
    data class Success<T>(val value : T) : VIewState<T>()




}

