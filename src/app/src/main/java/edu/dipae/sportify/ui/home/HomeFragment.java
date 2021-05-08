package edu.dipae.sportify.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import edu.dipae.sportify.R;
import edu.dipae.sportify.adapters.GridListAdapter;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // The root view
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ArrayList<Integer> columnWidths = new ArrayList<>(Arrays.asList(40, 40, 20));

        ArrayList<ArrayList<String>> data = this.homeViewModel.getMatches();
        GridListAdapter gridListAdapter = new GridListAdapter(this.getContext(), data, columnWidths);

        ListView listView = root.findViewById(R.id.grd_matches);
        listView.setAdapter(gridListAdapter);

        return root;
    }
}