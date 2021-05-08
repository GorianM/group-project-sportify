package edu.dipae.sportify.models;

public class Team
{
    private int id;
    private String name;
    private String stadiumName;
    private String city;
    private String country;
    private int sportId;
    private char gender;
    private int yearOfFounding;

    public Team() {
    }

    public Team(int id, String name, String stadiumName, String city, String country, int sportId, char gender, int yearOfFounding) {
        this.id = id;
        this.name = name;
        this.stadiumName = stadiumName;
        this.city = city;
        this.country = country;
        this.sportId = sportId;
        this.gender = gender;
        this.yearOfFounding = yearOfFounding;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getYearOfFounding() {
        return yearOfFounding;
    }

    public void setYearOfFounding(int yearOfFounding) {
        this.yearOfFounding = yearOfFounding;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stadiumName='" + stadiumName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", sportId=" + sportId +
                ", gender=" + gender +
                ", yearOfFounding=" + yearOfFounding +
                '}';
    }
}
