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
     *
     * @param title
     * @return
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
     *
     * @param n
     * @return
     */
    public String movieListMessage(final int n) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(n, movieList.size()); i++) {
            names.add(movieList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    /**
     *
     * @param users
     */
    public void updateFavoriteScore(final Users users) {
        for (Movie movie : movieList) {
            movie.setFavoriteScore(users);
        }
    }

    /**
     *
     * @param users
     */
    public void updateViewsScores(final Users users) {
        for (Movie movie : movieList) {
            movie.setViewsScore(users);
        }
    }
}
