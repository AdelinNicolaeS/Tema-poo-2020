package user;

import dataset.Movies;
import dataset.Serials;
import entertainment.Season;
import shows.Movie;
import shows.Serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private final Map<String, Integer> seasonsWithRating = new HashMap<>();
    private final ArrayList<String> moviesWithRating = new ArrayList<>();

    public Map<String, Integer> getSeasonsWithRating() {
        return seasonsWithRating;
    }

    public ArrayList<String> getMoviesWithRating() {
        return moviesWithRating;
    }

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    @Override
    public String toString() {
        return "User{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }

    /**
     * calculeaza cat de activ a fost utilizatorul
     * @return numarul total de rating-uri pe care le-a dat user-ul
     */
    public int getNumberRatings() {
        return seasonsWithRating.size() + moviesWithRating.size();
    }
    /**
     * verfica daca utilizatorul a vazut video-ul sau nu
     * @param title titlul video-ului
     * @return adevarat sau fals in functie de situatie
     */
    public boolean sawVideo(final String title) {
        for (Map.Entry<String, Integer> entry : history.entrySet()) {
            if (entry.getKey().equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * verfica daca un video este deja in lista de favorite sau nu
     * @param title titlul video-ului la care se face referire
     * @return adevarat sau fals in functie de situatie
     */
    public boolean hasFavoriteAlready(final String title) {
        for (String parser : favoriteMovies) {
            if (parser.equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * adauga in lista de favorite si obtine mesajul de afisat la out
     * @param title titlul video-ului la care se face referire
     * @return mesajul pentru iesire
     */
    public String favoriteMessage(final String title) {
        String message = "";
        if (sawVideo(title) && !hasFavoriteAlready(title)) {
            favoriteMovies.add(title);
            message = "success -> " + title + " was added as favourite";
        } else if (hasFavoriteAlready(title)) {
            message = "error -> " + title + " is already in favourite list";
        } else if (!sawVideo(title)) {
            message = "error -> " + title + " is not seen";
        }
        return message;
    }

    /**
     * adauga un video vazut in istoric si afiseaza mesajul la output
     * @param title titlul video-ului vazut
     * @return mesajul pentru iesire
     */
    public String viewMessage(final String title) {
        if (sawVideo(title)) {
            history.put(title, history.get(title) + 1);
        } else {
            history.put(title, 1);
        }
        return "success -> " + title + " was viewed with total views of " + history.get(title);
    }

    /**
     * verifica daca un sezon a primit deja rating
     * @param title titlul serialului
     * @param seasonNumber numarul sezonului
     * @return valoarea de adevar a verificarii cerute
     */
    public boolean hasSeasonRating(final String title, final Integer seasonNumber) {
        for (Map.Entry<String, Integer> entry : seasonsWithRating.entrySet()) {
            if (entry.getKey().equals(title) && entry.getValue().equals(seasonNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * se adauga o noua nota unui sezon, se recalculeaza coeficientii si
     * se afiseaza la tastatura mesajul corespunzator
     * @param title titlul serialului
     * @param seasonNumber numarul sezonului
     * @param grade nota acordata de utilizator
     * @param serials lista de seriale completa
     * @return mesajul pentru iesire
     */
    public String ratingSeason(final String title,
                                      final Integer seasonNumber,
                                      final double grade,
                                      final Serials serials) {
        if (sawVideo(title) && !hasSeasonRating(title, seasonNumber)) {
            Serial serial = serials.findSerial(title);
            Season season = serial.getSeasons().get(seasonNumber - 1);
            season.getRatings().add(grade);
            season.updateSeasonRating(grade); // actualizeaza sezonul si serialul
            serial.updateSerialRating();
            seasonsWithRating.put(title, seasonNumber);
            return "success -> " + title + " was rated with " + grade + " by " + username;
        } else if (hasSeasonRating(title, seasonNumber)) {
            return "error -> " + title + " has been already rated";
        } else {
            return "error -> " + title + " is not seen";
        }
    }

    /**
     * verifica daca utilizatorul a dat deja rating unui film
     * @param title titlul filmului
     * @return adevarat sau fals
     */
    public boolean hasMovieRating(final String title) {
        for (String string : moviesWithRating) {
            if (string.equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * acorda ratin-ul necesar filmului si updateaza atributele
     * @param title tiltul filmului
     * @param grade nota pe care user-ul o acorda
     * @param movies lista completa a filmelor
     * @return mesajul de afisat la iesire
     */
    public String ratingMovie(final String title, final double grade, final Movies movies) {
        if (sawVideo(title) && !hasMovieRating(title)) {
            Movie movie = movies.findMovie(title);
            movie.getRatings().add(grade);
            movie.updateMovieRating(grade);
            moviesWithRating.add(title);
            return "success -> " + title + " was rated with " + grade + " by " + username;
        } else if (hasMovieRating(title)) {
            return "error -> " + title + " has been already rated";
        } else {
            return "error -> " + title + " is not seen";
        }
    }
}
