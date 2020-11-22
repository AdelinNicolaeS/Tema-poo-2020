package main;

import actor.Actor;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import dataset.Actors;
import dataset.Movies;
import dataset.Serials;
import dataset.Users;
import fileio.*;
import org.json.simple.JSONArray;
import shows.Movie;
import shows.Serial;
import user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            User user = new User(userInputData.getUsername(), userInputData.getSubscriptionType(), userInputData.getHistory(), userInputData.getFavoriteMovies());
            users.getUserList().add(user);
        }
        for (ActionInputData actionInputData : input.getCommands()) {
            if (actionInputData.getActionType().equals("command")) {
                switch (actionInputData.getType()) {
                    case "favorite" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        assert user != null;
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "mlc", user.favoriteMessage(actionInputData.getTitle())));
                    }
                    case "view" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        assert user != null;
                        arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "mlc", user.viewMessage(actionInputData.getTitle())));
                    }
                    case "rating" -> {
                        User user = users.findUserByName(actionInputData.getUsername());
                        String title = actionInputData.getTitle();
                        assert user != null;
                        if (actionInputData.getSeasonNumber() != 0) {
                            Integer seasonNumber = actionInputData.getSeasonNumber();
                            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "mlc", user.ratingSeasonMessage(title, seasonNumber, actionInputData.getGrade(), serials)));
                        } else {
                            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "mlc", user.ratingMovieMessage(title, actionInputData.getGrade(), movies)));
                        }
                    }
                    default -> {
                        break;
                    }
                }
            } else if(actionInputData.getActionType().equals("query")) {
                if(actionInputData.getObjectType().equals("actors") && actionInputData.getCriteria().equals("average")) {

                }

            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
