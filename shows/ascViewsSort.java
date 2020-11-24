package shows;

import java.util.Comparator;

public class ascViewsSort implements Comparator<Video> {
    @Override
    public int compare(Video o1, Video o2) {
        if(o1.getViews() == o2.getViews()) return o1.getTitle().compareTo(o2.getTitle());
        return (o1.getViews() - o2.getViews());
    }
}
