package com.tobibur.workmanagerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.tobibur.workmanagerdemo.services.UploadWorker
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadWorkRequest = PeriodicWorkRequest.Builder(
            UploadWorker::class.java,
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraint)
            .addTag("my_unique_worker")
            .build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_unique_worker",
                ExistingPeriodicWorkPolicy.KEEP, uploadWorkRequest
            );
    }
}
