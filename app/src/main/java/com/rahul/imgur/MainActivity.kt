package com.rahul.imgur

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahul.imgur.ui.adapters.ImagesListAdapter
import com.rahul.imgur.ui.main.MainActivityViewModel
import com.rahul.imgur.ui.utils.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_header.*
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainActivity : AppCompatActivity() {
    private val liveViewModel: MainActivityViewModel by stateViewModel()
    private lateinit var imagesAdapter: ImagesListAdapter
    private lateinit var managerGrid: GridLayoutManager
    private lateinit var managerLinear: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resources.displayMetrics.getScreenSize()
        setContentView(R.layout.activity_main)
        initAdapterGrid()
        initObserversGrid()
        initSearch()
        initSettings()
    }

    //initializer
    private fun initAdapterGrid() {
        val glide = com.rahul.imgur.ui.utils.GlideApp.with(this)
        imagesAdapter = ImagesListAdapter(glide, Constants.GRID_VIEW, {
            liveViewModel.retry()
        }) {
        }
        managerGrid = getGridLayoutManager(ORIENTATION_PORTRAIT, imagesAdapter)
        recyclerView.apply {
            layoutManager = managerGrid
            adapter = imagesAdapter
        }
    }

    private fun initAdapterLinear() {
        val glide = com.rahul.imgur.ui.utils.GlideApp.with(this)
        imagesAdapter = ImagesListAdapter(glide, Constants.LIST_VIEW, {
            liveViewModel.retry()
        }) {
        }
        managerLinear = getLinearLayoutManager(ORIENTATION_PORTRAIT, imagesAdapter)
        recyclerView.apply {
            layoutManager = managerLinear
            adapter = imagesAdapter
        }
    }

    /*
    * observes the posts data retrieved from [CustomBoundaryCallback] methods
    * called when the data in database is finished
    */
    private fun initObserversGrid() {
        liveViewModel.posts.observe(this) { it ->
            imagesAdapter.submitList(it) {
                // Workaround for an issue where RecyclerView incorrectly uses the loading / spinner
                // item added to the end of the list as an anchor during initial load.
                val layoutManager = managerGrid
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    recyclerView.scrollToPosition(position)
                }
            }
        }
        liveViewModel.networkState.observe(this) { it ->
            imagesAdapter.setNetworkState(it)
        }
    }

    private fun initObserversLinear() {
        liveViewModel.posts.observe(this) { it ->
            imagesAdapter.submitList(it) {
                // Workaround for an issue where RecyclerView incorrectly uses the loading / spinner
                // item added to the end of the list as an anchor during initial load.
                val layoutManager = managerLinear
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    recyclerView.scrollToPosition(position)
                }
            }
        }
        liveViewModel.networkState.observe(this) { it ->
            imagesAdapter.setNetworkState(it)
        }
    }


    private fun initSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText.debounce {
                    updatedSearchFromInput()
                }
                return false
            }
        })
        searchView.setQuery("nature", true)
    }

    /*
    * caching the Search keywords in [SavedStateHandle] as key value pairs
    * so that they can be retrieved locally
     */
    private fun updatedSearchFromInput() {
        searchView.query.trim().toString().let {
            if (it.isNotEmpty()) {
                if (liveViewModel.showSearchedContent(it)) {
                    recyclerView.scrollToPosition(0)
                    (recyclerView.adapter as? ImagesListAdapter)?.submitList(null)
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        var manager = getGridLayoutManager(newConfig.orientation, imagesAdapter)
        recyclerView.layoutManager = manager
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    private fun initSettings() {
        toggleButton.setOnClickListener {

            if (toggleButton.isChecked) {
                initAdapterLinear()
                initObserversLinear()
            } else {

                initAdapterGrid()
                initObserversGrid()
            }
        }
    }

}