package edu.dipae.sportify.constants;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import edu.dipae.sportify.models.Athlete;
import edu.dipae.sportify.models.Sport;
import edu.dipae.sportify.models.Team;

public class DummyValues
{
    public static ArrayList<Sport> sports = new ArrayList<>(
            Arrays.asList(
                    new Sport(1, "Football", true, 0, 'M'),
                    new Sport(2, "Football", true, 0, 'F'),
                    new Sport(3, "Basketball", true, 0, 'M'),
                    new Sport(4, "Basketball", true, 0, 'F'),
                    new Sport(5, "Sprint", false, 8, 'M'),
                    new Sport(5, "Sprint", false, 8, 'F')
            )
    );

    public static ArrayList<Athlete> athletes = new ArrayList<>(
            Arrays.asList(
                    new Athlete(1,"Bob", "Marley", "Volos", "Greece", 1, 'M', 1990),
                    new Athlete(2,"Yo", "Man", "Karditsa", "Greece", 2, 'M', 1980),
                    new Athlete(3,"Alina", "Jensen", "Lamia", "Greece", 3, 'F', 1995)
            )
    );

    public static ArrayList<Team> teams= new ArrayList<>(
            Arrays.asList(
                    new Team(1, "PAOK", "Toumpa", "Thessaloniki", "Greece", 1, 'M', 1926),
                    new Team(2, "Olympiakos", "Karaiskaki", "Pireaus", "Greece", 1, 'M', 1925)
            )
    );

}
