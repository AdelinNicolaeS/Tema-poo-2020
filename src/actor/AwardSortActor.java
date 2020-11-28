package actor;

import java.util.Comparator;

public final class AwardSortActor implements Comparator<Actor> {

    @Override
    public int compare(final Actor o1, final Actor o2) {
        if (o1.getNumberAwards() - o2.getNumberAwards() == 0) {
            return o1.getName().compareTo(o2.getName());
        }
        return o1.getNumberAwards() - o2.getNumberAwards();
    }
}
