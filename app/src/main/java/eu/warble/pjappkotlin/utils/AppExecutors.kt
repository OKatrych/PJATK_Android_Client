package eu.warble.pjappkotlin.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(private val diskIO: Executor, private val networkIO: Executor, private val mainThread: Executor) {

    // For Singleton instantiation and task management
    companion object {
        private val INSTANCE = AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3), MainThreadExecutor())

        fun DISK() = INSTANCE.diskIO
        fun NETWORK() = INSTANCE.networkIO
        fun MAIN() = INSTANCE.mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}