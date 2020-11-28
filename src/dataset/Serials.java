package dataset;

import shows.Serial;

import java.util.ArrayList;
import java.util.List;

public final class Serials {
    private List<Serial> serialList = new ArrayList<>();

    public Serials(final List<Serial> serialList) {
        this.serialList = new ArrayList<>(serialList);
    }

    public Serials() {
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    /**
     * verificam daca un serial e in baza de date sau nu
     * @param title titlul serialului
     * @return serialul sau null, daca nu a fost gasit
     */
    public Serial findSerial(final String title) {
        for (Serial serial : serialList) {
            if (serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }

    /**
     * obtine mesajul de afisat la tastatura
     * @param n primele cate seriale se afiseaza
     * @return mesajul de afisat
     */
    public String serialListMessage(final int n) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(n, serialList.size()); i++) {
            names.add(serialList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    /**
     * recalculeaza numarul de aparitii in lista de favorite
     * a userilor pentru fiecare serial
     * @param users baza de date de utilizatori
     */
    public void updateFavoriteScore(final Users users) {
        for (Serial serial : serialList) {
            serial.setFavoriteScore(users);
        }
    }

    /**
     * recalculeaza numarul de vizualizari pentru un serial
     * @param users baza de date de utilizatori
     */
    public void updateViewsScore(final Users users) {
        for (Serial serial : serialList) {
            serial.setViewsScore(users);
        }
    }
}
