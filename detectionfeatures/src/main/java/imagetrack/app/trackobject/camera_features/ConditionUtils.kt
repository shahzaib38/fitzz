package imagetrack.app.trackobject.camera_features

object ConditionUtils {


    private fun exception(errorMessage: String){
        throw NullPointerException(errorMessage) }

    fun <T> checkNotNull(reference : T , errorMessage : String) : T{
        if (reference == null) {
            exception(errorMessage) }
        return reference }

    fun   checkIsArrayEmpty(size :  Int  ,  errorMessage : String){
        if(size < 0){
            exception(errorMessage) } }




}