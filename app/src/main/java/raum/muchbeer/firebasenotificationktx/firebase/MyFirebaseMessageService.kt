package raum.muchbeer.firebasenotificationktx.firebase

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import raum.muchbeer.firebasenotificationktx.util.sendNotification

const val TAG ="MyFirebaseMessageServi"

class MyFirebaseMessageService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i(TAG, "From: ${remoteMessage?.from}")


       /*  remoteMessage?.data?.let {
             //this will be triggered when a user  clicked the message
             Log.d(TAG, "tHE notifition payload is: ${remoteMessage.data}")
         }*/

        remoteMessage.notification?.let {
            sendNotification(it.body!!)
        }
    }

    private fun sendNotification(body: String?) {
        val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
        notificationManager?.sendNotification(body!!, applicationContext)
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.i(TAG, "RefreshToken : ${token}")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }
}