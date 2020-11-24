package shows;

import java.util.Comparator;

public class ascDurationSort implements Comparator<Video> {

    @Override
    public int compare(Video o1, Video o2) {
        if(o1.getDuration() == o2.getDuration()) return o1.getTitle().compareTo(o2.getTitle());
        return (o1.getDuration() - o2.getDuration());
    }
}
