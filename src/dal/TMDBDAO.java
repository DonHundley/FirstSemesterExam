package dal;

import be.Movie;
import be.MovieInfo;
import be.Result;
import be.TMDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TMDBDAO {

    private static final String imagePath="https://image.tmdb.org/t/p/original/";

    /**
     * This method is how we are fetching the data from TMDB to be displayed. Some of this code is reused from the example given by Jeppe.
     *
     * My additions:
     * This method takes our focused movie from the table view and immediately searches for it, replacing spaces in the title with +, otherwise the website would fail the search.
     * We fetch the movie poster, the movie title, the user voted average rating from TMDB, their description of the movie, the release date, and finally we use the backdrop image from them as well.
     * The images require a very specific path. This was outlined on TMDBs website. The original code from Jeppe had the beginnings of this, but it did not function.
     * There are a lot more things that could be implemented with the API and some functions could be automated with this information.
     * More information on using the API can be found here https://developers.themoviedb.org/3/getting-started/introduction.
     */

    public MovieInfo getResult(Movie selectedUser) {
        try {
            String uri =
                    "https://api.themoviedb.org/3/search/movie?api_key=b576033e42ffbda195c7a11b2a406a10&language=en-US&query="
                            + selectedUser.getName().replace(" ","+") +
                            "&page=1&include_adult=false";

            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code :"
                        + conn.getResponseCode());
            }
            try(Reader reader = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())))){
                Gson gson = new GsonBuilder().create();
                TMDB p = gson.fromJson(reader, TMDB.class);

                if (p.getTotal_results() > 1) {
                    Result r = p.getResults().get(0); // We are fetching the very first result in our list of search results that comes from our selected name and using that data to fill our result class. This does not always provide a perfect match.
                    MovieInfo mi = new MovieInfo(
                            r.getTitle(),
                            new Image(imagePath + r.getPoster_path()),
                            r.getVote_average().toString(),
                            r.getOverview(),
                            r.getRelease_date(),
                            new Image(imagePath + r.getBackdrop_path()));

                    conn.disconnect();
                    return mi;
                }
                else {
                    MovieInfo miError = new MovieInfo(
                            "Movie not found.",
                            new Image("images/cantfinderror.png"),
                            "",
                            "The movie or film you have selected does not match a title in TMDb and as such has no information to display.",
                            "",
                            new Image("images/errorbackdrop.png"));
                    conn.disconnect();
                    return miError;
                }
            }
        }
        catch (IOException | IndexOutOfBoundsException e) {e.printStackTrace();}
        return null;
    }
}
