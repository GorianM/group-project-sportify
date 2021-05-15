package edu.dipae.sportify.ui.matches;

import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import edu.dipae.sportify.dao.LocalDAO;
import edu.dipae.sportify.dao.RemoteDAO;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.AthleteResults;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;
import edu.dipae.sportify.models.TeamResults;
import edu.dipae.sportify.utils.DateUtils;

public class MatchesViewModel extends ViewModel {

    private RemoteDAO remoteDAO;
    private LocalDAO localDAO;

    public MatchesViewModel() {
        remoteDAO = new RemoteDAO();
        localDAO = new LocalDAO();
    }

    public ArrayList<ArrayList<String>> getMatches() throws Exception
    {
        ArrayList<Match> matches = remoteDAO.getAllMatches();
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        data.add(new ArrayList<>(Arrays.asList( "Sport", "Date", "Time", "Country", "City")));

        matches.forEach(
                match -> {
                    Sport sport = localDAO.getSportById(match.getSportId());

                    ArrayList<String> row = new ArrayList<>(
                            Arrays.asList(
                                    sport != null ? sport.getName() : " - ",
                                    DateUtils.dateToString(match.getDate()),
                                    DateUtils.timeToString(match.getDate()),
                                    match.getCountry(),
                                    match.getCity(),
                                    match.getId()
                            )
                    );

                    data.add(row);
                }
        );

        return data;
    }

    public String getMatchData(String matchId) throws ExecutionException, InterruptedException {
        Match match = remoteDAO.getMatchById(matchId);
        Sport sport = localDAO.getSportById(match.getSportId());

        String s = DateUtils.dateTimeToString(match.getDate());
        s += "\n" + match.getCity() + ", " + match.getCountry();

        if (sport.isTeamSport()) {
            TeamResults teamResults = match.getTeamResults();
            Team team1 = localDAO.getTeamById(teamResults.getTeamAId());
            Team team2 = localDAO.getTeamById(teamResults.getTeamBId());
            s += "\n" +team1.getName() + " (" + teamResults.getTeamAScore() + ") - (" + teamResults.getTeamBScore() + ") " + team2.getName();
        } else {
            s += "\n";

            for (int i = 0; i < match.getAthleteScores().size(); i++) {
                AthleteResults athleteResult = match.getAthleteScores().get(i);
                Athlete athlete = localDAO.getAthleteById(athleteResult.getAthleteId());
                s += "\n" + athlete.getFirstName() + " " + athlete.getLastName() + " - " + athleteResult.getAthleteScore();
            }
        }

        return s;
    }

    public void deleteMatch(String matchID) throws ExecutionException, InterruptedException {
        remoteDAO.deleteMatch(matchID);
    }
}
