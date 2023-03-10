package be;

import java.util.List;


/**
 * This class is nearly a direct copy of the example given from Jeppe, as it is almost entirely variables with their getters and setters I did not write this and took it from the example.
 */
public class Result {
    private Integer vote_count;
    private Integer id;
    private Boolean video;
    private Double vote_average;
    private String title;
    private Double popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids = null;
    private String backdrop_path;
    private Boolean adult;
    private String overview;
    private String release_date;


    public Integer getVote_count() {return vote_count;}

    public void setVote_count(Integer vote_count) {this.vote_count = vote_count;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Boolean getVideo() {return video;}

    public void setVideo(Boolean video) {this.video = video;}

    public Double getVote_average() {return vote_average;}

    public void setVote_average(Double vote_average) {this.vote_average = vote_average;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public Double getPopularity() {return popularity;}

    public void setPopularity(Double popularity) {this.popularity = popularity;}

    public String getPoster_path() {return poster_path;}

    public void setPoster_path(String poster_path) {this.poster_path = poster_path;}

    public String getOriginal_language() {return original_language;}

    public void setOriginal_language(String original_language) {this.original_language = original_language;}

    public String getOriginal_title() {return original_title;}

    public void setOriginal_title(String original_title) {this.original_title = original_title;}

    public List<Integer> getGenre_ids() {return genre_ids;}

    public void setGenre_ids(List<Integer> genre_ids) {this.genre_ids = genre_ids;}

    public String getBackdrop_path() {return backdrop_path;}

    public void setBackdrop_path(String backdrop_path) {this.backdrop_path = backdrop_path;}

    public Boolean getAdult() {return adult;}

    public void setAdult(Boolean adult) {this.adult = adult;}

    public String getOverview() {return overview;}

    public void setOverview(String overview) {this.overview = overview;}

    public String getRelease_date() {return release_date;}

    public void setRelease_date(String release_date) {this.release_date = release_date;}



    @Override
    public String toString() {
        return "Result{" + "vote_count=" + vote_count + ", id=" + id + ", video=" + video + ", vote_average=" + vote_average + ", title=" + title + ", popularity=" + popularity + ", poster_path=" + poster_path + ", original_language=" + original_language + ", original_title=" + original_title + ", genre_ids=" + genre_ids + ", backdrop_path=" + backdrop_path + ", adult=" + adult + ", overview=" + overview + ", release_date=" + release_date + '}';
    }

}

