package com.amit.weatherapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amit.weatherapp.MainActivity
import com.amit.weatherapp.R
import com.amit.weatherapp.data.Repository
import com.amit.weatherapp.data.local.DatabaseService
import com.amit.weatherapp.data.remote.NetworkService
import com.amit.weatherapp.data.remote.Networking
import com.amit.weatherapp.databinding.FragmentSearchBinding
import com.amit.weatherapp.ui.recyclerview.SharedAdapter
import com.amit.weatherapp.ui.viewmodel.SharedViewModel
import com.amit.weatherapp.util.ViewModelFactory


class SearchFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var _binding: FragmentSearchBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedViewModel = initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = initViewModel()

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerView
        progress = binding.progress
        sharedViewModel = initViewModel()
        sharedViewModel.observeSearch().observe(this, { weather ->
            weather?.let {
                toggleProgress(false)
                recyclerView.adapter = SharedAdapter(listOf(it)) { weatherId ->
                    sharedViewModel.toggleSaved(weatherId)
                }
            }
        })

        return root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView(
            (activity as MainActivity).supportActionBar!!.themedContext
        )
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.actionView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                checkQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    fun checkQuery(query: String) {
        toggleProgress(true)

        if (!Networking.isNetworkConnected(requireActivity().applicationContext)) {
            toggleProgress(false)
            showToast("Internet connection not found.")
            return
        }

        if (query.isBlank()) {
            toggleProgress(false)
            showToast("Please enter a city name.")
            return
        }

        sharedViewModel.search(query, NetworkService.API_KEY) { error ->
            toggleProgress(false)
            showToast(error)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel(): SharedViewModel {
        val model: SharedViewModel by viewModels {
            ViewModelFactory(
                Repository(
                    DatabaseService.getInstance(requireActivity().applicationContext),
                    Networking.create(NetworkService.BASE_URL)
                )
            )
        }
        return model
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel?.observeSearch().removeObservers(this)
    }


    private fun toggleProgress(enable: Boolean) {
        if (enable) {
            progress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}