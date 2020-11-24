package entertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    private int numberOfRatings = 0;

    private double rating = 0;

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }

    public Double averageSeasonRating() {
        Double average = (double) 0;
        for (Double i : ratings) {
            average += i;
        }
        average /= ratings.size();
        return average;
    }

    public void updateSeasonRating(double grade) {
        rating = (numberOfRatings * rating + grade) / (numberOfRatings + 1);
        numberOfRatings++;

        /*double average = 0;
        for(Double value : ratings) {
            average += value;
        }
        average /= ratings.size();
        rating = average;
        System.out.println("fucking rating este " + rating);
    }*/
    }
}

