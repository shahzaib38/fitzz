package imagetrack.app.translate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.*
import java.lang.reflect.Type

class FlowCallAdapter<R>(private val responseType: Type,
                      ) : CallAdapter<R, Flow<R>> {


    override fun responseType(): Type =responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>): Flow<R> = callbackFlow{

        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                close(t) }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.isSuccessful) {
                    offer(response.body()!!)
                } else {
                    close(HttpException(response))
                }
            }
        })

        awaitClose {
            call.cancel() }

    }


}