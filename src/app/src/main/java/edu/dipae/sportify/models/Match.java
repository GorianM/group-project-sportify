package edu.dipae.sportify.models;
import java.util.ArrayList;
import java.util.Date;

public class Match
{
    private int id;
    private Date date;
    private String city;
    private String country;
    private String sportId;

    private TeamResults teamResults;
    private ArrayList<AthleteResults> athleteScores;
}

