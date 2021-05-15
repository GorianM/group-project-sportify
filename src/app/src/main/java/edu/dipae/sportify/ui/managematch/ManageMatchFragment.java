package edu.dipae.sportify.ui.managematch;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import edu.dipae.sportify.R;
import edu.dipae.sportify.adapters.KeyPairSpinnerAdapter;
import edu.dipae.sportify.dialogs.DialogFactory;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.AthleteResults;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;
import edu.dipae.sportify.models.TeamResults;
import edu.dipae.sportify.utils.DateUtils;
import edu.dipae.sportify.utils.ExceptionHandler;

public class ManageMatchFragment extends Fragment
{

    private ArrayList<TableRow> temporaryRows;
    private boolean isCalendarVisible;
    private boolean isClockVisible;
    private ManageMatchViewModel mViewModel;
    private View rootView;

    public static ManageMatchFragment newInstance() {
        return new ManageMatchFragment();
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
        this.rootView =  inflater.inflate(R.layout.fragment_manage_match, container, false);

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

        button = rootView.findViewById(R.id.btn_save);
        button.setOnClickListener(
                v -> saveMatch(v)
        );

        button = rootView.findViewById(R.id.btn_cancel);
        button.setOnClickListener(
                v -> {
                    navigateToMatches(v);
                }
        );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ManageMatchViewModel.class);
        // TODO: Use the ViewModel

        this.setSpinnerAdapters();

