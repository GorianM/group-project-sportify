package edu.dipae.sportify.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import edu.dipae.sportify.R;
import edu.dipae.sportify.adapters.GridListAdapter;
import edu.dipae.sportify.adapters.KeyPairSpinnerAdapter;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.utils.ExceptionHandler;

public class HomeFragment extends Fragment {

    View rootView;
    private HomeViewModel homeViewModel;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // The root view
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_nav_home_to_insertMatchFragment);
            }
        });


        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setSpinnerAdapters();

        Spinner spinner = rootView.findViewById(R.id.sel_sport);
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
    }

    private void setSpinnerAdapters()
    {
        ArrayList<Sport> sports = this.homeViewModel.getAllSports();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        sports.forEach(
                sport -> data.add(new ArrayList<String>(Arrays.asList("" + sport.getId(), sport.getName())))
        );

        Spinner spinner = rootView.findViewById(R.id.sel_sport);
        spinner.setAdapter(new KeyPairSpinnerAdapter(this.getContext(), data));
    }

    public void onSportChanged() {
        new Thread(()->{
            try {
                Sport sport = getSelectedSport();
                ArrayList<ArrayList<String>> data = this.homeViewModel.getMatches(sport.getId());
                ArrayList<Integer> columnWidths = sport.isTeamSport()
                        ? new ArrayList<>(Arrays.asList(28, 7, 7, 28, 30))
                        : new ArrayList<>(Arrays.asList(25, 25, 25, 25));
                GridListAdapter gridListAdapter = new GridListAdapter(this.getContext(), data, columnWidths);

                getActivity().runOnUiThread(() -> {
                    ListView listView = rootView.findViewById(R.id.grd_matches);
                    listView.setAdapter(gridListAdapter);
                });
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> ExceptionHandler.handleException(getContext(), e));
            }
        }).start();
    }

    private Sport getSelectedSport() {
        Spinner sportSpinner = rootView.findViewById(R.id.sel_sport);
        ArrayList<String> selected = (ArrayList<String>) sportSpinner.getSelectedItem();
        return homeViewModel.getSportById(Integer.parseInt(selected.get(0)));
    }
}