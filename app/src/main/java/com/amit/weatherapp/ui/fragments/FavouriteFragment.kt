package com.amit.weatherapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.amit.weatherapp.data.Repository
import com.amit.weatherapp.data.local.DatabaseService
import com.amit.weatherapp.data.remote.NetworkService
import com.amit.weatherapp.data.remote.Networking
import com.amit.weatherapp.databinding.FragmentFavouriteBinding
import com.amit.weatherapp.ui.recyclerview.SharedAdapter
import com.amit.weatherapp.ui.viewmodel.SharedViewModel
import com.amit.weatherapp.util.ViewModelFactory

class FavouriteFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var _binding: FragmentFavouriteBinding? = null

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

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView

        sharedViewModel.observeSaved().observe(this, { weatherList ->
            recyclerView.adapter = SharedAdapter(weatherList) { weatherId ->
                sharedViewModel.toggleSaved(weatherId)
            }
        })
        return root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.observeSearch().removeObservers(this)
    }
}