package eu.morningbird.buttoncalendar.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.morningbird.buttoncalendar.R
import eu.morningbird.buttoncalendar.adapter.MonthsAdapter
import eu.morningbird.buttoncalendar.adapter.YearsAdapter
import eu.morningbird.buttoncalendar.databinding.ActivityMainBinding
import eu.morningbird.buttoncalendar.model.util.NavigationDirections
import eu.morningbird.buttoncalendar.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    private lateinit var monthsAdapter: MonthsAdapter
    private lateinit var yearsAdapter: YearsAdapter

    private var yearRecyclerViewDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            monthsAdapter.year?.let { year ->
                if (year == Calendar.getInstance().get(Calendar.YEAR)) {
                    yearRecyclerView.smoothScrollToPosition(Calendar.getInstance().get(Calendar.MONTH))
                } else {
                    yearRecyclerView.smoothScrollToPosition(0)
                }
            }
            super.onChanged()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
        initObservers()
    }

    override fun onResume() {
        yearRecyclerView.adapter?.registerAdapterDataObserver(yearRecyclerViewDataObserver)
        super.onResume()
    }

    override fun onPause() {
        yearRecyclerView.adapter?.unregisterAdapterDataObserver(yearRecyclerViewDataObserver)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutMenuItem -> {
                viewModel.aboutOnClick(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initBinding() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initRecyclers()
    }

    private fun initRecyclers() {
        monthsAdapter = MonthsAdapter(this)
        yearRecyclerView.adapter = monthsAdapter
        yearRecyclerView.layoutManager = LinearLayoutManager(this)

        yearsAdapter = YearsAdapter(this)
        yearsSpinner.adapter = yearsAdapter
        yearsSpinner.onItemSelectedListener = viewModel.onYearItemSelected
    }

    private fun initObservers() {
        viewModel.navigationEvent.observe(this,
            Observer { event ->
                event.get()?.let { directions -> navigate(directions) }
            }
        )

        viewModel.months.observe(this,
            Observer { months ->
                months?.let {
                    monthsAdapter.data = it
                }
            }
        )

        viewModel.years.observe(this,
            Observer { years ->
                years?.let {
                    yearsAdapter.data = it
                }
            }
        )

    }

    private fun navigate(directions: NavigationDirections) {
        val intent = Intent(this, directions.destination)
        directions.flags?.let {
            for (flag in it) intent.addFlags(flag)
        }
        directions.bundle?.let {
            intent.putExtras(it)
        }
        startActivity(intent)
        if (directions.shouldFinishCurrentActivity) this.finish()
    }

}
