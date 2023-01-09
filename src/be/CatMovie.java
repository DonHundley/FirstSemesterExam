package be;

public class CatMovie {

    private int id;
    private int categoryID;
    private int movieID;

    public CatMovie(int categoryID, int movieID) {
        this.categoryID = categoryID;
        this.movieID = movieID;
    }

    public CatMovie(int id, int categoryID, int movieID) {
        this(categoryID, movieID);
        this.id = id;
    }
}