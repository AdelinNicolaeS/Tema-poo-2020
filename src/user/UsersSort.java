package user;

import java.util.Comparator;

public final class UsersSort implements Comparator<User> {

    @Override
    public int compare(final User o1, final User o2) {
        int x1 = o1.getMoviesWithRating().size() + o1.getSeasonsWithRating().size();
        int x2 = o2.getMoviesWithRating().size() + o2.getSeasonsWithRating().size();
        if (x1 == x2) {
            return o1.getUsername().compareTo(o2.getUsername());
        }
        return (x1 - x2);
    }
}
