package dataset;

import actor.Actor;
import fileio.ActionInputData;

import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import shows.Movie;
import shows.Serial;
import user.User;

import java.util.List;

public final class DataCenter {
    private final Actors actors = new Actors();
    private final Movies movies = new Movies();
    private final Serials serials = new Serials();
    private final Users users = new Users();
    private final Videos videos = new Videos();
    private List<ActionInputData> actions;

    public Actors getActors() {
        return actors;
    }

    public Movies getMovies() {
        return movies;
    }

    public Serials getSerials() {
        return serials;
    }

    public Users getUsers() {
        return users;
    }

    public Videos getVideos() {
        return videos;
    }

    public List<ActionInputData> getActions() {
        return actions;
    }

    /**
     * mutam baza de date undeva unde o putem modifica
     * @param input baza de date initiala
     */
    public void initializeData(final Input input) {
        for (ActorInputData actorInput : input.getActors()) {
            Actor actor = new Actor(actorInput.getName(),
                                    actorInput.getCareerDescription(),
                                    actorInput.getFilmography(),
                                    actorInput.getAwards());
            actors.getActorsList().add(actor);
        }
        for (MovieInputData movieInput : input.getMovies()) {
            Movie movie = new Movie(movieInput.getTitle(),
                                    movieInput.getCast(),
                                    movieInput.getGenres(),
                                    movieInput.getYear(),
                                    movieInput.getDuration());
            movies.getMovieList().add(movie);
        }
        for (SerialInputData serialInputData : input.getSerials()) {
            Serial serial = new Serial(serialInputData.getTitle(),
                                       serialInputData.getCast(),
                                       serialInputData.getGenres(),
                                       serialInputData.getNumberSeason(),
                                       serialInputData.getSeasons(),
                                       serialInputData.getYear());
            serials.getSerialList().add(serial);
        }
        for (UserInputData userInputData : input.getUsers()) {
            User user = new User(userInputData.getUsername(),
                                 userInputData.getSubscriptionType(),
                                 userInputData.getHistory(),
                                 userInputData.getFavoriteMovies());
            users.getUserList().add(user);
        }
        actions = input.getCommands();
        videos.rebuildList(movies, serials); // construiesc de la 0 videos
    }

    /**
     * recalculam noi atribute, precum numarul de vizualizari, de prezente
     * in lista de favorite sau numarul de premii
     */
    public void calculateNewFields() {
        movies.updateFavoriteScore(users);
        serials.updateFavoriteScore(users);
        movies.updateViewsScores(users);
        serials.updateViewsScore(users);
        videos.setPopularGenres();
        actors.setAllNumberOfAwards();
    }
}
