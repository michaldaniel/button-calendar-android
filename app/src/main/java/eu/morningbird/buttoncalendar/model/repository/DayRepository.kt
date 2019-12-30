package eu.morningbird.buttoncalendar.model.repository

import eu.morningbird.buttoncalendar.MyApplication
import eu.morningbird.buttoncalendar.model.Day

object DayRepository {

    suspend fun save(day: Day) {
        day.id?.let {
            MyApplication.database.dayDao().update(day)
        } ?: run {
            MyApplication.database.dayDao().insert(day)
        }
    }

    suspend fun get(year: Int, day: Int): Day {
        return MyApplication.database.dayDao().get(year, day)?.let {
            it
        } ?: run {
            Day(null, year, day, false)
        }
    }

    suspend fun getYears(): List<Day>? {
        return MyApplication.database.dayDao().getGroupedByYear()
    }

}