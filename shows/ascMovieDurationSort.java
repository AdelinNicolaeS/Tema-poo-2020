package shows;

import java.util.Comparator;

public class ascMovieDurationSort implements Comparator<Movie> {

    @Override
    public int compare(Movie o1, Movie o2) {
        if(o1.getDuration() == o2.getDuration()) return o1.getTitle().compareTo(o2.getTitle());
        return (o1.getDuration() - o2.getDuration());
    }
}
