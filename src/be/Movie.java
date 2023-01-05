package be;

public class Movie {
    int movieID;
    String title;
    String category;
    double ratingIMDB;
    double myRating;
    int lastView;

    public Movie(Integer movieID, String title, String category, double ratingIMDB, double myRating, int lastView) {
        this.movieID = movieID;
        this.title = title;
        this.category = category;
        this.ratingIMDB = ratingIMDB;
        this.myRating = myRating;
        this.lastView = lastView;
    }

    public Movie() {
    }

    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getRatingIMDB() {
        return ratingIMDB;
    }

    public double getMyRating() {
        return myRating;
    }

    public int getLastView() {
        return lastView;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRatingIMDB(double ratingIMDB) {
        this.ratingIMDB = ratingIMDB;
    }

    public void setMyRating(double myRating) {
        this.myRating = myRating;
    }

    public void setLastView(int lastView) {
        this.lastView = lastView;
    }
}
