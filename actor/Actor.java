package actor;

import dataset.Movies;
import dataset.Serials;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;
    private Double rating = (double) 0;
    private int numberAwards = 0;

    public int getNumberAwards() {
        return numberAwards;
    }

    /**
     *
     */
    public void setNumberAwards() {
        int sum = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            sum += entry.getValue();
        }
        this.numberAwards = sum;
    }

    public Actor(final String name,
                 final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    @Override
    public String toString() {
        return "Actor{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }

    /**
     *
     * @param movies
     * @param serials
     */
    public void setRating(final Movies movies, final Serials serials) {
        double average = 0;
        int count = 0;
        for (String video : filmography) {
            if (movies.findMovie(video) != null && movies.findMovie(video).getRating() > 0) {
                average += movies.findMovie(video).getRating();
                count++;
            } else {
                boolean x1 = (serials.findSerial(video) != null);
                if (x1 && serials.findSerial(video).getRating() > 0) {
                    average += serials.findSerial(video).getRating();
                    count++;
                }
            }
        }
        if (count == 0) {
            rating = (double) 0;
            return;
        }
        average /= count;
        rating = average;
    }

    public Double getRating() {
        return rating;
    }

    /**
     *
     * @param awardsList
     * @return
     */
    public int hasAwards(final List<String> awardsList) {
        int ok = 1;
        for (String award : awardsList) {
            int s = 0;
            for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
                if (entry.getKey().name().equals(award)) {
                    s += entry.getValue();
                }
            }
            if (s == 0) {
                ok = 0;   // ok verifica daca are toate premiile necesare
                break;
            }
        }
        return ok;
    }

    /**
     *
     * @param words
     * @return
     */
    public boolean descriptionHasWords(final List<String> words) {
        for (String word : words) {
            Pattern pattern;
            pattern = Pattern.compile("(?i)\\b(" + word + ")\\b(?i)", Pattern.CASE_INSENSITIVE);
            if (!pattern.matcher(careerDescription).find()) {
                return false;
            }
        }
        return true;
    }
}
