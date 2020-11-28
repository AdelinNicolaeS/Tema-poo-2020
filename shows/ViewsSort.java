package shows;

import java.util.Comparator;

public final class ViewsSort implements Comparator<Video> {
    @Override
    public int compare(final Video o1, final Video o2) {
        if (o1.getViews() == o2.getViews()) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
        return (o1.getViews() - o2.getViews());
    }
}
