package dataset;

import shows.Video;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Videos {
    private ArrayList<Video> videosList = new ArrayList<>();
    private Map<String, Integer> popularGenres = new HashMap<>();

    public Map<String, Integer> getPopularGenres() {
        return popularGenres;
    }

    public void setPopularGenres(Map<String, Integer> popularGenres) {
        this.popularGenres = popularGenres;
    }

    public Videos() {
    }

    public Videos(ArrayList<Video> videosList, Map<String, Integer> popularGenres) {
        this.videosList = new ArrayList<>(videosList);
        this.popularGenres =new HashMap<>(popularGenres);
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

    public void setPopularGenres(Users users) {
        popularGenres.clear();
        for(Video video : videosList) {
            for(String genre : video.getGenres()) {
                if(!popularGenres.containsKey(genre)) {
                    popularGenres.put(genre, video.getViews());
                } else {
                    popularGenres.put(genre, popularGenres.get(genre) + video.getViews());
                }
            }
        }
    }
    public String mostPopularGenre() {
        int mx = -1;
        String genre = null;
        for(Map.Entry<String, Integer> entry : popularGenres.entrySet()) {
            if(entry.getValue() > mx) {
                mx = entry.getValue();
                genre = entry.getKey();
            }
        }
        return genre;
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

    public String popularRecommendation(User user) {
        String popularTitle = null;
        Videos videos = new Videos(this.videosList, this.popularGenres);
        videos.getVideosList().removeIf((v) -> user.sawVideo((v.getTitle())));
        boolean findvideo = false;
        while(!findvideo && !videos.getPopularGenres().isEmpty()) {
            String popularGenre = videos.mostPopularGenre();
            for(Video video : videos.getVideosList()) {
                if(video.getGenres().contains(popularGenre)) {
                    findvideo = true;
                    popularTitle = video.getTitle();
                    break;
                }
            }
            videos.popularGenres.remove(popularGenre);
        }
        if(findvideo) return ("PopularRecommendation result: " + popularTitle);
        return "PopularRecommendation cannot be applied!";
    }
}
