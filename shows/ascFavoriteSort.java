package shows;

import java.util.Comparator;

public class ascFavoriteSort implements Comparator<Video> {
    @Override
    public int compare(Video o1, Video o2) {
        if (o1.getFavorite() == o2.getFavorite()) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return (o1.getFavorite() - o2.getFavorite());
    }
}
