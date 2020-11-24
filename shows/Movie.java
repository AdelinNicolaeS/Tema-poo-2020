package shows;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    private final int duration;

    private final List<Double> ratings = new ArrayList<>(); // de ce e final??????????????
    private int numberOfRatings = 0;

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Movie{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }


    public void updateMovieRating(Double grade) {
        this.setRating((numberOfRatings * this.getRating() + grade) / (numberOfRatings + 1));
        numberOfRatings++;
    }

}
