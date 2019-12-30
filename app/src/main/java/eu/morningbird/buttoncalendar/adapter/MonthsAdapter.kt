package eu.morningbird.buttoncalendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import eu.morningbird.buttoncalendar.R
import eu.morningbird.buttoncalendar.model.DayItem
import eu.morningbird.buttoncalendar.model.MonthItem
import kotlinx.android.synthetic.main.item_month.view.*

class MonthsAdapter(private val context: Context) :
    RecyclerView.Adapter<MonthsAdapter.ViewHolder>() {

    val year: Int?
        get() {
            return if (data.isNotEmpty()) data.first().first.year
            else null
        }

    var data: List<Pair<MonthItem, MutableList<DayItem>>> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun getItem(position: Int): Pair<MonthItem, MutableList<DayItem>> {
        return data[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_month, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(position: Int) {
            val item = getItem(position)
            view.monthNameTextView.text = item.first.name
            val animator: SimpleItemAnimator = object : DefaultItemAnimator() {
                override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder): Boolean {
                    return true
                }
            }
            val adapter = DaysAdapter(
                view.context,
                item.second
            )
            view.monthRecyclerView.setHasFixedSize(true)
            view.monthRecyclerView.adapter = adapter
            view.monthRecyclerView.itemAnimator = animator
            view.monthRecyclerView.layoutManager = GridLayoutManager(view.context, 7)

        }
    }
}