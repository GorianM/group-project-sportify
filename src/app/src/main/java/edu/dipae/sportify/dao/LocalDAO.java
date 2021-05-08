package edu.dipae.sportify.dao;

import java.util.ArrayList;

import edu.dipae.sportify.constants.DummyValues;
import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;

public class LocalDAO {
    public static ArrayList<Sport> getAllSports() {
        // TODO replace with actual query
        return DummyValues.sports;
    }

    public static ArrayList<Team> getAllTeams() {
        // TODO replace with actual query
        return DummyValues.teams;
    }

    public static ArrayList<Athlete> getAllAthletes() {
        // TODO replace with actual query
        return DummyValues.athletes;
    }
}
