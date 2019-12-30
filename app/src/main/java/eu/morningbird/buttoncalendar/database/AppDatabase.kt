package eu.morningbird.buttoncalendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eu.morningbird.buttoncalendar.database.dao.DayDao
import eu.morningbird.buttoncalendar.model.Day

@Database(
    entities = [Day::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao

    companion object {
        private const val DATABASE_NAME = "application_db"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}