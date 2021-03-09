package raum.muchbeer.firebasenotificationktx

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import raum.muchbeer.firebasenotificationktx.databinding.FragmentEggTimerBinding
import raum.muchbeer.firebasenotificationktx.viewmodel.EggTimerViewModel

class EggTimerFragment : Fragment() {

    private val TOPIC = "breakfast"

    private lateinit var binding : FragmentEggTimerBinding
    private lateinit var viewModel: EggTimerViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEggTimerBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)
        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = this

        createChannel(
            getString(R.string.egg_notification_channel_id),
                    getString(R.string.egg_notification_channel_name)
        )
        subscribeTopic()
        return binding.root
    }

    fun subscribeTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener{ task ->
                if(!task.isSuccessful) {

                    val error = getString(R.string.message_subscribe_failed)
                    Log.d("EggFragment", error )
                } else {
                    Toast.makeText(activity, "successful subscribe to : ", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun createChannel(channelId: String, channelName: String) {

        // START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // change importance
                NotificationManager.IMPORTANCE_HIGH
            )//  disable badges for this channel
                .apply {
                    //this allow the notification not to be available when looking on the setting or long pressing the app
                    setShowBadge(false)
                }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }


    }

    companion object {
        fun newInstance() = EggTimerFragment()
    }
}