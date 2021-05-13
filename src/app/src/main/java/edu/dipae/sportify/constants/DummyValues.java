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
                    new Sport(1, "Football (M)", true, 0, 'M'),
                    new Sport(2, "Football (F)", true, 0, 'F'),
                    new Sport(3, "Basketball (M)", true, 0, 'M'),
                    new Sport(4, "Basketball (F)", true, 0, 'F'),
                    new Sport(5, "Sprint (M)", false, 8, 'M'),
                    new Sport(6, "Sprint (F)", false, 8, 'F'),
                    new Sport(7, "Chess", false, 2, 'A')
            )
    );

    public static ArrayList<Athlete> athletes = new ArrayList<>(
            Arrays.asList(
                    new Athlete(1,"Bob", "Marley", "Volos", "Greece", 1, 'M', 1990),
                    new Athlete(2,"Yo", "Man", "Karditsa", "Greece", 2, 'M', 1980),
                    new Athlete(3,"Alina", "Jensen", "Lamia", "Greece", 3, 'F', 1995),

                    new Athlete(101, "Fabiano", "Caruana", "St. Louis", "U.S.A", 7, 'M', 1992),
                    new Athlete(102, "Hikaru", "Nakamura", "St. Louis", "U.S.A", 7, 'M', 1987),
                    new Athlete(103, "Alexandra", "Botez", "Vancouver", "Canada", 7, 'F', 1995),
                    new Athlete(104, "Hou", "Yifan", "Xinghua", "China", 7, 'F', 1994),
                    new Athlete(105, "Aleksandra", "Aleksandra", "Orsk", "Russia", 7, 'F', 1998)

            )
    );

    public static ArrayList<Team> teams= new ArrayList<>(
            Arrays.asList(
                    new Team(1, "PAOK", "Toumpa", "Thessaloniki", "Greece", 1, 'M', 1926),
                    new Team(2, "Olympiakos", "Karaiskaki", "Pireaus", "Greece", 1, 'M', 1925)
            )
    );

}
