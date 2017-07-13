package com.grid.appy.citizenrights.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.grid.appy.citizenrights.R;


public class DatePickerFragment  extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    TextView datepicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        datepicker= (TextView)getActivity().findViewById(R.id.datepicker);
        datepicker.setText(day+"/"+(month+1)+"/"+year);
    }
}