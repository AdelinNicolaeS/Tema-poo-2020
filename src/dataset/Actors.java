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
     * recalculeaza rating-urile actorilor pe baza notelor filmelor si serialelor
     * @param movies filmele din baza de date existente cu rating-urile lor
     * @param serials serialele din baza de date existente cu rating-urile lor
     */
    public void updateRatings(final Movies movies, final Serials serials) {
        for (Actor actor : actorsList) {
            actor.setRating(movies, serials);
        }
    }

    /**
     * recalculeaza numarul total de premii pentru fiecare actor
     */
    public void setAllNumberOfAwards() {
        for (Actor actor : actorsList) {
            actor.setNumberAwards();
        }
    }

    /**
     *
     * @param n spune cati actori includem in query
     * @return mesajul de afisat in output
     */
    public String nameListMessage(final int n) {
        List<String> names = new ArrayList<>(); // lista cu numele actorilor
        for (int i = 0; i < Math.min(n, actorsList.size()); i++) {
            names.add(actorsList.get(i).getName());
        }
        return "Query result: " + names.toString();
    }
}
