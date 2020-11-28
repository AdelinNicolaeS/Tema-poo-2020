package dataset;

import shows.Video;
import shows.RatingSort;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Videos {
    private ArrayList<Video> videosList = new ArrayList<>();
    private Map<String, Integer> popularGenres = new HashMap<>();

    public Map<String, Integer> getPopularGenres() {
        return popularGenres;
    }

    public Videos() {
    }

    public Videos(final ArrayList<Video> videosList, final Map<String, Integer> popularGenres) {
        this.videosList = new ArrayList<>(videosList);
        this.popularGenres = new HashMap<>(popularGenres);
    }

    public Videos(final ArrayList<Video> videosList) {
        this.videosList = new ArrayList<>(videosList);
    }

    public ArrayList<Video> getVideosList() {
        return videosList;
    }

    public void setVideosList(final ArrayList<Video> videosList) {
        this.videosList = videosList;
    }

    /**
     *
     */
    public void setPopularGenres() {
        popularGenres.clear();
        for (Video video : videosList) {
            for (String genre : video.getGenres()) {
                if (!popularGenres.containsKey(genre)) {
                    popularGenres.put(genre, video.getViews());
                } else {
                    popularGenres.put(genre, popularGenres.get(genre) + video.getViews());
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public String mostPopularGenre() {
        int mx = -1;
        String genre = null;
        for (Map.Entry<String, Integer> entry : popularGenres.entrySet()) {
            if (entry.getValue() > mx) {
                mx = entry.getValue();
                genre = entry.getKey();
            }
        }
        return genre;
    }

    /**
     *
     * @param movies
     * @param serials
     */
    public void rebuildList(final Movies movies, final Serials serials) {
        videosList.clear();
        videosList.addAll(movies.getMovieList());
        videosList.addAll(serials.getSerialList());
    }

    /**
     *
     * @param user
     * @return
     */
    public String standardRecommendation(final User user) {
        String message = "StandardRecommendation ";
        for (Video video : videosList) {
            if (!user.getHistory().containsKey(video.getTitle())) {
                return (message + "result: " + video.getTitle());
            }
        }
        return message + "cannot be applied!";
    }

    /**
     *
     * @param user
     * @return
     */
    public String bestUnseenRecommendation(final User user) {
        Videos videos = new Videos(this.videosList);
        videos.getVideosList().removeIf((v) -> user.sawVideo(v.getTitle()));
        if (videos.getVideosList().isEmpty()) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        }
        Video bestUnseenVideo = videos.getVideosList().get(0);
        for (Video video : videos.getVideosList()) {
            if (video.getRating() > bestUnseenVideo.getRating()) {
                bestUnseenVideo = video;
            }
        }
        return "BestRatedUnseenRecommendation result: " + bestUnseenVideo.getTitle();
    }

    /**
     *
     * @param user
     * @return
     */
    public String popularRecommendation(final User user) {
        if (user.getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }
        String popularTitle = null;
        Videos videos = new Videos(this.videosList, this.popularGenres);
        videos.getVideosList().removeIf((v) -> user.sawVideo((v.getTitle())));
        boolean findvideo = false;
        while (!findvideo && !videos.getPopularGenres().isEmpty()) {
            String popularGenre = videos.mostPopularGenre();
            for (Video video : videos.getVideosList()) {
                if (video.getGenres().contains(popularGenre)) {
                    findvideo = true;
                    popularTitle = video.getTitle();
                    break;
                }
            }
            videos.popularGenres.remove(popularGenre);
        }
        if (findvideo) {
            return ("PopularRecommendation result: " + popularTitle);
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     *
     * @param user
     * @return
     */
    public String favoriteRecommandation(final User user) {
        if (user.getSubscriptionType().equals("BASIC")) {
            return "FavoriteRecommendation cannot be applied!";
        }
        Videos videos = new Videos(this.videosList);
        videos.getVideosList().removeIf((v) -> user.sawVideo(v.getTitle()));
        int maxFavorite = 0;
        String favoriteTitle = null;
        for (Video video : videos.getVideosList()) {
            if (video.getFavorite() > maxFavorite) {
                maxFavorite = video.getFavorite();
                favoriteTitle = video.getTitle();
            }
        }
        if (maxFavorite == 0) {
            return "FavoriteRecommendation cannot be applied!";
        } else {
            return "FavoriteRecommendation result: " + favoriteTitle;
        }
    }

    /**
     *
     * @param user
     * @param genre
     * @return
     */
    public String searchRecommandation(final User user, final String genre) {
        if (user.getSubscriptionType().equals("BASIC")) {
            return "SearchRecommendation cannot be applied!";
        }
        Videos videos = new Videos(this.videosList);
        videos.getVideosList().removeIf((v) -> user.sawVideo(v.getTitle()));
        videos.getVideosList().removeIf((v) -> !v.getGenres().contains(genre));
        if (videos.getVideosList().isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        videos.getVideosList().sort(new RatingSort());
        List<String> nameList = new ArrayList<>();
        for (Video video : videos.getVideosList()) {
            nameList.add(video.getTitle());
        }
        return ("SearchRecommendation result: " + nameList);
    }
}
