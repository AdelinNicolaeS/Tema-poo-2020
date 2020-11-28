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

    /**
     *
     * @return
     */
    public int getViews() {
        return views;
    }

    /**
     *
     * @param users
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

    /**
     *
     * @return
     */
    public int getFavorite() {
        return favorite;
    }

    /**
     *
     * @param favorite
     */
    public void setFavorite(final int favorite) {
        this.favorite = favorite;
    }

    /**
     *
     * @return
     */
    public double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }

    /**
     *
     * @param title
     * @param year
     * @param cast
     * @param genres
     */
    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    /**
     *
     * @return
     */
    public final String getTitle() {
        return title;
    }

    /**
     *
     * @return
     */
    public final int getYear() {
        return year;
    }

    /**
     *
     * @return
     */
    public final ArrayList<String> getCast() {
        return cast;
    }

    /**
     *
     * @return
     */
    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     *
     * @param filters
     * @return
     */
    public boolean checkFilterYear(final List<List<String>> filters) {
        if (filters.get(0).get(0) == null) {
            return true;
        }
        int y = Integer.parseInt(filters.get(0).get(0));
        return y == year;
    }

    /**
     *
     * @param filters
     * @return
     */
    public boolean checkFilterGenre(final List<List<String>> filters) {
        boolean ok;
        if (filters.get(1).get(0) == null) {
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
     *
     * @param filters
     * @return
     */
    public boolean checkFilters(final List<List<String>> filters) {
        return (checkFilterGenre(filters) && checkFilterYear(filters));
    }

    /**
     *
     * @param users
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
     * @return
     */
    public abstract int getDuration();
}


