package org.example;

public class Main {

    public static void initData(DBOperations dbOperations) {
        dbOperations.insertDirector("Christopher Nolan", "USA");
        dbOperations.insertDirector("Matt Reeves", "USA");
        dbOperations.insertDirector("David Cronenberg", "Canada");
        dbOperations.insertDirector("Danny Boyle", "England");

        dbOperations.insertMovie("Interstellar", "2014", "2h 49m", (float) 8.7);
        dbOperations.insertMovie("The Batman 2022", "2022", "2h 56m", (float) 7.8);
        dbOperations.insertMovie("Inception", "2010", "2h 28m", (float) 8.8);
        dbOperations.insertMovie("The Dead Zone", "1983", "1h 43m", (float) 7.2);
        dbOperations.insertMovie("28 Days Later", "2002", "1h 53m", (float) 7.5);

        dbOperations.insertFilmWithDirector(1, 1);
        dbOperations.insertFilmWithDirector(2, 2);
        dbOperations.insertFilmWithDirector(3, 1);
        dbOperations.insertFilmWithDirector(4, 3);
        dbOperations.insertFilmWithDirector(5, 4);
    }

    public static void main(String[] args) {
        DBOperations dbOperations = new DBOperations();
        dbOperations.dbConnection();
        initData(dbOperations);
        dbOperations.fetchMovieData();
        dbOperations.fetchDirectorByCountry("USA");
        dbOperations.getNumberOfDirectorFilms();
    }
}
