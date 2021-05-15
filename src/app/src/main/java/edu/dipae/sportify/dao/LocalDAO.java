package edu.dipae.sportify.dao;

import java.util.ArrayList;

import edu.dipae.sportify.constants.DummyValues;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;

public class LocalDAO {

    public Sport getSportById(int sportId) {
        // TODO replace with actual query
        return DummyValues.sports.stream().filter(sport -> sport.getId() == sportId).findAny().orElse(null);
    }

    public ArrayList<Sport> getAllSports() {
        // TODO replace with actual query
        return DummyValues.sports;
    }

    public Team getTeamById(int teamId) {
        // TODO replace with actual query
        return DummyValues.teams.stream().filter(team -> team.getId() == teamId).findAny().orElse(null);
    }

    public ArrayList<Team> getAllTeams() {
        // TODO replace with actual query
        return DummyValues.teams;
    }

    public Athlete getAthleteById(int athleteId) {
        // TODO replace with actual query
        return DummyValues.athletes.stream().filter(athlete -> athlete.getId() == athleteId).findAny().orElse(null);
    }

    public ArrayList<Athlete> getAllAthletes() {
        // TODO replace with actual query
        return DummyValues.athletes;
    }
}
