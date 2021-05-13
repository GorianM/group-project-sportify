package edu.dipae.sportify.ui.insertmatch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import edu.dipae.sportify.R;
import edu.dipae.sportify.adapters.KeyPairSpinnerAdapter;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
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
        temporaryRows = new ArrayList<>();

        // The root view
        this.rootView =  inflater.inflate(R.layout.fragment_insert_match, container, false);

        TimePicker timePicker = rootView.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        DatePicker datePicker = rootView.findViewById(R.id.date_picker);
        datePicker.setOnDateChangedListener(
                (view, year, monthOfYear, dayOfMonth) -> setMatchDate(view, year, monthOfYear, dayOfMonth)
        );

        this.setButtonListeners();

        Spinner spinner = rootView.findViewById(R.id.sel_match_sport);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        onSportChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

        return this.rootView;
    }

    private void setSpinnerAdapters()
    {
        ArrayList<Sport> sports = mViewModel.getAllSports();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        sports.forEach(
                sport -> data.add(new ArrayList<String>(Arrays.asList("" + sport.getId(), sport.getName())))
        );

        Spinner spinner = rootView.findViewById(R.id.sel_match_sport);
        spinner.setAdapter(new KeyPairSpinnerAdapter(this.getContext(), data));
    }

    private void setButtonListeners()
    {
        ImageButton imageButton = rootView.findViewById(R.id.btn_toggle_calendar);
        imageButton.setOnClickListener(
                v -> toggleCalendar(v)
        );

        imageButton = rootView.findViewById(R.id.btn_toggle_time_picker);
        imageButton.setOnClickListener(
                v -> toggleClock(v)
        );

        Button button = rootView.findViewById(R.id.btn_save_time);
        button.setOnClickListener(
                v -> setMatchTime(v)
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InsertMatchViewModel.class);
        // TODO: Use the ViewModel

        this.setSpinnerAdapters();
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
        isClockVisible = !isClockVisible;
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

    private ArrayList<TableRow> temporaryRows;

    public void onSportChanged()
    {
        clearTemporaryRows();

        Spinner sportSpinner = rootView.findViewById(R.id.sel_match_sport);
        ArrayList<String> selected = (ArrayList<String>) sportSpinner.getSelectedItem();

        Sport sport = mViewModel.getSportById(Integer.parseInt(selected.get(0)));

        if(sport.isTeamSport()) {
            rootView.findViewById(R.id.row_home_team).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.row_home_team_score).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.row_visitors).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.row_visitors_score).setVisibility(View.VISIBLE);
        } else  {
            rootView.findViewById(R.id.row_home_team).setVisibility(View.GONE);
            rootView.findViewById(R.id.row_home_team_score).setVisibility(View.GONE);
            rootView.findViewById(R.id.row_visitors).setVisibility(View.GONE);
            rootView.findViewById(R.id.row_visitors_score).setVisibility(View.GONE);

            TableLayout tableLayout = rootView.findViewById(R.id.table_layout_insert);

            ArrayList<Athlete> athletes = mViewModel.getAthletesBySportAndSex(sport.getSex(),sport.getId());
            ArrayList<ArrayList<String>> data = new ArrayList<>();

            athletes.forEach(
                    athlete -> {
                        String fullName = athlete.getFirstName() + " " + athlete.getLastName();
                        data.add(new ArrayList<String>(Arrays.asList("" + athlete.getId(), fullName)));
                    }
            );

            final int startIndex = 5;

            for (int i = 0; i<sport.getParticipants();i++)
            {
                TableRow row = new TableRow(getContext());
                row.setGravity(Gravity.CENTER_VERTICAL);
                row.setWeightSum(100);
                row.setPadding(0,10,0,0);
                row.setLayoutParams(
                        new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT
                        )
                );

                TextView textView = new TextView(getContext());
                textView.setText("Athlete #" + (i + 1));
                textView.setGravity(Gravity.END);
                textView.setLayoutParams(
                        new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                getResources().getInteger(R.integer.ins_form_row_1)
                        )
                );

                Spinner spinner = new Spinner(getContext());
                spinner.setLayoutParams(
                        new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                getResources().getInteger(R.integer.ins_form_row_2)
                        )
                );

                spinner.setAdapter(new KeyPairSpinnerAdapter(getContext(), data));

                if(data.size()>0)
                {
                    spinner.setSelection(i % data.size());
                }

                row.addView(textView);
                row.addView(spinner);

//                tableLayout.addView(3+i,row);
                tableLayout.addView(row, startIndex + i);
                temporaryRows.add(row);
            }
        }
    }

    private void clearTemporaryRows() {
        if(temporaryRows!=null)
        {
            TableLayout tableLayout = rootView.findViewById(R.id.table_layout_insert);

            temporaryRows.forEach(
                    row -> {
                        tableLayout.removeView(row);
                    }
            );

            temporaryRows = new ArrayList<>();
        }
    }
}