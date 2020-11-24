package shows;

import java.util.Comparator;

public class ascRatingSort implements Comparator<Video> {

    @Override
    public int compare(Video o1, Video o2) {
        if(o1.getRating() == o2.getRating()) return o1.getTitle().compareTo(o2.getTitle());
        return (int) (o1.getRating() - o2.getRating());
    }
}
