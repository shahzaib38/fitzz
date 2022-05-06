package imagetrack.app.trackobject.demo

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteCoroutine(val context : Context,val  workerParameters: WorkerParameters) : CoroutineWorker(context ,workerParameters) {




    override suspend fun doWork(): Result {

        println("Coroutine Thread "+Thread.currentThread().name )

        withContext(Dispatchers.IO) {
            println("Coroutine Thread "+Thread.currentThread().name )

      val inputData=       workerParameters.inputData.getString("yes")
            println("Coroutine Thread "+inputData )

        }



        return Result.success(workDataOf("no" to "Bro"))

    }




}
