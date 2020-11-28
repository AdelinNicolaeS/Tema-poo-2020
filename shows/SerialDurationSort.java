package shows;

import entertainment.Season;

import java.util.Comparator;

public final class SerialDurationSort implements Comparator<Serial> {

    @Override
    public int compare(final Serial o1, final Serial o2) {
        int duration1 = 0, duration2 = 0;
        for (Season season : o1.getSeasons()) {
            duration1 += season.getDuration();
        }
        for (Season season : o2.getSeasons()) {
            duration2 += season.getDuration();
        }
        return (duration1 - duration2);
    }
}
