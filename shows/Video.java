package shows;

import dataset.Users;
import user.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Video {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;
    private double rating = 0;
    private int favorite = 0;
    private int views = 0;

    public int getViews() {
        return views;
    }

    public void setViewsScore(Users users) {
        int value = 0;
        for(User user : users.getUserList()) {
            if(user.getHistory().get(title) != null) {
                value += user.getHistory().get(title);
            }
        }
        views = value;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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

    public boolean checkFilterYear(List<List<String>> filters) {
        if (filters.get(0).get(0) == null) return true;
        int y = Integer.parseInt(filters.get(0).get(0));
        return y == year;
    }

    public boolean checkFilterGenre(List<List<String>> filters) {
        /*if(filters.get(1).get(0) == null) return true;
        String g = filters.get(1).get(0);
        for(String genre : genres) {
            if(Objects.equals(g, genre)) return true;
        }
        return false;
    */
        boolean ok;
        if(filters.get(1).get(0) == null) return true;
        List<String> genresList = filters.get(1);
        for (String g1 : genresList) {
            ok = false;
            for (String g2 : genres) {
                if (g2.equals(g1)) {
                    ok = true;
                    break;
                }
            }
            if (!ok) return false;
        }
        return true;
    }

    public boolean checkFilters(List<List<String>> filters) {
        return (checkFilterGenre(filters) && checkFilterYear(filters));
    }

    public void setFavoriteScore(Users users) {
        int total = 0;
        for (User user : users.getUserList()) {
            if (user.getFavoriteMovies().contains(getTitle())) {
                total++;
            }
        }
        this.setFavorite(total);
    }

    public abstract int getDuration();
}


