package dataset;

import shows.Video;
import shows.ascRatingSort;
import user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public String bestUnseenRecommendation(User user) {
        Videos videos = new Videos(this.videosList);
        videos.getVideosList().removeIf((v) -> user.sawVideo(v.getTitle()));
        if(videos.getVideosList().isEmpty()) return "BestRatedUnseenRecommendation cannot be applied!";
        Video bestUnseenVideo = videos.getVideosList().get(0);
        for(Video video : videos.getVideosList()) {
            if(video.getRating() > bestUnseenVideo.getRating()) {
                bestUnseenVideo = video;
            }
        }
        return "BestRatedUnseenRecommendation result: " + bestUnseenVideo.getTitle();
    }
}
