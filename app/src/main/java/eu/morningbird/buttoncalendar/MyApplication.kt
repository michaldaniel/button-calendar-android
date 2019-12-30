package eu.morningbird.buttoncalendar

import android.app.Application
import eu.morningbird.buttoncalendar.database.AppDatabase

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        database = AppDatabase.getInstance(applicationContext)
    }
}
