package dataset;

import shows.Serial;

import java.util.ArrayList;
import java.util.List;

public final class Serials {
    private List<Serial> serialList = new ArrayList<>();

    public Serials(List<Serial> serialList) {
        this.serialList = new ArrayList<>(serialList);
    }

    public Serials() {
    }

    public List<Serial> getSerialList() {
        return serialList;
    }

    public void setSerialList(List<Serial> serialList) {
        this.serialList = serialList;
    }

    public Serial findSerial(String title) {
        for (Serial serial : serialList) {
            if (serial.getTitle().equals(title)) {
                return serial;
            }
        }
        return null;
    }

    public String serialListMessage(int N) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < Math.min(N, serialList.size()); i++) {
            names.add(serialList.get(i).getTitle());
        }
        return "Query result: " + names.toString();
    }

    public void updateFavoriteScore(Users users) {
        for (Serial serial : serialList) {
            serial.setFavoriteScore(users);
        }
    }
    public void updateViewsScore(Users users) {
        for(Serial serial : serialList) {
            serial.setViewsScore(users);
        }
    }
}
