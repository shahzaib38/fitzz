package imagetrack.app.trackobject.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadCastReceiver : BroadcastReceiver(){




    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent!=null){

            val action = intent.action

            if("com.test.APP_SEND" == action){
                Toast.makeText(context!! ,"Action ",Toast.LENGTH_LONG).show()
            }














        }





    }


}