package imagetrack.app.image_processing

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean


//Decorated Class
class ScopedExecutor(private val executor: Executor) : Executor {

    private val shutdown = AtomicBoolean()

    override fun execute(command: Runnable) {
   if (shutdown.get()) {
      return;
    }

        executor.execute {
            command.run()
        }

    }

    fun shutdown(){

        shutdown.set(true)
    }


    companion object{

        private var INSTANCE  : ScopedExecutor?=null

        fun getInstance(executor : Executor):ScopedExecutor?{
            if(INSTANCE== null){
                INSTANCE  = ScopedExecutor(executor) }

            return INSTANCE
        }

    }

}