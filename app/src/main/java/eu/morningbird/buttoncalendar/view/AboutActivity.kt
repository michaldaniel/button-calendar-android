package eu.morningbird.buttoncalendar.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import eu.morningbird.buttoncalendar.R
import eu.morningbird.buttoncalendar.adapter.AboutsAdapter
import eu.morningbird.buttoncalendar.databinding.ActivityAboutBinding
import eu.morningbird.buttoncalendar.model.util.NavigationDirections
import eu.morningbird.buttoncalendar.viewmodel.AboutViewModel
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    private lateinit var viewModel: AboutViewModel

    private lateinit var appInfoAboutsAdapter: AboutsAdapter
    private lateinit var developerInfoAboutsAdapter: AboutsAdapter
    private lateinit var supportInfoAboutsAdapter: AboutsAdapter
    private lateinit var legalInfoAboutsAdapter: AboutsAdapter
    private lateinit var attributionsInfoAboutsAdapter: AboutsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
        initObservers()
    }

    private fun initBinding() {
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        initRecyclers()
    }

    private fun initRecyclers() {
        appInfoAboutsAdapter =
            AboutsAdapter(this)
        appInfoRecyclerView.adapter = appInfoAboutsAdapter
        appInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        developerInfoAboutsAdapter =
            AboutsAdapter(this)
        developerInfoRecyclerView.adapter = developerInfoAboutsAdapter
        developerInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        supportInfoAboutsAdapter =
            AboutsAdapter(this)
        supportInfoRecyclerView.adapter = supportInfoAboutsAdapter
        supportInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        legalInfoAboutsAdapter =
            AboutsAdapter(this)
        legalInfoRecyclerView.adapter = legalInfoAboutsAdapter
        legalInfoRecyclerView.layoutManager = LinearLayoutManager(this)

        attributionsInfoAboutsAdapter =
            AboutsAdapter(this)
        attributionsInfoRecyclerView.adapter = attributionsInfoAboutsAdapter
        attributionsInfoRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initObservers() {
        viewModel.navigationEvent.observe(this,
            Observer { event ->
                event.get()?.let { directions -> navigate(directions) }
            }
        )

        viewModel.appInfoAboutItems.observe(this, Observer { items ->
            items?.let {
                appInfoAboutsAdapter.data = it
            }
        })
        viewModel.developerInfoAboutItems.observe(this, Observer { items ->
            items?.let {
                developerInfoAboutsAdapter.data = it
            }
        })
        viewModel.supportInfoAboutItems.observe(this, Observer { items ->
            items?.let {
                supportInfoAboutsAdapter.data = it
            }
        })
        viewModel.legalInfoAboutItems.observe(this, Observer { items ->
            items?.let {
                legalInfoAboutsAdapter.data = it
            }
        })
        viewModel.attributionsInfoAboutItems.observe(this, Observer { items ->
            items?.let {
                attributionsInfoAboutsAdapter.data = it
            }
        })
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