        Bundle args = getArguments();
        if( args != null ) {
            String matchId = args.getString("matchId");
            if (matchId != null) {
                new Thread(() -> {
                    try {
                        Match match = this.mViewModel.retrieveMatch(matchId);

                        getActivity().runOnUiThread(() -> {
                            initFields(match);
                        });
                    } catch (Exception e) {
                        getActivity().runOnUiThread(() -> ExceptionHandler.handleException(getContext(), e));
                    }
                }).start();
            }
        }
    }

    private void initFields(Match match) {
        EditText editText = this.rootView.findViewById(R.id.edt_match_country);
        editText.setText(match.getCountry());
        editText = this.rootView.findViewById(R.id.edt_match_city);
        editText.setText(match.getCity());
        editText = this.rootView.findViewById(R.id.edt_match_date);
        editText.setText(DateUtils.dateToString(match.getDate()));
        editText = this.rootView.findViewById(R.id.edt_match_time);
        editText.setText(DateUtils.timeToString(match.getDate()));

        Spinner sportSpinner = rootView.findViewById(R.id.sel_match_sport);

        int count = sportSpinner.getAdapter().getCount();
        for (int i = 0; i < count; i++){
            ArrayList<String> row = (ArrayList<String>) sportSpinner.getAdapter().getItem(i);
            String temp = row.get(0);
            if(Integer.parseInt(temp) == match.getSportId()){
                sportSpinner.setSelection(i);
                break;
            }
        }

        Sport sport = mViewModel.getSportById(match.getSportId());
        if(sport.isTeamSport()) {
            editText = this.rootView.findViewById(R.id.edt_home_team_score);
            editText.setText("" + match.getTeamResults().getTeamAScore());
            editText = this.rootView.findViewById(R.id.edt_visitors_score);
            editText.setText("" + match.getTeamResults().getTeamBScore());

            sportSpinner = rootView.findViewById(R.id.sel_match_home_team);

            count = sportSpinner.getAdapter().getCount();
            for (int i = 0; i < count; i++) {
                ArrayList<String> row = (ArrayList<String>) sportSpinner.getAdapter().getItem(i);
                String temp = row.get(0);
                if (Integer.parseInt(temp) == match.getTeamResults().getTeamAId()) {
                    sportSpinner.setSelection(i);
                    break;
                }
            }

            sportSpinner = rootView.findViewById(R.id.sel_match_visitors);

            count = sportSpinner.getAdapter().getCount();
            for (int i = 0; i < count; i++) {
                ArrayList<String> row = (ArrayList<String>) sportSpinner.getAdapter().getItem(i);
                String temp = row.get(0);
                if (Integer.parseInt(temp) == match.getTeamResults().getTeamBId()) {
                    sportSpinner.setSelection(i);
                    break;
                }
            }
        }
//        } else {
//            ArrayList<AthleteResults> athleteScores = match.getAthleteScores();
//            AthleteResults scoreRow;
//            TableRow row;
//            for (int i = 0; i < temporaryRows.size(); i += 2) {
//                scoreRow = athleteScores.get(i / 2);
//                row = temporaryRows.get(i);
//                sportSpinner = (Spinner) row.getChildAt(1);
//
//                count = sportSpinner.getAdapter().getCount();
//                for (int j = 0; j < count; j++) {
//                    ArrayList<String> spinnerRow = (ArrayList<String>) sportSpinner.getAdapter().getItem(j);
//                    String temp = spinnerRow.get(0);
//                    DialogFactory.showInfoDialog(getContext(), "Info", "temp: "+temp+"\n athlete id: "+scoreRow.getAthleteId());
//                    if(Integer.parseInt(temp) == scoreRow.getAthleteId()){
//                        sportSpinner.setSelection(j);
//                        break;
//                    }
//                }
//
//                row = temporaryRows.get(i + 1);
//                editText = (EditText) row.getChildAt(1);
//                editText.setText("" + scoreRow.getAthleteScore());
//            }
//        }
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

    public void saveMatch(View view)
    {
        AlertDialog dialog = DialogFactory.showProgressDialog(getContext());
        new Thread(
                () ->
                {
                    doTheSaving();

                    dialog.dismiss();

                    getActivity().runOnUiThread(
                            () -> navigateToMatches(view)
                    );
                }
        ).start();
    }

    private void navigateToMatches(View view){
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_insertMatchFragment_to_matchesFragment);
    }

    private void doTheSaving()
    {
        EditText editText = this.rootView.findViewById(R.id.edt_match_country);
        String country = editText.getText().toString();
        editText = this.rootView.findViewById(R.id.edt_match_city);
        String city = editText.getText().toString();

        Spinner sportSpinner = rootView.findViewById(R.id.sel_match_sport);
        ArrayList<String> selected = (ArrayList<String>) sportSpinner.getSelectedItem();

        Sport selectedSport = mViewModel.getSportById(Integer.parseInt(selected.get(0)));

        try
        {
            this.mViewModel.save(
                    city,
                    country,
                    selectedSport.getId(),
                    getAthleteResults(selectedSport.isTeamSport()),
                    getTeamResults(selectedSport.isTeamSport())
            );
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private TeamResults getTeamResults(boolean isTeamSport)
    {
        TeamResults teamResults = null;
        if(isTeamSport)
        {
            Spinner homeSpinner = rootView.findViewById(R.id.sel_match_home_team);
            ArrayList<String> selectedRow = (ArrayList<String>) homeSpinner.getSelectedItem();
            String homeTeamId = selectedRow.get(0);

            EditText homeScoreField = rootView.findViewById(R.id.edt_home_team_score);
            String homeScore = homeScoreField.getText().toString();
            homeScore = homeScore.isEmpty() ? "0" : homeScore;

            Spinner visitorsSpinner = rootView.findViewById(R.id.sel_match_visitors);
            selectedRow = (ArrayList<String>) visitorsSpinner.getSelectedItem();
            String visitorsTeamId = selectedRow.get(0);

            EditText visitorsScoreField = rootView.findViewById(R.id.edt_visitors_score);
            String visitorsScore = visitorsScoreField.getText().toString();
            visitorsScore = visitorsScore.isEmpty() ? "0" : visitorsScore;

            teamResults = new TeamResults(
                    Integer.parseInt(homeTeamId),
                    Integer.parseInt(homeScore),
                    Integer.parseInt(visitorsTeamId),
                    Integer.parseInt(visitorsScore)
            );
        }

        return teamResults;
    }

    private ArrayList<AthleteResults> getAthleteResults(boolean isTeamSport)
    {
        ArrayList<AthleteResults> athleteResults = null;

        if(!isTeamSport)
        {
            athleteResults = new ArrayList<>();

            for(int i = 0; i < temporaryRows.size(); i += 2)
            {
                TableRow athleteRow =temporaryRows.get(i);
                Spinner spinner = (Spinner) athleteRow.getChildAt(1);
                ArrayList<String> selectedRow = (ArrayList<String>) spinner.getSelectedItem();
                String selectedAthleteId = selectedRow.get(0);

                TableRow athleteScoreRow = temporaryRows.get(i + 1);
                EditText editText = (EditText) athleteScoreRow.getChildAt(1);
                String athleteScore = editText.getText().toString();
                athleteScore = athleteScore.isEmpty() ? "0" : athleteScore;

                athleteResults.add(
                        new AthleteResults(
                                Integer.parseInt(selectedAthleteId),
                                Integer.parseInt(athleteScore)
                        )
                );
            }
        }

        return athleteResults;
    }

    public void onSportChanged()
    {
        clearTemporaryRows();

        Spinner sportSpinner = rootView.findViewById(R.id.sel_match_sport);
        ArrayList<String> selected = (ArrayList<String>) sportSpinner.getSelectedItem();

        Sport selectedSport = mViewModel.getSportById(Integer.parseInt(selected.get(0)));

        if(selectedSport.isTeamSport())
        {
            this.prepareTeamSportFields(selectedSport);
        }
        else
        {
            this.prepareAthleteFields(selectedSport);
        }
    }

    private void prepareTeamSportFields(Sport selectedSport)
    {
        rootView.findViewById(R.id.row_home_team).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.row_home_team_score).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.row_visitors).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.row_visitors_score).setVisibility(View.VISIBLE);

        ArrayList<Team> teams = mViewModel.getTeamsBySportAndSex(selectedSport.getSex(), selectedSport.getId());
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        teams.forEach(
                team -> {
                    data.add(new ArrayList<String>(Arrays.asList("" + team.getId(), team.getName())));
                }
        );

        Spinner spinner = this.rootView.findViewById(R.id.sel_match_home_team);
        spinner.setAdapter(new KeyPairSpinnerAdapter(getContext(), data));
        spinner.setSelection(0);

        spinner = this.rootView.findViewById(R.id.sel_match_visitors);
        spinner.setAdapter(new KeyPairSpinnerAdapter(getContext(), data));
        spinner.setSelection(1);
    }

    private void prepareAthleteFields(Sport selectedSport)
    {
        rootView.findViewById(R.id.row_home_team).setVisibility(View.GONE);
        rootView.findViewById(R.id.row_home_team_score).setVisibility(View.GONE);
        rootView.findViewById(R.id.row_visitors).setVisibility(View.GONE);
        rootView.findViewById(R.id.row_visitors_score).setVisibility(View.GONE);

        TableLayout tableLayout = rootView.findViewById(R.id.table_layout_insert);

        ArrayList<Athlete> athletes = mViewModel.getAthletesBySportAndSex(selectedSport.getSex(), selectedSport.getId());
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        athletes.forEach(
                athlete -> {
                    String fullName = athlete.getFirstName() + " " + athlete.getLastName();
                    data.add(new ArrayList<String>(Arrays.asList("" + athlete.getId(), fullName)));
                }
        );

        final int startIndex = 7;

        for (int i = 0; i < selectedSport.getParticipants(); i++)
        {
            int index = startIndex + (i * 2);
            TableRow row = getAthleteSelectionRow(data, i);
            tableLayout.addView(row, index);
            temporaryRows.add(row);

            row = getAthleteScoreRow(i);
            tableLayout.addView(row, index + 1);
            temporaryRows.add(row);
        }
    }

    private TableRow getAthleteSelectionRow(ArrayList<ArrayList<String>> data, int i)
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

        if(data.size()>0) {
            if(mViewModel.getSportId() == getSelectedSport().getId()) {
                Match match = mViewModel.getMatch();
                AthleteResults scoreRow = match.getAthleteScores().get(i);
                int count = spinner.getAdapter().getCount();
                for (int j = 0; j < count; j++) {
                    ArrayList<String> spinnerRow = (ArrayList<String>) spinner.getAdapter().getItem(j);
                    String temp = spinnerRow.get(0);
                    DialogFactory.showInfoDialog(getContext(), "Info", "temp: "+temp+"\n athlete id: "+scoreRow.getAthleteId());
                    if(Integer.parseInt(temp) == scoreRow.getAthleteId()){
                        spinner.setSelection(j);
                        break;
                    }
                }
            }else {
                spinner.setSelection(i % data.size());
            }
        }

        row.addView(textView);
        row.addView(spinner);

        return row;
    }

    private Sport getSelectedSport() {
        Spinner sportSpinner = rootView.findViewById(R.id.sel_match_sport);
        ArrayList<String> selected = (ArrayList<String>) sportSpinner.getSelectedItem();
        return mViewModel.getSportById(Integer.parseInt(selected.get(0)));
    }

    private TableRow getAthleteScoreRow(int i)
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
        textView.setText("Ath. #" + (i + 1)+ " Score");
        textView.setGravity(Gravity.END);
        textView.setLayoutParams(
                new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        getResources().getInteger(R.integer.ins_form_row_1)
                )
        );

        EditText editText = new EditText(getContext());
        editText.setLayoutParams(
                new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        getResources().getInteger(R.integer.ins_form_row_2)
                )
        );

        row.addView(textView);
        row.addView(editText);

        return row;
    }

    private void clearTemporaryRows()
    {
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