package com.lb.activity_recognition

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.ActivityTransitionResult

class TransitionRecognitionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("AppLog", "TransitionRecognitionReceiver onReceive")
//        if (ActivityRecognitionResult.hasResult(intent)) {
//            ActivityRecognitionResult.extractResult(intent)?.let { extractResult ->
//                Log.d("AppLog", "TransitionRecognitionReceiver ActivityRecognitionResult:${extractResult.toString()}")
//                val mostProbableActivity = extractResult.mostProbableActivity ?: return
//                liveData.value = mostProbableActivity.toString()
//            }
//        }
        if (ActivityTransitionResult.hasResult(intent)) {
            ActivityTransitionResult.extractResult(intent)?.let { extractResult ->
                val sb = StringBuilder()
                for (event in extractResult.transitionEvents) {
                    Log.d("AppLog", "TransitionRecognitionReceiver ActivityTransitionResult: $event")
                    sb.append(TransitionRecognitionUtils.createTransitionString(event))
                }
                transitionRecognitionLiveData.value = sb.toString()
            }
        }
    }

    companion object {
        val transitionRecognitionLiveData = MutableLiveData<String>()
    }
}
