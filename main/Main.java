package main;

import actor.AwardSortActor;
import actor.NameSortActor;
import actor.SortByRating;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import dataset.Actors;
import dataset.Users;
import dataset.DataCenter;
import dataset.Serials;
import dataset.Movies;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import shows.FavoriteSort;

import shows.MovieDurationSort;
import shows.RatingSort;

import shows.SerialDurationSort;

import shows.ViewsSort;
import user.User;
import user.UsersSort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
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
        DataCenter dataCenter = new DataCenter();
        dataCenter.initializeData(input);
        dataCenter.calculateNewFields();

        for (ActionInputData action : input.getCommands()) {
            int id = action.getActionId();
            String message;
            if (action.getActionType().equals("command")) {
                User user = dataCenter.getUsers().findUserByName(action.getUsername());
                if (action.getType().equals("favorite")) {
                    message = user.favoriteMessage(action.getTitle());
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                    dataCenter.calculateNewFields();
                } else if (action.getType().equals("view")) {
                    message = user.viewMessage(action.getTitle());
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                    dataCenter.calculateNewFields();
                } else if (action.getType().equals("rating")) {
                    String title = action.getTitle();
                    if (action.getSeasonNumber() != 0) {
                        Integer seasonNumber = action.getSeasonNumber();
                        message = user.ratingSeason(title,
                                                    seasonNumber,
                                                    action.getGrade(),
                                                    dataCenter.getSerials());
                        arrayResult.add(fileWriter.writeFile(id, "csf", message));
                    } else {
                        message = user.ratingMovie(title,
                                                   action.getGrade(),
                                                   dataCenter.getMovies());
                        arrayResult.add(fileWriter.writeFile(id, "csf", message));
                    }
                    dataCenter.getActors().updateRatings(dataCenter.getMovies(),
                                                         dataCenter.getSerials());
                }

            } else if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    int number;
                    Actors actorsTmp = new Actors(dataCenter.getActors().getActorsList());
                    if (action.getCriteria().equals("average")) {
                        number = action.getNumber();
                    } else {
                        number = actorsTmp.getActorsList().size();
                    }
                    if (action.getCriteria().equals("average")) {
                        actorsTmp.getActorsList().removeIf((v) -> v.getRating() == 0);
                        actorsTmp.getActorsList().sort(new SortByRating());
                    } else if (action.getCriteria().equals("awards")) {
                        List<String> awardsList;
                        awardsList = action.getFilters().get(action.getFilters().size() - 1);
                        actorsTmp.getActorsList().removeIf((v) -> v.hasAwards(awardsList) == 0);
                        actorsTmp.getActorsList().sort(new AwardSortActor());
                    } else if (action.getCriteria().equals("filter_description")) {
                        List<String> list = action.getFilters().get(2);
                        actorsTmp.getActorsList().removeIf((v) -> !v.descriptionHasWords(list));
                        actorsTmp.getActorsList().sort(new NameSortActor());
                    }
                    if (action.getSortType().equals("desc")) {
                        Collections.reverse(actorsTmp.getActorsList());
                    }
                    message = actorsTmp.nameListMessage(number);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getObjectType().equals("movies")) {
                    Movies moviesTmp = new Movies(dataCenter.getMovies().getMovieList());
                    moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(action.getFilters()));
                    if (action.getCriteria().equals("ratings")) {
                        moviesTmp.getMovieList().removeIf((v) -> v.getRating() == 0);
                        moviesTmp.getMovieList().sort(new RatingSort());
                    } else if (action.getCriteria().equals("favorite")) {
                        moviesTmp.getMovieList().removeIf((v) -> v.getFavorite() == 0);
                        moviesTmp.getMovieList().sort(new FavoriteSort());
                    } else if (action.getCriteria().equals("longest")) {
                        moviesTmp.getMovieList().sort(new MovieDurationSort());
                    } else if (action.getCriteria().equals("most_viewed")) {
                        moviesTmp.getMovieList().removeIf((v) -> v.getViews() == 0);
                        moviesTmp.getMovieList().sort(new ViewsSort());
                    }
                    if (action.getSortType().equals("desc")) {
                        Collections.reverse(moviesTmp.getMovieList());
                    }
                    message = moviesTmp.movieListMessage(action.getNumber());
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getObjectType().equals("shows")) {
                    Serials serialsTmp = new Serials(dataCenter.getSerials().getSerialList());
                    List<List<String>> filter = action.getFilters();
                    serialsTmp.getSerialList().removeIf(((v) -> !v.checkFilters(filter)));
                    if (action.getCriteria().equals("ratings")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getRating() == 0);
                        serialsTmp.getSerialList().sort(new RatingSort());
                    } else if (action.getCriteria().equals("favorite")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getFavorite() == 0);
                        serialsTmp.getSerialList().sort(new FavoriteSort());
                    } else if (action.getCriteria().equals("longest")) {
                        serialsTmp.getSerialList().sort(new SerialDurationSort());
                    } else if (action.getCriteria().equals("most_viewed")) {
                        serialsTmp.getSerialList().removeIf((v) -> v.getViews() == 0);
                        serialsTmp.getSerialList().sort(new ViewsSort());
                    }
                    if (action.getSortType().equals("desc")) {
                        Collections.reverse(serialsTmp.getSerialList());
                    }
                    message = serialsTmp.serialListMessage(action.getNumber());
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getObjectType().equals("users")) {
                    Users usersTmp = new Users(dataCenter.getUsers().getUserList());
                    usersTmp.getUserList().removeIf((v) -> v.getNumberRatings() == 0);
                    usersTmp.getUserList().sort(new UsersSort());
                    if (action.getSortType().equals("desc")) {
                        Collections.reverse(usersTmp.getUserList());
                    }
                    message = usersTmp.usersListMessage(action.getNumber());
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                }

            } else if (action.getActionType().equals("recommendation")) {
                User userTmp = dataCenter.getUsers().findUserByName(action.getUsername());
                if (action.getType().equals("standard")) {
                    message =  dataCenter.getVideos().standardRecommendation(userTmp);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getType().equals("best_unseen")) {
                    message = dataCenter.getVideos().bestUnseenRecommendation(userTmp);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getType().equals("popular")) {
                    message = dataCenter.getVideos().popularRecommendation(userTmp);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getType().equals("favorite")) {
                    message = dataCenter.getVideos().favoriteRecommandation(userTmp);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                } else if (action.getType().equals("search")) {
                    String genre = action.getGenre();
                    message = dataCenter.getVideos().searchRecommandation(userTmp, genre);
                    arrayResult.add(fileWriter.writeFile(id, "csf", message));
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}

