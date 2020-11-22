package dataset;

import shows.Movie;

import java.util.ArrayList;
import java.util.List;

public final class Movies {
    private List<Movie> movieList = new ArrayList<>();

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Movie findMovie(String title) {
        for (Movie movie : movieList) {
            if (movie.getTitle().equals(title)) return movie;
        }
        return null;
    }
}
