package shows;

import entertainment.Season;

import java.util.ArrayList;

public final class Serial extends Video {

    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "Serial{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }

    /**
     * recalculeaza rating-ul serialului pe baza notelor sezoanelor
     */
    public void updateSerialRating() {
        double average = 0; // ratingul serialului e media sezoanelor
        for (Season season : seasons) {
            average += season.getRating();
        }
        average /= seasons.size();
        this.setRating(average);
    }

    @Override
    public int getDuration() {
        int duration = 0; // durata sezonului e suma duratelor sezoanelor
        for (Season season : seasons) {
            duration += season.getDuration();
        }
        return duration;
    }

}
