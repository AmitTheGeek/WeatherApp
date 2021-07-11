package com.amit.weatherapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.amit.weatherapp.MainActivity
import com.amit.weatherapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sharedPref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val switch: SwitchCompat = binding.switchButton

        sharedPref = activity?.getSharedPreferences(
            MainActivity.PREF_NAME,
            MainActivity.PRIVATE_MODE
        )!!

        switch.isChecked = sharedPref.getBoolean(MainActivity.DARK_MODE_ENABLED, false)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            setDarkMode(isChecked)
            val editor = sharedPref.edit()
            editor.putBoolean(MainActivity.DARK_MODE_ENABLED, isChecked).apply()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDarkMode(boolean: Boolean) {
        if (boolean) {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES);
        } else {
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO);
        }
    }
}