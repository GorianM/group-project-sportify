package edu.dipae.sportify.ui.matches;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import edu.dipae.sportify.R;
import edu.dipae.sportify.adapters.GridListAdapter;
import edu.dipae.sportify.dialogs.DialogFactory;
import edu.dipae.sportify.utils.ExceptionHandler;

public class MatchesFragment extends Fragment {

    private View rootView;
    private MatchesViewModel matchesViewModel;
    AlertDialog gridDialog;

    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        matchesViewModel = new ViewModelProvider(this).get(MatchesViewModel.class);

        // The root view
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(
                view-> {
                    Navigation
                            .findNavController(view)
                            .navigate(R.id.action_matchesFragment_to_insertMatchFragment);
                }
        );

        initList();

        return rootView;
    }

    private void initList() {
        new Thread( () -> {
            try {
                ArrayList<ArrayList<String>> data = this.matchesViewModel.getMatches();

                getActivity().runOnUiThread(
                        () -> {
                            ArrayList<Integer> columnWidths = new ArrayList<>(Arrays.asList(20, 22, 13, 20, 25));

                            GridListAdapter gridListAdapter = new GridListAdapter(this.getContext(), data, columnWidths);

                            ListView listView = rootView.findViewById(R.id.grd_matches);
                            listView.setAdapter(gridListAdapter);
                            listView.setOnItemClickListener(
                                    (parent, view, position, id) -> onListItemClicked(parent, view, position, id)
                            );
                        }
                );
            } catch (Exception e) {
                getActivity().runOnUiThread(
                        () -> ExceptionHandler.handleException(getContext(), e)
                );
            }
        }).start();
    }

    public void onListItemClicked(View parent, View view, int position, long id) {
        if(position == 0){
            return;
        }

        ListView listView = rootView.findViewById(R.id.grd_matches);
        ArrayList<String> row = (ArrayList<String>) listView.getItemAtPosition(position);
        String matchId = row.get(5);
        gridDialog = DialogFactory.showGridDialog(
                getContext(),
                viewView -> onDialogViewClicked(viewView, matchId),
                editView -> onDialogEditClick(editView, matchId),
                deleteView -> onDialogDeleteClick(deleteView, matchId)
        );
    }

    public void onDialogViewClicked(View view, String matchID) {
        new Thread( () -> {
            try {
                String text = matchesViewModel.getMatchData(matchID);
                getActivity().runOnUiThread(() -> {
                    DialogFactory.showInfoDialog(getContext(), "Info", text);
                });
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> ExceptionHandler.handleException(getContext(), e));
            }
        }).start();
    }

    public void onDialogDeleteClick(View view, String matchId) {

        new Thread( () -> {
            try {
                String text = matchesViewModel.getMatchData(matchId);
                getActivity().runOnUiThread(() -> {
                    DialogFactory.showConfirmationDialog(
                            getContext(),
                            "Delete Match",
                            "Delete Match with data:\n" + text,
                            (dialog, which) -> doTheDeleting(matchId)
                    );
                });
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> ExceptionHandler.handleException(getContext(), e));
            }
        }).start();
    }

    public void doTheDeleting(String matchID) {

        new Thread( () -> {
            try {
                matchesViewModel.deleteMatch(matchID);
                String test = "";
                getActivity().runOnUiThread(() -> {
                    DialogFactory.showInfoDialog(getContext(), "Info", "The row was deleted");
                    initList();
                    gridDialog.dismiss();
                });

            } catch (Exception e) {
                getActivity().runOnUiThread(() -> ExceptionHandler.handleException(getContext(), e));
            }
        }).start();
    }

    public void onDialogEditClick(View view, String matchId) {
        Bundle bundle = new Bundle();
        bundle.putString("matchId", matchId);

        gridDialog.dismiss();

        Navigation.findNavController(rootView)
                .navigate(R.id.action_matchesFragment_to_insertMatchFragment, bundle);
    }
}