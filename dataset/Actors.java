package dataset;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Actors {
    private List<Actor> actorsList = new ArrayList<>();

    public Actors() {

    }

    public Actors(List<Actor> actorsList) {
        this.actorsList.addAll(actorsList);
    }

    public List<Actor> getActorsList() {
        return actorsList;
    }

    public void setActorsList(List<Actor> actorsList) {
        this.actorsList = actorsList;
    }

    public void updateRatings(Movies movies, Serials serials) {
        for (Actor actor : actorsList) {
            actor.setRating(movies, serials);
        }
    }

    public String nameListMessage(int N) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(N, actorsList.size()); i++) {
            names.add(actorsList.get(i).getName());
        }
        return "Query result: " + names.toString();
    }
}
