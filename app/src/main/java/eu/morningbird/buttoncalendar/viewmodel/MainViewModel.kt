package eu.morningbird.buttoncalendar.viewmodel


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.morningbird.buttoncalendar.model.DayItem
import eu.morningbird.buttoncalendar.model.MonthItem
import eu.morningbird.buttoncalendar.model.repository.DayRepository
import eu.morningbird.buttoncalendar.model.util.Event
import eu.morningbird.buttoncalendar.model.util.NavigationDirections
import eu.morningbird.buttoncalendar.view.AboutActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {
    val navigationEvent: MutableLiveData<Event<NavigationDirections>> = MutableLiveData()
    val calendarIsLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val yearsAreLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val months: MutableLiveData<List<Pair<MonthItem, MutableList<DayItem>>>> = MutableLiveData()
    val years: MutableLiveData<List<Int>> = MutableLiveData()

    private suspend fun getYears(): List<Int> {
        val data: MutableList<Int> = ArrayList()
        val years = DayRepository.getYears()
        years?.let {
            for (year in it) {
                data.add(year.year)
            }
        }

        if (!data.contains(Calendar.getInstance().get(Calendar.YEAR))) data.add(
            0,
            Calendar.getInstance().get(Calendar.YEAR)
        )

        return data
    }

    private suspend fun getMonths(year: Int): List<Pair<MonthItem, MutableList<DayItem>>> {
        val start: Calendar = Calendar.getInstance()
        start.set(year, 0, 1, 0, 0, 0)

        val months: MutableList<MonthItem> = ArrayList()
        while (start.get(Calendar.YEAR) == year) {
            val name =
                start.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)?.let { it } ?: run {
                    start.get(
                        Calendar.MONTH
                    ).toString()
                }
            months.add(MonthItem(name, year, start.get(Calendar.MONTH)))
            start.add(Calendar.MONTH, 1)
        }
        val data: MutableList<Pair<MonthItem, MutableList<DayItem>>> = ArrayList()
        for (month in months) {
            val days = getDays(month)
            data.add(Pair(month, days.toMutableList()))
        }
        return data
    }

    private suspend fun getDays(monthItem: MonthItem): List<DayItem> {
        val start: Calendar = Calendar.getInstance()
        start.set(monthItem.year, monthItem.month, 1, 0, 0, 0)

        val days: MutableList<DayItem> = ArrayList()
        while (start.get(Calendar.MONTH) == monthItem.month) {
            days.add(
                DayItem(
                    start.get(Calendar.DAY_OF_MONTH).toString(), DayRepository.get(
                        start.get(
                            Calendar.YEAR
                        ), start.get(Calendar.DAY_OF_YEAR)
                    )
                )
            )
            start.add(Calendar.DAY_OF_YEAR, 1)
        }
        return days
    }

    private var currentYear: Int? = null
    fun updateCalendar(year: Int) {
        if (currentYear == year) return
        calendarIsLoading.postValue(true)
        GlobalScope.launch {
            val months = getMonths(year)
            this@MainViewModel.months.postValue(months)
            calendarIsLoading.postValue(false)
        }
    }

    fun updateYears() {
        yearsAreLoading.postValue(true)
        GlobalScope.launch {
            val years = getYears()
            this@MainViewModel.years.postValue(years)
            yearsAreLoading.postValue(false)
        }
    }

    fun aboutOnClick(context: Context) {
        val intent = Intent(context, AboutActivity::class.java)
        navigationEvent.postValue(
            Event(
                NavigationDirections(
                    intent,
                    AboutActivity::class.java,
                    null,
                    null,
                    false
                )
            )
        )
    }

    val onYearItemSelected = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            /* Do nothing */
        }

        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position) as Int
            updateCalendar(item)
        }

    }

    init {
        updateYears()
        updateCalendar(Calendar.getInstance().get(Calendar.YEAR))
    }

}