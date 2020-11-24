package actor;

import java.util.Comparator;

public class ascAwardSortActor implements Comparator<Actor> {

    @Override
    public int compare(Actor o1, Actor o2) {
        if (o1.getNumberAwards() - o2.getNumberAwards() == 0) return o1.getName().compareTo(o2.getName());
        return o1.getNumberAwards() - o2.getNumberAwards();
    }
}
