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

    public void setNumberAwards(int numberAwards) {
        this.numberAwards = numberAwards;
    }

    public Actor(final String name, final String careerDescription, final ArrayList<String> filmography, final Map<ActorsAwards, Integer> awards) {
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

    public String getCareerDescription() {
        return careerDescription;
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

    public void setRating(Movies movies, Serials serials) {
        double average = 0;
        int count = 0;
        for (String video : filmography) {
            if (movies.findMovie(video) != null && movies.findMovie(video).getRating() > 0) {
                average += movies.findMovie(video).getRating();
                count++;
            } else if (serials.findSerial(video) != null && serials.findSerial(video).getRating() > 0) {
                average += serials.findSerial(video).getRating();
                count++;
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

    public int hasAwards(List<String> awardsList) {
        int totalAwards = 0, ok = 1;
        for (String award : awardsList) {
            int s = 0;
            for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
                if (entry.getKey().name().equals(award)) {
                    s += entry.getValue();
                }
            }
            if (s == 0) ok = 0;   // ok verifica daca are toate premiile necesare
            totalAwards += s;
        }
        numberAwards = totalAwards;
        if (ok == 0) return 0;
        return totalAwards;
    }

    public boolean descriptionHasWords(List<String> words) {
        for (String word : words) {
            if (!Pattern.compile(word, Pattern.CASE_INSENSITIVE).matcher(careerDescription).find()) return false;
        }
        return true;
    }
}