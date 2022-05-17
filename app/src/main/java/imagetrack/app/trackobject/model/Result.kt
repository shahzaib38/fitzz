package imagetrack.app.trackobject.model

sealed class Result <out T>( t: T )

data class onSuccess<T>(var t : T): Result<T>(t)
data class onError<T>(var t : T) :  Result<T>(t)
data class onShutDown<T>(var t : T) :  Result<T>(t)

