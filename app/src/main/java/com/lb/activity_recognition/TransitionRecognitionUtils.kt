package com.lb.activity_recognition

import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionEvent
import com.google.android.gms.location.DetectedActivity
import java.text.SimpleDateFormat
import java.util.*

object TransitionRecognitionUtils {
    fun createTransitionString(activity: ActivityTransitionEvent): String {
        val theActivity = toActivityString(activity.activityType)
        val transType = toTransitionType(activity.transitionType)
        return ("Transition: "
                + theActivity + " (" + transType + ")" + "   "
                + SimpleDateFormat("HH:mm:ss", Locale.ROOT)
                .format(Date()) + "\n\n")
    }


    private fun toActivityString(activity: Int): String {
        return when (activity) {
            DetectedActivity.STILL -> "STILL"
            DetectedActivity.WALKING -> "WALKING"
            DetectedActivity.ON_BICYCLE -> "ON_BICYCLE"
            DetectedActivity.ON_FOOT -> "ON_FOOT"
            DetectedActivity.IN_VEHICLE -> "IN_VEHICLE"
            DetectedActivity.RUNNING -> "RUNNING"
            else -> "UNKNOWN"
        }
    }

    private fun toTransitionType(transitionType: Int): String {
        return when (transitionType) {
            ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "ENTER"
            ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "EXIT"
            else -> "UNKNOWN"
        }
    }

}
