package dataset;

import shows.Movie;
import user.User;

import java.util.ArrayList;
import java.util.List;

public final class Movies {
    private List<Movie> movieList = new ArrayList<>();

    public Movies(List<Movie> movieList) {
        this.movieList = new ArrayList<>(movieList);
    }

    public Movies() {

    }

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

    public String movieListMessage(int N) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(N, movieList.size()); i++) {
            names.add(movieList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    public void updateFavoriteScore(Users users) {
        for (Movie movie : movieList) {
            movie.setFavoriteScore(users);
        }
    }
    public void updateViewsScores(Users users){
        for(Movie movie : movieList){
            movie.setViewsScore(users);
        }
    }
}
