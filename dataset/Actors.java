package dataset;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Actors {
    private List<Actor> actorsList = new ArrayList<>();

    public List<Actor> getActorsList() {
        return actorsList;
    }

    public void setActorsList(List<Actor> actorsList) {
        this.actorsList = actorsList;
    }


}
