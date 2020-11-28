package shows;

import dataset.Users;
import user.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Video {
    /**
     * Show's title
     */
    private String title;
    /**
     * The year the show was released
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    private double rating = 0;
    private int favorite = 0;
    private int views = 0;

    public final int getViews() {
        return views;
    }

    /**
     * seteaza numarul de vizualizari pentru un video
     * @param users utilizatorii existenti
     */
    public void setViewsScore(final Users users) {
        int value = 0;
        for (User user : users.getUserList()) {
            if (user.getHistory().get(title) != null) {
                value += user.getHistory().get(title);
            }
        }
        views = value;
    }

    public final int getFavorite() {
        return favorite;
    }

    public final void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    public final double getRating() {
        return rating;
    }

    public final void setRating(final double rating) {
        this.rating = rating;
    }

    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * verifica daca respecta filtrul de an
     * @param filters lista de filtre cerute
     * @return adevarat sau fals, daca nu respecta filtrul
     */
    public boolean checkFilterYear(final List<List<String>> filters) {
        if (filters.get(0).get(0) == null) { // daca filtrul nu contine anul
            return true;
        }
        int y = Integer.parseInt(filters.get(0).get(0));
        return y == year;
    }

    /**
     * verifica daca trece filtrul genului
     * @param filters filtrele cerute la input
     * @return adevarat sau fals, in functie de rezultat
     */
    public boolean checkFilterGenre(final List<List<String>> filters) {
        boolean ok;
        if (filters.get(1).get(0) == null) { // daca filtrul cu contine genuri
            return true;
        }
        List<String> genresList = filters.get(1);
        for (String g1 : genresList) {
            ok = false;
            for (String g2 : genres) {
                if (g2.equals(g1)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    /**
     * verfica daca trece filtrele
     * @param filters filtrele cerute
     * @return adevarat sau fals in functie de ce gaseste
     */
    public boolean checkFilters(final List<List<String>> filters) {
        return (checkFilterGenre(filters) && checkFilterYear(filters));
    }

    /**
     * obtine numarul de prezente in lista de favorite
     * @param users baza de date de utilizatori
     */
    public void setFavoriteScore(final Users users) {
        int total = 0;
        for (User user : users.getUserList()) {
            if (user.getFavoriteMovies().contains(getTitle())) {
                total++;
            }
        }
        this.setFavorite(total);
    }

    /**
     *
     * @return durata totala a video-ului
     */
    public abstract int getDuration();
}


