package eu.morningbird.buttoncalendar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.morningbird.buttoncalendar.R
import eu.morningbird.buttoncalendar.model.DayItem
import eu.morningbird.buttoncalendar.model.repository.DayRepository
import eu.morningbird.buttoncalendar.util.clickWithDebounce
import kotlinx.android.synthetic.main.item_day.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DaysAdapter(private val context: Context, data: MutableList<DayItem>) :
    RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    private var data: MutableList<DayItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun changeItem(position: Int, value: DayItem) {
        data[position] = value
        notifyItemChanged(position, value)
    }

    private fun getItem(position: Int): DayItem {
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payload: List<Any?>) {
        if (payload.isEmpty()) holder.bindView(position)
        else {
            for (load in payload) {
                if (load is DayItem) {
                    holder.bindView(load)
                }
            }
        }
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(position: Int) {
            val item = getItem(position)
            view.dayNameTextView.text = item.name

            view.clickWithDebounce {
                item.day.marked = !item.day.marked
                changeItem(position, item)
                GlobalScope.launch {
                    DayRepository.save(item.day)
                }
            }

            bindView(item)
        }

        fun bindView(item: DayItem) {
            if (item.day.marked) {
                view.dayNameTextView.setTextColor(
                    view.context.resources.getColor(
                        android.R.color.black,
                        view.context.theme
                    )
                )
                view.dayItemConstraintLayout.background =
                    view.context.getDrawable(R.drawable.bg_white_box)
            } else {
                view.dayNameTextView.setTextColor(
                    view.context.resources.getColor(
                        android.R.color.white,
                        view.context.theme
                    )
                )
                view.dayItemConstraintLayout.background =
                    view.context.getDrawable(R.drawable.bg_black_box)
            }
        }
    }

    init {
        this.data = data
    }
}