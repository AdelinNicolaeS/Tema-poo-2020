package main;

import actor.*;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import dataset.Actors;
import dataset.Movies;
import dataset.Serials;
import dataset.Users;
import fileio.*;
import org.json.simple.JSONArray;
import shows.*;
import user.User;
import user.UsersSort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        Actors actors = new Actors();
        Movies movies = new Movies();
        Serials serials = new Serials();
        Users users = new Users();

        for (ActorInputData actorInput : input.getActors()) {
            Actor actor = new Actor(actorInput.getName(), actorInput.getCareerDescription(), actorInput.getFilmography(), actorInput.getAwards());
            actors.getActorsList().add(actor);
        }
        for (MovieInputData movieInput : input.getMovies()) {
            Movie movie = new Movie(movieInput.getTitle(), movieInput.getCast(), movieInput.getGenres(), movieInput.getYear(), movieInput.getDuration());
            movies.getMovieList().add(movie);
        }
        for (SerialInputData serialInputData : input.getSerials()) {
            Serial serial = new Serial(serialInputData.getTitle(), serialInputData.getCast(), serialInputData.getGenres(), serialInputData.getNumberSeason(), serialInputData.getSeasons(), serialInputData.getYear());
            serials.getSerialList().add(serial);
        }
        for (UserInputData userInputData : input.getUsers()) {
            User user = new User(userInputData.getUsername(),
                    userInputData.getSubscriptionType(),
                    userInputData.getHistory(),
                    userInputData.getFavoriteMovies());
            users.getUserList().add(user);
        }

        movies.updateFavoriteScore(users);
        serials.updateFavoriteScore(users);
        movies.updateViewsScores(users);
        serials.updateViewsScore(users);

        for (ActionInputData actionInputData : input.getCommands()) {
            if (actionInputData.getActionType().equals("command")) {
                switch (actionInputData.getType()) {
                    case "favorite" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        assert user != null;
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", user.favoriteMessage(actionInputData.getTitle())));
                        movies.updateFavoriteScore(users);
                        serials.updateFavoriteScore(users);
                    }
                    case "view" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        assert user != null;
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", user.viewMessage(actionInputData.getTitle())));
                        movies.updateViewsScores(users);
                        serials.updateViewsScore(users);
                    }
                    case "rating" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        String title = actionInputData.getTitle();
                        assert user != null;
                        if (actionInputData.getSeasonNumber() != 0) {
                            Integer seasonNumber = actionInputData.getSeasonNumber();
                            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", user.ratingSeasonMessage(title, seasonNumber, actionInputData.getGrade(), serials)));
                        } else {
                            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", user.ratingMovieMessage(title, actionInputData.getGrade(), movies)));
                        }
                        actors.updateRatings(movies, serials);
                    }
                    default -> {
                        break;
                    }
                }
            } else if (actionInputData.getActionType().equals("query")) {
                if (actionInputData.getObjectType().equals("actors")) {
                    if (actionInputData.getCriteria().equals("average")) {
                        int N = actionInputData.getNumber();
                        Actors actorsTmp = new Actors(actors.getActorsList());
                        actorsTmp.getActorsList().removeIf((v) -> v.getRating() == 0);
                        Collections.sort(actorsTmp.getActorsList(), new ascSortByRating());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(actorsTmp.getActorsList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", actorsTmp.nameListMessage(N)));
                    } else if (actionInputData.getCriteria().equals("awards")) {
                        Actors actorsTmp = new Actors(actors.getActorsList());
                        actorsTmp.getActorsList().removeIf((v) -> v.hasAwards(actionInputData.getFilters().get(3)) == 0);
                        Collections.sort(actorsTmp.getActorsList(), new ascAwardSortActor());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(actorsTmp.getActorsList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", actorsTmp.nameListMessage(actorsTmp.getActorsList().size())));
                    } else if (actionInputData.getCriteria().equals("filter_description")) {
                        Actors actorsTmp = new Actors(actors.getActorsList());
                        actorsTmp.getActorsList().removeIf((v) -> !v.descriptionHasWords(actionInputData.getFilters().get(2)));
                        Collections.sort(actorsTmp.getActorsList(), new ascNameSortActor());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(actorsTmp.getActorsList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", actorsTmp.nameListMessage(actorsTmp.getActorsList().size())));
                    }
                } else if (actionInputData.getObjectType().equals("movies")) {
                    int N = actionInputData.getNumber();
                    Movies moviesTmp = new Movies(movies.getMovieList());
                    if (actionInputData.getCriteria().equals("ratings")) {
                        moviesTmp.getMovieList().removeIf((v) -> v.getRating() == 0);
                        moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(moviesTmp.getMovieList(), new ascRatingSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(moviesTmp.getMovieList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", moviesTmp.movieListMessage(N)));
                    } else if (actionInputData.getCriteria().equals("favorite")) {
                        moviesTmp.getMovieList().removeIf((v) -> v.getFavorite() == 0);
                        moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(moviesTmp.getMovieList(), new ascFavoriteSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(moviesTmp.getMovieList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", moviesTmp.movieListMessage(N)));

                    } else if (actionInputData.getCriteria().equals("longest")) {
                        moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(moviesTmp.getMovieList(), new ascDurationSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(moviesTmp.getMovieList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", moviesTmp.movieListMessage(N)));
                    } else if(actionInputData.getCriteria().equals("most_viewed")) {
                        moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        moviesTmp.getMovieList().removeIf((v) -> v.getViews() == 0);
                        Collections.sort(moviesTmp.getMovieList(), new ascViewsSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(moviesTmp.getMovieList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", moviesTmp.movieListMessage(N)));
                    }
                } else if (actionInputData.getObjectType().equals("shows")) {
                    int N = actionInputData.getNumber();
                    Serials serialsTmp = new Serials(serials.getSerialList());
                    if (actionInputData.getCriteria().equals("ratings")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getRating() == 0);
                        serialsTmp.getSerialList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(serialsTmp.getSerialList(), new ascRatingSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(serialsTmp.getSerialList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", serialsTmp.serialListMessage(N)));
                    } else if (actionInputData.getCriteria().equals("favorite")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getFavorite() == 0);
                        serialsTmp.getSerialList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(serialsTmp.getSerialList(), new ascFavoriteSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(serialsTmp.getSerialList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", serialsTmp.serialListMessage(N)));
                    } else if (actionInputData.getCriteria().equals("longest")) {
                        serialsTmp.getSerialList().removeIf((v) -> !v.checkFilters(actionInputData.getFilters()));
                        Collections.sort(serialsTmp.getSerialList(), new ascFavoriteSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(serialsTmp.getSerialList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", serialsTmp.serialListMessage(N)));
                    } else if(actionInputData.getCriteria().equals("most_viewed")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getViews() == 0);
                        serialsTmp.getSerialList().removeIf(((v) -> !v.checkFilters(actionInputData.getFilters())));
                        Collections.sort(serialsTmp.getSerialList(), new ascViewsSort());
                        if (actionInputData.getSortType().equals("desc")) {
                            Collections.reverse(serialsTmp.getSerialList());
                        }
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", serialsTmp.serialListMessage(N)));
                    }
                } else if(actionInputData.getObjectType().equals("users") && actionInputData.getCriteria().equals("num_ratings")) {
                    Users usersTmp = new Users(users.getUserList());
                    int n = actionInputData.getNumber();
                    usersTmp.getUserList().removeIf((v) -> v.getSeasonsWithRating().size() + v.getMoviesWithRating().size() == 0);
                    Collections.sort(usersTmp.getUserList(), new UsersSort());
                    if(actionInputData.getSortType().equals("desc")) {
                        Collections.reverse(usersTmp.getUserList());
                    }
                    arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "csf", usersTmp.UsersListMessage(n)));
                }

            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}

