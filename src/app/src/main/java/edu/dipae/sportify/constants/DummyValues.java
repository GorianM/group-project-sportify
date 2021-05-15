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
                    new Sport(2, "Football (W)", true, 0, 'F'),
                    new Sport(3, "Basketball (M)", true, 0, 'M'),
                    new Sport(4, "Basketball (W)", true, 0, 'F'),
                    new Sport(5, "Sprint (M)", false, 8, 'M'),
                    new Sport(6, "Sprint (W)", false, 8, 'F'),
                    new Sport(7, "Chess", false, 2, 'A')
            )
    );

    public static ArrayList<Athlete> athletes = new ArrayList<>(
            Arrays.asList(
                    new Athlete(1,"Peter", "Marley", "Volos", "Greece", 1, 'M', 1990),
                    new Athlete(2,"Hans", "Mann", "Karditsa", "Greece", 2, 'M', 1980),
                    new Athlete(3,"Helena", "Jensen", "Lamia", "Greece", 3, 'F', 1995),

                    new Athlete(4, "Fabiano", "Caruana", "St. Louis", "U.S.A", 7, 'M', 1992),
                    new Athlete(5, "Hikaru", "Nakamura", "St. Louis", "U.S.A", 7, 'M', 1987),
                    new Athlete(6, "Alexandra", "Botez", "Vancouver", "Canada", 7, 'F', 1995),
                    new Athlete(7, "Hou", "Yifan", "Xinghua", "China", 7, 'F', 1994),
                    new Athlete(8, "Aleksandra", "Aleksandra", "Orsk", "Russia", 7, 'F', 1998),

                    new Athlete(9, "Emilio", "Addonizio", "Milano", "Italy", 5, 'M', 1999),
                    new Athlete(10, "Gianni", "Bresciani", "Rome", "Italy", 5, 'M', 2000),
                    new Athlete(11, "Leonardo", "Cagnina", "Malta", "Malta", 5, 'M', 1998),
                    new Athlete(12, "Mateo", "Buratti", "Malta", "Malta", 5, 'M', 2001),
                    new Athlete(13, "Achille", "Calistro", "Madrite", "Spain", 5, 'M', 1995),
                    new Athlete(14, "Emmanuel", "Blanco", "Leticia", "Colombia", 5, 'M', 1998),
                    new Athlete(15, "Sebastian", "Cano", "Bogota", "Colombia", 5, 'M', 2001),
                    new Athlete(16, "Martin", "Camacho", "Leticia", "Colombia", 5, 'M', 1997),
                    new Athlete(17, "Samuel", "Cortés", "Medellin", "Colombia", 5, 'M', 1992),
                    new Athlete(18, "Santiago", "Esteban", "Medellin", "Colombia", 5, 'M', 1994),

                    new Athlete(19, "Nala", "Acheampong", "Kinshasa", "Republic of Congo", 6, 'F', 1994),
                    new Athlete(20, "Anika", "Kenyatta", "Abuja", "Nigeria", 6, 'F', 2000),
                    new Athlete(21, "Zendaya", "Nnamani", "Djibouti", "Djibouti", 6, 'F', 1996),
                    new Athlete(22, "Imara", "Nwachukwu", "Djibouti", "Djibouti", 6, 'F', 1998),
                    new Athlete(23, "Makena", "Abimbola", "Cape Town", "South Africa", 6, 'F', 2001),
                    new Athlete(24, "Elena", "Teresa", "Maroa", "Venezuela", 6, 'F', 1990),
                    new Athlete(25, "Ana", "Teresa", "La Cruz", "Venezuela", 6, 'F', 2000),
                    new Athlete(26, "Elizabeth", "Fernanda", "La Cruz", "Venezuela", 6, 'F', 1991),
                    new Athlete(27, "Laura", "Velez", "Colonia Tovar", "Venezuela", 6, 'F', 1992),
                    new Athlete(28, "Laura", "Jose", "Colonia Tovar", "Venezuela", 6, 'F', 2001)

            )
    );

    public static ArrayList<Team> teams= new ArrayList<>(
            Arrays.asList(
                    new Team(1, "PAOK", "Toumpa", "Thessaloniki", "Greece", 1, 'M', 1926),
                    new Team(2, "Olympiakos", "Karaiskaki", "Pireaus", "Greece", 1, 'M', 1925),
                    new Team(3, "Chelsea", "Stamford Bridge", "London", "UK", 1, 'M', 1905),
                    new Team(4, "Manchester United", "Old Trafford", "Manchester", "UK", 1, 'M', 1878),
                    new Team(5, "Liverpool", "Goodison Park", "Liverpool", "UK", 1, 'M', 1892),

                    new Team(6, "PAOK", "Toumpa", "Thessaloniki", "Greece", 2, 'F', 1946),
                    new Team(7, "Olympiakos", "Karaiskaki", "Pireaus", "Greece", 2, 'F', 1937),
                    new Team(8, "Chelsea", "Stamford Bridge", "London", "UK", 2, 'F', 1976),
                    new Team(9, "Manchester United", "Old Trafford", "Manchester", "UK", 2, 'F', 1944),
                    new Team(10, "Liverpool", "Goodison Park", "Liverpool", "UK", 2, 'F', 1912),


                    new Team(11, "Miami Heat", "AmericanAirlines Arena", "Miami", "USA", 3, 'M', 1988),
                    new Team(12, "Toronto Raptors", "ScotiaBank Arena", "Toronto", "USA", 3, 'M', 1995),
                    new Team(13, "Golden State Warriors", "Chase Center", "San Francisco", "USA", 3, 'M', 1971),
                    new Team(14, "Los Angeles Lakers", "Staples Center", "Los Angeles", "USA", 3, 'M', 1947),
                    new Team(15, "New York Knicks", "Madison Square Garden", "New York", "USA", 3, 'M', 1946),

                    new Team(16, "Miami Heat", "AmericanAirlines Arena", "Miami", "USA", 4, 'F', 1993),
                    new Team(17, "Toronto Raptors", "ScotiaBank Arena", "Toronto", "USA", 4, 'F', 1996),
                    new Team(18, "Golden State Warriors", "Chase Center", "San Francisco", "USA", 4, 'F', 1978),
                    new Team(19, "Los Angeles Lakers", "Staples Center", "Los Angeles", "USA", 4, 'F', 1955),
                    new Team(20, "New York Knicks", "Madison Square Garden", "New York", "USA", 4, 'F', 1966)

            )
    );

}
