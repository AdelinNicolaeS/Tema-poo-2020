package main;

import common.Constants;
import actor.AwardSortActor;
import actor.NameSortActor;
import actor.SortByRating;
import checker.Checker;
import checker.Checkstyle;
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

        DataCenter dataCenter = new DataCenter();
        dataCenter.initializeData(input);
        dataCenter.calculateNewFields();
        for (ActionInputData action : input.getCommands()) {
            int id = action.getActionId();
            String message;
            switch (action.getActionType()) {
                case Constants.COMMAND:
                    User user = dataCenter.getUsers().findUserByName(action.getUsername());
                    switch (action.getType()) {
                        case "favorite" -> {
                            message = user != null ? user.favoriteMessage(action.getTitle()) : null;
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                            dataCenter.calculateNewFields();
                        }
                        case "view" -> {
                            message = user != null ? user.viewMessage(action.getTitle()) : null;
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                            dataCenter.calculateNewFields();
                        }
                        case "rating" -> {
                            String title = action.getTitle();
                            if (action.getSeasonNumber() != 0) {
                                Integer seasonNumber = action.getSeasonNumber();
                                if (user != null) {
                                    message = user.ratingSeason(title,
                                                                seasonNumber,
                                                                action.getGrade(),
                                                                dataCenter.getSerials());
                                } else  {
                                    message = null;
                                }
                            } else {
                                if (user != null) {
                                    message = user.ratingMovie(title,
                                                               action.getGrade(),
                                                               dataCenter.getMovies());
                                } else {
                                    message = null;
                                }
                            }
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                            dataCenter.getActors().updateRatings(dataCenter.getMovies(),
                                                                 dataCenter.getSerials());
                        }
                        default -> {
                            return;
                        }
                    }

                    break;
                case Constants.QUERY:
                    switch (action.getObjectType()) {
                        case Constants.ACTORS -> {
                            int number;
                            Actors actorsTmp = new Actors(dataCenter.getActors().getActorsList());
                            if (action.getCriteria().equals("average")) {
                                number = action.getNumber();
                            } else {
                                number = actorsTmp.getActorsList().size();
                            }
                            switch (action.getCriteria()) {
                                case "average" -> {
                                    actorsTmp.getActorsList().removeIf((v) -> v.getRating() == 0);
                                    actorsTmp.getActorsList().sort(new SortByRating());
                                }
                                case Constants.AWARDS -> {
                                    List<String> l;
                                    int pos = action.getFilters().size() - 1;
                                    l = action.getFilters().get(pos);
                                    actorsTmp.getActorsList().removeIf((v) -> v.hasAwards(l) == 0);
                                    actorsTmp.getActorsList().sort(new AwardSortActor());
                                }
                                case "filter_description" -> {
                                    List<String> list = action.getFilters().get(2);
                                    actorsTmp.getActorsList().removeIf((v) -> !v.descriptionHasWords(list));
                                    actorsTmp.getActorsList().sort(new NameSortActor());
                                }
                                default -> {
                                    return;
                                }
                            }
                            if (action.getSortType().equals("desc")) {
                                Collections.reverse(actorsTmp.getActorsList());
                            }
                            message = actorsTmp.nameListMessage(number);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case Constants.MOVIES -> {
                            Movies moviesTmp = new Movies(dataCenter.getMovies().getMovieList());
                            moviesTmp.getMovieList().removeIf((v) -> !v.checkFilters(action.getFilters()));
                            switch (action.getCriteria()) {
                                case "ratings" -> {
                                    moviesTmp.getMovieList().removeIf((v) -> v.getRating() == 0);
                                    moviesTmp.getMovieList().sort(new RatingSort());
                                }
                                case "favorite" -> {
                                    moviesTmp.getMovieList().removeIf((v) -> v.getFavorite() == 0);
                                    moviesTmp.getMovieList().sort(new FavoriteSort());
                                }
                                case "longest" -> moviesTmp.getMovieList().sort(new MovieDurationSort());
                                case "most_viewed" -> {
                                    moviesTmp.getMovieList().removeIf((v) -> v.getViews() == 0);
                                    moviesTmp.getMovieList().sort(new ViewsSort());
                                }
                                default -> {
                                    return;
                                }
                            }
                            if (action.getSortType().equals("desc")) {
                                Collections.reverse(moviesTmp.getMovieList());
                            }
                            message = moviesTmp.movieListMessage(action.getNumber());
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case Constants.SHOWS -> {
                            Serials serialsTmp = new Serials(dataCenter.getSerials().getSerialList());
                            List<List<String>> filter = action.getFilters();
                            serialsTmp.getSerialList().removeIf(((v) -> !v.checkFilters(filter)));
                            switch (action.getCriteria()) {
                                case "ratings" -> {
                                    serialsTmp.getSerialList().removeIf((v) -> v.getRating() == 0);
                                    serialsTmp.getSerialList().sort(new RatingSort());
                                }
                                case "favorite" -> {
                                    serialsTmp.getSerialList().removeIf((v) -> v.getFavorite() == 0);
                                    serialsTmp.getSerialList().sort(new FavoriteSort());
                                }
                                case "longest" -> serialsTmp.getSerialList().sort(new SerialDurationSort());
                                case "most_viewed" -> {
                                    serialsTmp.getSerialList().removeIf((v) -> v.getViews() == 0);
                                    serialsTmp.getSerialList().sort(new ViewsSort());
                                }
                                default -> {
                                    return;
                                }
                            }
                            if (action.getSortType().equals("desc")) {
                                Collections.reverse(serialsTmp.getSerialList());
                            }
                            message = serialsTmp.serialListMessage(action.getNumber());
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case Constants.USERS -> {
                            if (!action.getCriteria().equals(Constants.NUM_RATINGS)) {
                                return;
                            }
                            Users usersTmp = new Users(dataCenter.getUsers().getUserList());
                            usersTmp.getUserList().removeIf((v) -> v.getNumberRatings() == 0);
                            usersTmp.getUserList().sort(new UsersSort());
                            if (action.getSortType().equals("desc")) {
                                Collections.reverse(usersTmp.getUserList());
                            }
                            message = usersTmp.usersListMessage(action.getNumber());
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        default -> {
                            return;
                        }
                    }

                    break;
                case Constants.RECOMMENDATION:
                    User userTmp = dataCenter.getUsers().findUserByName(action.getUsername());
                    assert userTmp != null;
                    switch (action.getType()) {
                        case "standard" -> {
                            message = dataCenter.getVideos().standardRecommendation(userTmp);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case "best_unseen" -> {
                            message = dataCenter.getVideos().bestUnseenRecommendation(userTmp);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case "popular" -> {
                            message = dataCenter.getVideos().popularRecommendation(userTmp);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case "favorite" -> {
                            message = dataCenter.getVideos().favoriteRecommandation(userTmp);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        case "search" -> {
                            String genre = action.getGenre();
                            message = dataCenter.getVideos().searchRecommandation(userTmp, genre);
                            // noinspection unchecked
                            arrayResult.add(fileWriter.writeFile(id, "csf", message));
                        }
                        default -> {
                            return;
                        }
                    }
                    break;
                default:
                    return;
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}

