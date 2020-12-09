package com.example.mywaste.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.mywaste.ui.dashboard.ReportViewModel
import java.util.GregorianCalendar

class MyDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val dashboardViewModel by activityViewModels<ReportViewModel>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(requireContext()).also { it.setOnDateSetListener(this) }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        if (arguments?.getBoolean(START_KEY) == true)
            dashboardViewModel.startDate.value = GregorianCalendar(year, month, dayOfMonth)
        else
            dashboardViewModel.endDate.value = GregorianCalendar(year, month, dayOfMonth)
    }

    companion object {
        private const val START_KEY = "START"
        fun forStartDate() =
            MyDatePickerDialog().also { it.arguments = Bundle().also { it.putBoolean(START_KEY, true) } }
    }
}