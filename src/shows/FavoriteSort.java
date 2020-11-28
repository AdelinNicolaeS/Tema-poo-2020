package shows;

import java.util.Comparator;

public final class FavoriteSort implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        if (o1.getFavorite() == o2.getFavorite()) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return (o1.getFavorite() - o2.getFavorite());
    }
}
