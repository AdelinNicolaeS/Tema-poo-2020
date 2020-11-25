package dataset;

import shows.Video;
import user.User;

import java.util.ArrayList;

public class Videos {
    private ArrayList<Video> videosList = new ArrayList<>();

    public Videos() {
    }

    public Videos(ArrayList<Video> videosList) {
        this.videosList = new ArrayList<>(videosList);
    }

    public ArrayList<Video> getVideosList() {
        return videosList;
    }

    public void setVideosList(ArrayList<Video> videosList) {
        this.videosList = videosList;
    }

    public void rebuildList(Movies movies, Serials serials) {
        videosList.clear();
        videosList.addAll(movies.getMovieList());
        videosList.addAll(serials.getSerialList());
    }

    public String standardRecommendation(User user) {
        String message = "StandardRecommendation ";
        for(Video video : videosList) {
            if(!user.getHistory().containsKey(video.getTitle())) {
                return (message + "result: " + video.getTitle());
            }
        }
        return message + "cannot be applied!";
    }
}
