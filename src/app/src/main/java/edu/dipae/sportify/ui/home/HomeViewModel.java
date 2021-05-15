package edu.dipae.sportify.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import edu.dipae.sportify.dao.LocalDAO;
import edu.dipae.sportify.dao.RemoteDAO;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;
import edu.dipae.sportify.utils.DateUtils;

public class HomeViewModel extends ViewModel {

    private RemoteDAO remoteDAO;
    private LocalDAO localDAO;

    public HomeViewModel() {
        remoteDAO = new RemoteDAO();
        localDAO = new LocalDAO();
    }

    public ArrayList<ArrayList<String>> getMatches(int sportId) throws ExecutionException, InterruptedException {
        Sport sport = localDAO.getSportById(sportId);
        if(sport.isTeamSport()){
            return getTeamMatchData(sportId);
        } else {
            return getAthleteMatchData(sportId);
        }
    }

    private ArrayList<ArrayList<String>> getTeamMatchData(int sportId) throws ExecutionException, InterruptedException {
        ArrayList<Match> matches = remoteDAO.getMatchBySportId(sportId);
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        data.add(new ArrayList<>(Arrays.asList( "Home", "", "", "Visitors", "Time")));

        matches.forEach(
                match -> {
                    Team home = localDAO.getTeamById(match.getTeamResults().getTeamAId());
                    Team visitors = localDAO.getTeamById(match.getTeamResults().getTeamAId());
                    ArrayList<String> row = new ArrayList<>(
                            Arrays.asList(
                                    home.getName(),
                                    "" + match.getTeamResults().getTeamAScore(),
                                    "" + match.getTeamResults().getTeamAScore(),
                                    visitors.getName(),
                                    DateUtils.shortDateTime(match.getDate())
                            )
                    );

                    data.add(row);
                }
        );

        return data;
    }

    private ArrayList<ArrayList<String>> getAthleteMatchData(int sportId) throws ExecutionException, InterruptedException {
        ArrayList<Match> matches = remoteDAO.getMatchBySportId(sportId);
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        data.add(new ArrayList<>(Arrays.asList( "Country", "City", "Date", "Time")));

        matches.forEach(
                match -> {
                    ArrayList<String> row = new ArrayList<>(
                            Arrays.asList(
                                    match.getCountry(),
                                    match.getCountry(),
                                    DateUtils.shortDateTime(match.getDate()),
                                    DateUtils.shortDateTime(match.getDate())
                            )
                    );

                    data.add(row);
                }
        );

        return data;
    }

    public ArrayList<Sport> getAllSports() {
        return this.localDAO.getAllSports();
    }

    public Sport getSportById(int sportId) {
        return this.localDAO.getSportById(sportId);
    }
}