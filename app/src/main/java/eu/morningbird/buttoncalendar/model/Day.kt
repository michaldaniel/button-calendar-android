package eu.morningbird.buttoncalendar.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "day",
    indices = [Index(value = ["id"], unique = true), Index(value = ["year", "day"], unique = true)]
)
data class Day(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "day")
    var day: Int,
    @ColumnInfo(name = "order")
    var marked: Boolean
)