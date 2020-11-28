package dataset;

import shows.Movie;

import java.util.ArrayList;
import java.util.List;

public final class Movies {
    private List<Movie> movieList = new ArrayList<>();

    public Movies(final List<Movie> movieList) {
        this.movieList = new ArrayList<>(movieList);
    }

    public Movies() {

    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(final List<Movie> movieList) {
        this.movieList = movieList;
    }

    /**
     * verificam daca un film face parte din baza de date
     * @param title titlul filmului pe care il cautam
     * @return filmul daca face parte din lista sau null, in caz contrar
     */
    public Movie findMovie(final String title) {
        for (Movie movie : movieList) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * obtine lista de filme ce vor fi afisate conform criteriilor
     * @param n primele cate filme punem in query
     * @return mesajul de afisat in output
     */
    public String movieListMessage(final int n) {
        List<String> names = new ArrayList<>(); // insereaza numele filmelor
        for (int i = 0; i < Math.min(n, movieList.size()); i++) {
            names.add(movieList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    /**
     * recalculeaza numarul de aparitii in lista de favorite pentru
     * fiecare film
     * @param users baza de date a utilizatorilor
     */
    public void updateFavoriteScore(final Users users) {
        for (Movie movie : movieList) {
            movie.setFavoriteScore(users);
        }
    }

    /**
     * recalculeaza numarul de vizualizari pentru fiecare film
     * @param users baza de date de utilizatori
     */
    public void updateViewsScores(final Users users) {
        for (Movie movie : movieList) {
            movie.setViewsScore(users);
        }
    }
}
