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
     *
     * @param title
     * @return
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
     *
     * @param n
     * @return
     */
    public String serialListMessage(final int n) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(n, serialList.size()); i++) {
            names.add(serialList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    /**
     *
     * @param users
     */
    public void updateFavoriteScore(final Users users) {
        for (Serial serial : serialList) {
            serial.setFavoriteScore(users);
        }
    }

    /**
     *
     * @param users
     */
    public void updateViewsScore(final Users users) {
        for (Serial serial : serialList) {
            serial.setViewsScore(users);
        }
    }
}
