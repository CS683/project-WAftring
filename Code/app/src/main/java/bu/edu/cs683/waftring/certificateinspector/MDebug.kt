package bu.edu.cs683.waftring.certificateinspector

import android.util.Log

public class MDebug {
    companion object _MDebug {
        private val TAG = "MDEBUG"
        private val CALLER_INDEX = 3
        public fun enter() {
            //val className = Thread.currentThread().stackTrace[CALLER_INDEX].className
            val methodName = Thread.currentThread().stackTrace[CALLER_INDEX].methodName
            Log.d(TAG, "Enter " + methodName)
        }

        public fun exit() {
            //val className = Thread.currentThread().stackTrace[CALLER_INDEX].className
            val methodName = Thread.currentThread().stackTrace[CALLER_INDEX].methodName
            Log.d(TAG, "Exit " + methodName)
        }
    }
}