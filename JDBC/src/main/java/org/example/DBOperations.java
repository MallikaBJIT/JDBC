package org.example;

import org.example.utils.DirectorUtil;
import org.example.utils.FilmDirectorUtil;
import org.example.utils.MovieUtil;
import org.example.utils.Util;

import java.sql.*;

public class DBOperations {
    Connection connection;

    public void dbConnection() {
        String url = "jdbc:mysql://localhost:3306/" + Util.DATABASE_NAME;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, Util.USER_NAME, Util.PASSWORD);
            System.out.println("connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertMovie(String title, String date, String duration, float rating) {
        String query = "INSERT INTO " + MovieUtil.TABLE_NAME + " (" + MovieUtil.COLUMN_TITLE + ", " + MovieUtil.COLUMN_RELEASE_YEAR +
                ", " + MovieUtil.COLUMN_DURATION + ", " + MovieUtil.COLUMN_RATING + ") VALUES (\"" + title.toUpperCase() + "\",\"" +
                date.toString() + "\",\"" + duration.toUpperCase() + "\"," + rating + ");";
        try {
            Statement statement = connection.createStatement();
            if (statement.executeUpdate(query) == 1) {
                System.out.println("insert data into movie table is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertDirector(String name, String country) {
        String query = "INSERT INTO " + DirectorUtil.TABLE_NAME + " (" + DirectorUtil.COLUMN_NAME + ", " + DirectorUtil.COLUMN_COUNTRY +
                ") VALUES (\"" + name.toUpperCase() + "\",\"" + country.toUpperCase() + "\");";
        try {
            Statement statement = connection.createStatement();
            if (statement.executeUpdate(query) == 1) {
                System.out.println("insert data into director table is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertFilmWithDirector(int filmId, int directorId) {
        String query = "INSERT INTO " + FilmDirectorUtil.TABLE_NAME + " (" + FilmDirectorUtil.COLUMN_FILM_ID + "," +
                FilmDirectorUtil.COLUMN_DIRECTOR_ID + ") VALUES (" + filmId + "," + directorId + ");";
        try {
            Statement statement = connection.createStatement();
            if (statement.executeUpdate(query) == 1) {
                System.out.println("insert data into directed_film table is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getNumberOfDirectorFilms() {
        //inner join directed_film and director table
        //group by on director_id of director table
        String query = "SELECT d." + DirectorUtil.COLUMN_NAME + ", d." + DirectorUtil.COLUMN_COUNTRY +
                ", COUNT(fd." + FilmDirectorUtil.COLUMN_FILM_ID + ") AS film_count " +
                "FROM " + DirectorUtil.TABLE_NAME + " d INNER JOIN " + FilmDirectorUtil.TABLE_NAME +
                " fd ON d." + DirectorUtil.COLUMN_ID + " = fd." + FilmDirectorUtil.COLUMN_DIRECTOR_ID +
                " GROUP BY  d." + DirectorUtil.COLUMN_ID + ";";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("\nNumber of directed films by each directors -->");
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String country = resultSet.getString(2);
                int count = resultSet.getInt(3);
                System.out.println(name + ": no of movies " + count + ", country " + country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchMovieData() {
        String query = "SELECT * FROM " + MovieUtil.TABLE_NAME + ";";
        System.out.println("\nMovie details -->");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String title = resultSet.getString(MovieUtil.COLUMN_TITLE);
                String releaseYear = resultSet.getString(MovieUtil.COLUMN_RELEASE_YEAR);
                String duration = resultSet.getString(MovieUtil.COLUMN_DURATION);
                float rating = resultSet.getFloat(MovieUtil.COLUMN_RATING);
                System.out.println("Movie " + title + " released in " + releaseYear + "" +
                        ". Movie Duration: " + duration + " and Rating: " + rating);
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchDirectorByCountry(String countryName) {
        String query = "SELECT " + DirectorUtil.COLUMN_NAME + " FROM " + DirectorUtil.TABLE_NAME + " WHERE "
                + DirectorUtil.COLUMN_COUNTRY + " = \"" + countryName.toUpperCase() + "\";";
        System.out.println("\nDirectors from " + countryName + " --->");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString(DirectorUtil.COLUMN_NAME);
                System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
