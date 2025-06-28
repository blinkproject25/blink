package com.example.scamsmsdetector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.widget.Toast

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            val bundle = intent.extras
            val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            for (msg in msgs) {
                val messageBody = msg.messageBody
                val sender = msg.originatingAddress
                if (isScamMessage(messageBody)) {
                    Toast.makeText(context, "⚠️ Scam SMS Detected from $sender", Toast.LENGTH_LONG).show()
                    // You could also start an activity or send a notification
                }
            }
        }
    }

    private fun isScamMessage(message: String): Boolean {
        val scamKeywords = listOf("won", "prize", "click", "free", "urgent", "account", "verify")
        return scamKeywords.any { keyword ->
            message.contains(keyword, ignoreCase = true)
        }
    }
}
