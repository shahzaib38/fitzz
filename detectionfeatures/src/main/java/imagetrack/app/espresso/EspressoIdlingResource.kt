package imagetrack.app.espresso

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    const val  EspressoName = "EspressoIdling"

    val idlingResouces = CountingIdlingResource(EspressoName)

    fun increment(){
        idlingResouces.increment()
        idlingResouces.dumpStateToLogs()               // Prints the current state of this resource to the logcat at info level.
        println("decremented ") }

    fun decrement(){

        if(!idlingResouces.isIdleNow){
            try {
                idlingResouces.decrement()
                idlingResouces.dumpStateToLogs()      // Prints the current state of this resource to the logcat at info level.
                println("decremented ")
            }catch(illegalStateException  :IllegalStateException){
                println("${illegalStateException.message}")
            } }
    }


    fun getName() :String {
        return idlingResouces.name
    }



}