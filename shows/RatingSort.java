package shows;

import java.util.Comparator;

public final class RatingSort implements Comparator<Video> {

    @Override
    public int compare(final Video o1, final Video o2) {
        if (o1.getRating() == o2.getRating()) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return (int) (o1.getRating() - o2.getRating());
    }
}
