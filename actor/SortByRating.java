package actor;

import java.util.Comparator;

public final class SortByRating implements Comparator<Actor> {
    @Override
    public int compare(final Actor o1, final Actor o2) {
        if (o1.getRating().equals(o2.getRating())) {
            return o1.getName().compareTo(o2.getName());
        }
        if (o1.getRating() > o2.getRating()) {
            return 1;
        }
        return -1;
    }
}
