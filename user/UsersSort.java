package user;

import java.util.Comparator;

public class UsersSort implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        if(o1.getMoviesWithRating().size() + o1.getSeasonsWithRating().size() == o2.getMoviesWithRating().size() + o2.getSeasonsWithRating().size()) {
            return o1.getUsername().compareTo(o2.getUsername());
        }
        return (o1.getMoviesWithRating().size() + o1.getSeasonsWithRating().size() - o2.getMoviesWithRating().size() - o2.getSeasonsWithRating().size());
    }
}
