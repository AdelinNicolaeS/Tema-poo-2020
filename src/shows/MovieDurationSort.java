package shows;

import java.util.Comparator;

public final class MovieDurationSort implements Comparator<Movie> {

    @Override
    public int compare(final Movie o1, final Movie o2) {
        if (o1.getDuration() == o2.getDuration()) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return (o1.getDuration() - o2.getDuration());
    }
}
