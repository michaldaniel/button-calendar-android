package eu.morningbird.buttoncalendar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import eu.morningbird.buttoncalendar.R
import kotlinx.android.synthetic.main.item_year.view.*

class YearsAdapter(private val context: Context) : BaseAdapter(), SpinnerAdapter {

    var data: List<Int> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_year, parent, false)
        view.yearNameTextView.text = getItem(position).toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_year_dropdown, parent, false)
        view.yearNameTextView.text = getItem(position).toString()
        return view
    }

    override fun getItem(position: Int): Int {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}
