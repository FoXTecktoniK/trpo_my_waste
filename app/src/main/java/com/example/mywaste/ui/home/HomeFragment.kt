package com.example.mywaste.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mywaste.R
import com.example.mywaste.databinding.FragmentHomeBinding
import com.example.mywaste.domain.CategoryModel
import com.example.mywaste.domain.WasteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = binding.currencySpinner
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currencies_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        binding.fab.setOnClickListener {

            createWaste()
            binding.categoryEditText.setText("")
            binding.shopEditText.setText("")
            binding.totalEditText.setText("")

            Toast.makeText(requireContext(), "Трата сохранена", Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launchWhenCreated {
            updateCategoriesSuggest()
        }
    }

    private fun createWaste() {
        if (checkWasteData())
            return

        val calendar =
            GregorianCalendar(
                binding.datePicker.year,
                binding.datePicker.month,
                binding.datePicker.dayOfMonth
            )
        val model = WasteModel(
            binding.categoryEditText.text.toString(),
            binding.shopEditText.text.toString(),
            binding.totalEditText.text.toString().toFloat(),
            binding.currencySpinner.selectedItem.toString(),
            calendar
        )
        lifecycleScope.launch(Dispatchers.IO) {
            model.insert()
            updateCategoriesSuggest()
        }
    }

    private fun checkWasteData(): Boolean {
        var badData = false
        if (binding.categoryEditText.text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название категории", Toast.LENGTH_SHORT)
                .show()
            badData = true
        }

        if (binding.shopEditText.text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите название магазина", Toast.LENGTH_SHORT).show()
            badData = true
        }

        if (binding.totalEditText.text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите сумму", Toast.LENGTH_SHORT).show()
            badData = true
        }
        if (binding.totalEditText.text.toString().toFloat().compareTo(0) <= 0) {
            Toast.makeText(requireContext(), "Сумма должна быть больше 0", Toast.LENGTH_SHORT)
                .show()
            badData = true
        }
        val calendar =
            GregorianCalendar(
                binding.datePicker.year,
                binding.datePicker.month,
                binding.datePicker.dayOfMonth
            )
        if (calendar.after(GregorianCalendar())) {
            Toast.makeText(
                requireContext(),
                "Дата покупки должна быть не позднее сегодняшнего дня",
                Toast.LENGTH_SHORT
            ).show()
            badData = true
        }
        return badData
    }

    private suspend fun updateCategoriesSuggest() {
        val names = withContext(Dispatchers.IO) {
            CategoryModel.getAll()
                .map(CategoryModel::name)
        }
        withContext(Dispatchers.Main) {
            val adapter = ArrayAdapter<String>(
                requireContext(), android.R.layout.simple_list_item_1,
                names
            )
            binding.categoryEditText.threshold = 0
            binding.categoryEditText.setAdapter(adapter)
            binding.categoryEditText.showDropDown()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}