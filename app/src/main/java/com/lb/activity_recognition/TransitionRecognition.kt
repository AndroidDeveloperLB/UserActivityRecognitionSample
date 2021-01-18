package com.lb.activity_recognition

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.UiThread
import com.google.android.gms.location.*
import java.util.*


class TransitionRecognition(context: Context) {
    private var activityRecognitionClient: ActivityRecognitionClient? = null
    private val pendingIntent: PendingIntent

    init {
        val intent = Intent(context, TransitionRecognitionReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    @UiThread
    fun startTracking(context: Context) {
//        Log.d("AppLog", "TransitionRecognition startTracking")
        if (activityRecognitionClient != null) {
//            Log.d("AppLog", "TransitionRecognition already started tracking")
            return
        }
        val transitions = ArrayList<ActivityTransition>()
        val activityTypes = arrayOf(DetectedActivity.STILL, DetectedActivity.WALKING, DetectedActivity.IN_VEHICLE, DetectedActivity.ON_BICYCLE, DetectedActivity.RUNNING)
        for (activityType in activityTypes) {
            transitions.add(ActivityTransition.Builder()
                    .setActivityType(activityType)
                    .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                    .build())
            transitions.add(ActivityTransition.Builder()
                    .setActivityType(activityType)
                    .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                    .build())
        }
        val request = ActivityTransitionRequest(transitions)
        val activityRecognitionClient = ActivityRecognition.getClient(context)
        this.activityRecognitionClient = activityRecognitionClient
        val task = activityRecognitionClient.requestActivityTransitionUpdates(request, pendingIntent)
        task.addOnSuccessListener {
            Log.d("AppLog", "TransitionRecognition succeeded to start tracking")
        }
        task.addOnFailureListener {
            Log.d("AppLog", "TransitionRecognition failure to start tracking $it")
            stopTracking()
        }
    }

    @UiThread
    fun stopTracking() {
//        Log.d("AppLog", "TransitionRecognition stopTracking")
        activityRecognitionClient?.removeActivityTransitionUpdates(pendingIntent)?.addOnSuccessListener {
            Log.d("AppLog", "removeActivityTransitionUpdates succeeded stopping tracking")
            pendingIntent.cancel()
        }?.addOnFailureListener { e ->
            Log.d("AppLog", "removeActivityTransitionUpdates could not be unregistered: $e")
        }
        activityRecognitionClient = null
    }


}
