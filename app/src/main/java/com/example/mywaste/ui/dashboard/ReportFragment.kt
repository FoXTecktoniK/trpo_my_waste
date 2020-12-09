package com.example.mywaste.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywaste.databinding.FragmentDashboardBinding
import com.example.mywaste.ui.MyDatePickerDialog
import java.util.Calendar

class ReportFragment : Fragment() {

    private val dashboardViewModel by activityViewModels<ReportViewModel>()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerAdapter: WasteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            dashboardViewModel.updateInfo()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter = WasteAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        dashboardViewModel.itemsLiveData.observe(viewLifecycleOwner) { wasteList ->
            recyclerAdapter.submitList(wasteList)
        }
        dashboardViewModel.startDate.observe(viewLifecycleOwner) { calendar ->
            binding.startDateTv.text = formatCalendar(calendar)
        }

        dashboardViewModel.endDate.observe(viewLifecycleOwner) { calendar ->
            binding.endDateTv.text = formatCalendar(calendar)
        }

        binding.startDateTv.setOnClickListener {
            MyDatePickerDialog.forStartDate().show(parentFragmentManager, "datepicker")
        }
        binding.endDateTv.setOnClickListener {
            MyDatePickerDialog().show(parentFragmentManager, "datepicker")
        }
    }

    private fun formatCalendar(calendar: Calendar): String {
        return "${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
            calendar.get(Calendar.YEAR)
        }"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}