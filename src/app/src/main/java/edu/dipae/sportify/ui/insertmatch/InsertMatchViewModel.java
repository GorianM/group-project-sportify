package edu.dipae.sportify.ui.insertmatch;

import android.view.View;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import edu.dipae.sportify.dao.LocalDAO;
import edu.dipae.sportify.dao.RemoteDAO;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.AthleteResults;
import edu.dipae.sportify.models.Match;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;
import edu.dipae.sportify.models.TeamResults;

public class InsertMatchViewModel extends ViewModel
{
    private RemoteDAO remoteDAO;
    private LocalDAO localDAO;
    private Match newMatch;

    public InsertMatchViewModel()
    {
        this.remoteDAO = new RemoteDAO();
        this.localDAO = new LocalDAO();

        this.newMatch = new Match();
        Calendar c = Calendar.getInstance();
        this.newMatch.setDate(c.getTime());
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
        return this.newMatch.getDate();
    }

    public Match setMatchDate(Date matchDate)
    {
        this.newMatch.setDate(matchDate);
        return this.newMatch;
    }

    public Match set(String city, String country, String sportId, ArrayList<AthleteResults> athleteScores, TeamResults teamResults)
    {
        // The id will be generated automatically
        // The date is already updated
        newMatch.setCity(city);
        newMatch.setCountry(country);
        newMatch.setSportId(sportId);

        newMatch.setAthleteScores(athleteScores);
        newMatch.setTeamResults(teamResults);

        remoteDAO.insertMatch(newMatch);

        return newMatch;
    }
}