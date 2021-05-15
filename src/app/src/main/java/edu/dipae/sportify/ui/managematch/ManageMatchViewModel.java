package edu.dipae.sportify.ui.managematch;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import edu.dipae.sportify.dao.LocalDAO;
import edu.dipae.sportify.dao.RemoteDAO;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.AthleteResults;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;
import edu.dipae.sportify.models.TeamResults;

public class ManageMatchViewModel extends ViewModel
{
    private RemoteDAO remoteDAO;
    private LocalDAO localDAO;
    private Match selectedMatch;

    public ManageMatchViewModel()
    {
        this.remoteDAO = new RemoteDAO();
        this.localDAO = new LocalDAO();

        this.selectedMatch = new Match();
        Calendar c = Calendar.getInstance();
        this.selectedMatch.setDate(c.getTime());
    }

    public ArrayList<Sport> getAllSports(){
        return this.localDAO.getAllSports();
    }


    public Sport getSportById(int sportId) {
        ArrayList<Sport> sports = this.localDAO.getAllSports();
        Sport result = sports.stream().filter(
                sport -> sport.getId() == sportId
        ).findFirst(
        ).orElse(null);

        return result;
    }

    public ArrayList<Team> getTeamsBySportAndSex(char sex, int sportId)
    {
        ArrayList<Team> teams = this.localDAO.getAllTeams();
        ArrayList<Team> result = new ArrayList<>(
                teams.stream().filter(
                        team -> {
                            return (sex == 'A' || team.getSex() == sex) && team.getSportId() == sportId;
                        }
                ).collect(Collectors.toList())
        );

        return result;
    }

    public ArrayList<Athlete> getAthletesBySportAndSex(char sex, int sportId)
    {
        ArrayList<Athlete> athletes = this.localDAO.getAllAthletes();
        ArrayList<Athlete> result = new ArrayList<>(
                athletes.stream().filter(
                        athlete -> {
                            return (sex == 'A' || athlete.getSex() == sex) && athlete.getSportId() == sportId;
                        }
                ).collect(Collectors.toList())
        );

        return result;
    }

    public Date getMatchDate()
    {
        return this.selectedMatch.getDate();
    }

    public Match setMatchDate(Date matchDate)
    {
        this.selectedMatch.setDate(matchDate);
        return this.selectedMatch;
    }

    public Match save(String city, String country, int sportId, ArrayList<AthleteResults> athleteScores, TeamResults teamResults) throws Exception
    {
        // The id will be generated automatically
        // The date is already updated
        selectedMatch.setCity(city);
        selectedMatch.setCountry(country);
        selectedMatch.setSportId(sportId);

        selectedMatch.setAthleteScores(athleteScores);
        selectedMatch.setTeamResults(teamResults);

        if(selectedMatch.getId() != null )
        {
            remoteDAO.updateMatch(selectedMatch);
        }
        else
        {
            remoteDAO.insertMatch(selectedMatch);
        }

        return selectedMatch;
    }

    public Match retrieveMatch(String matchId) throws ExecutionException, InterruptedException {
        this.selectedMatch = remoteDAO.getMatchById(matchId);
        return this.selectedMatch;
    }

    public int getSportId() {
        return this.selectedMatch.getSportId();
    }

    public Match getMatch() {
        return this.selectedMatch;
    }
}