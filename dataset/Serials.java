package dataset;

import shows.Serial;

import java.util.ArrayList;
import java.util.List;

public final class Serials {
    private List<Serial> serialList = new ArrayList<>();

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
}
