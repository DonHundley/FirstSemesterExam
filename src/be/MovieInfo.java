package be;

import javafx.scene.image.Image;

public class MovieInfo {
    private String title;
    private Image poster;
    private String rating;
    private String description;
    private String releaseDate;
    private Image backdropImage;

    public MovieInfo(String title, Image poster, String rating, String description, String releaseDate, Image backdropImage) {
        this.title = title;
        this.poster = poster;
        this.rating = rating;
        this.description = description;
        this.releaseDate = releaseDate;
        this.backdropImage = backdropImage;
    }

    public String getTitle() {
        return title;
    }

    public Image getPoster() {
        return poster;
    }

    public String getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Image getBackdropImage() {
        return backdropImage;
    }
}
