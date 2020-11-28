package dataset;

import actor.Actor;

import java.util.ArrayList;
import java.util.List;

public final class Actors {
    private List<Actor> actorsList = new ArrayList<>();

    public Actors() {

    }

    public Actors(final List<Actor> actorsList) {
        this.actorsList.addAll(actorsList);
    }

    public List<Actor> getActorsList() {
        return actorsList;
    }

    /**
     *
     * @param movies
     * @param serials
     */
    public void updateRatings(final Movies movies, final Serials serials) {
        for (Actor actor : actorsList) {
            actor.setRating(movies, serials);
        }
    }

    /**
     *
     */
    public void setAllNumberOfAwards() {
        for (Actor actor : actorsList) {
            actor.setNumberAwards();
        }
    }

    /**
     *
     * @param n
     * @return
     */
    public String nameListMessage(final int n) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(n, actorsList.size()); i++) {
            names.add(actorsList.get(i).getName());
        }
        return "Query result: " + names.toString();
    }
}
