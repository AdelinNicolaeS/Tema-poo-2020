package actor;

import java.util.Comparator;

public class ascSortByRating implements Comparator<Actor> {
    @Override
    public int compare(Actor o1, Actor o2) {
        if (o1.getRating().equals(o2.getRating())) return o1.getName().compareTo(o2.getName());
        return (int) (o1.getRating() - o2.getRating());
    }
}
