package raum.muchbeer.firebasenotificationktx.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import raum.muchbeer.firebasenotificationktx.MainActivity
import raum.muchbeer.firebasenotificationktx.R
import raum.muchbeer.firebasenotificationktx.receiver.SnoozeReceiver

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
val maneneoMengi = applicationContext.getString(R.string.maneno_mengi)
    val bigTextStyle = NotificationCompat.BigTextStyle().bigText(maneneoMengi)

    val imageIcon = BitmapFactory.decodeResource(
        applicationContext.resources, R.drawable.cooked_egg
    )
    val bigIconStyle = NotificationCompat.BigPictureStyle()
                        .bigPicture(imageIcon)
        .bigLargeIcon(null)


    //SNOOZE TO another one minute
    val snoozeintent = Intent(applicationContext, SnoozeReceiver::class.java)
    val pendingIntentSnooze = PendingIntent.getBroadcast(
        applicationContext,
        REQUEST_CODE,
        snoozeintent,
        FLAGS
    )

    //To support old version of android using
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.egg_notification_channel_id)
    ).setSmallIcon(R.drawable.cooked_egg)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setStyle(bigTextStyle)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
     //   .setLargeIcon(imageIcon) (this set the big Icon if used bigPictureStyle)
      //  .setOnlyAlertOnce(true) -> this is true if new notification override the old one
       // .setContentIntent(snoozeIntent)
       .addAction(
            R.drawable.egg_icon,
            applicationContext.getString(R.string.snooze),
            pendingIntentSnooze
        )

    notify(NOTIFICATION_ID, builder.build())

}

fun NotificationManager.cancelAllNotification() {
    cancelAll()
}