package edu.dipae.sportify.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<ArrayList<String>> getMatches(){
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        data.add(new ArrayList<>( Arrays.asList("Team 1",           "Team 2",      "Time")));

        data.add(new ArrayList<>( Arrays.asList("M. Utd",           "Arsenal",     "9:30")));
        data.add(new ArrayList<>( Arrays.asList("PAOK",             "Olympiakos",  "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Panionios",        "Levadiakos",  "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Barcelona",        "Chelsea",     "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Liverpool",        "Totenham",    "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Milan",            "Uventus",     "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Valencia",         "Real Madrid", "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Asteras Tripolis", "Panaxaikh",   "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Atletico Madrid",  "Juventus",    "9:30")));
        data.add(new ArrayList<>( Arrays.asList("Ajax",             "Lyon",        "9:30")));

        return data;


    }
}