package eu.morningbird.buttoncalendar.database.dao

import androidx.room.Dao
import androidx.room.Query
import eu.morningbird.buttoncalendar.model.Day

@Dao
interface DayDao : BaseDao<Day> {
    @Query("select * from day where year == :year and day == :day limit 1")
    suspend fun get(year: Int, day: Int): Day?

    @Query("select * from day group by year order by year desc")
    suspend fun getGroupedByYear(): List<Day>?
}