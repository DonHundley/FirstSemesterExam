package be;

public class Movie {
    Integer movieID;
    String title;
    String category;
    Integer ratingIMDB;
    Integer myRating;
    Integer lastView;

    public Movie(Integer movieID, String title, String category, Integer ratingIMDB, Integer myRating, Integer lastView) {
        this.movieID = movieID;
        this.title = title;
        this.category = category;
        this.ratingIMDB = ratingIMDB;
        this.myRating = myRating;
        this.lastView = lastView;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public Integer getRatingIMDB() {
        return ratingIMDB;
    }

    public Integer getMyRating() {
        return myRating;
    }

    public Integer getLastView() {
        return lastView;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRatingIMDB(Integer ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public void setMyRating(Integer myRating) {
        this.myRating = myRating;
    }

    public void setLastView(Integer lastView) {
        this.lastView = lastView;
    }
}
