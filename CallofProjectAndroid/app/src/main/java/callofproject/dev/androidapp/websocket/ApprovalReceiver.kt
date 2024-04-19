package callofproject.dev.androidapp.websocket

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ApprovalReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val action = intent.action
            if (action != null) {
                when (action) {
                    "APPROVE_ACTION" -> {
                        Toast.makeText(context, "Bildirim onaylandı", Toast.LENGTH_SHORT).show()
                    }

                    "REJECT_ACTION" -> {
                        Toast.makeText(context, "Bildirim reddedildi", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}