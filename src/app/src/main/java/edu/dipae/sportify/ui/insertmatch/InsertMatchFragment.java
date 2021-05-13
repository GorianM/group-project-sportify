package edu.dipae.sportify.ui.insertmatch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import edu.dipae.sportify.R;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.utils.DateUtils;

public class InsertMatchFragment extends Fragment
{

    private boolean isCalendarVisible;
    private boolean isClockVisible;
    private InsertMatchViewModel mViewModel;
    private View rootView;

    public static InsertMatchFragment newInstance() {
        return new InsertMatchFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        isCalendarVisible = false;
        isClockVisible = false;

        // The root view
        this.rootView =  inflater.inflate(R.layout.fragment_insert_match, container, false);

        TimePicker timePicker = rootView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        ImageButton imageButton = rootView.findViewById(R.id.btn_toggle_calendar);
        imageButton.setOnClickListener(
                v -> toggleCalendar(v)
        );

        DatePicker datePicker = rootView.findViewById(R.id.date_picker);
        datePicker.setOnDateChangedListener(
                (view, year, monthOfYear, dayOfMonth) -> setMatchDate(view, year, monthOfYear, dayOfMonth)
        );

        imageButton = rootView.findViewById(R.id.btn_toggle_time_picker);
        imageButton.setOnClickListener(
                v -> toggleClock(v)
        );

        Button button = rootView.findViewById(R.id.btn_save_time);
        button.setOnClickListener(
                v -> setMatchTime(v)
        );
        return this.rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsertMatchViewModel.class);
        // TODO: Use the ViewModel
    }

    public void toggleCalendar(View view)
    {
        isCalendarVisible=!isCalendarVisible;
        TableRow row = rootView.findViewById(R.id.row_datepicker);
        row.setVisibility(isCalendarVisible ? View.VISIBLE : View.GONE);
    }

    public void setMatchDate(View view,int year,int monthOfYear,int dayOfMonth)
    {
        Date date = mViewModel.getMatchDate();

        Calendar c = Calendar.getInstance();
        // The date object holds both TIME AND DATE
        // that is why we read what we hold in the variable
        // to avoid erasing the other part
        c.setTime(date);
        c.set(year, monthOfYear, dayOfMonth);

        Match model = this.mViewModel.setMatchDate(c.getTime());

        EditText editText = this.rootView.findViewById(R.id.edt_match_date);
        editText.setText(DateUtils.dateToString(model.getDate()));

        toggleCalendar(null);
    }

    public void toggleClock(View view)
    {
        isClockVisible=!isClockVisible;
        TableRow row = rootView.findViewById(R.id.row_timepicker);
        row.setVisibility(isClockVisible ? View.VISIBLE : View.GONE);
    }

    public void setMatchTime(View view)
    {
        Date date = mViewModel.getMatchDate();

        Calendar c = Calendar.getInstance();
        // The date object holds both TIME AND DATE
        // that is why we read what we hold in the variable
        // to avoid erasing the other part
        c.setTime(date);

        TimePicker timePicker = rootView.findViewById(R.id.time_picker);
        int hours = timePicker.getHour();
        int minutes = timePicker.getMinute();

        c.set(Calendar.HOUR_OF_DAY, hours);
        c.set(Calendar.MINUTE, minutes);

        Match model = this.mViewModel.setMatchDate(c.getTime());

        EditText editText = this.rootView.findViewById(R.id.edt_match_time);
        editText.setText(DateUtils.timeToString(model.getDate()));

        toggleClock(null);
    }

    public void saveMatch(View v)
    {
        EditText edtName = this.rootView.findViewById(R.id.edt_match_name);
        String name = edtName.getText().toString();
        EditText edtCity = this.rootView.findViewById(R.id.edt_match_name);
        String city = edtCity.getText().toString();
        EditText edtCountry = this.rootView.findViewById(R.id.edt_match_name);
        String country = edtCountry.getText().toString();
//        this.mViewModel.set();
    }
}