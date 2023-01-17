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
                Result r = p.getResults().get(0);
                MovieInfo mi = new MovieInfo(
                        r.getTitle(),
                        new Image(imagePath+r.getPoster_path()),
                        r.getVote_average().toString(),
                        r.getOverview(),
                        r.getRelease_date(),
                        new Image(imagePath+r.getBackdrop_path()));

                conn.disconnect();
                return mi;
            }
        }
        catch (IOException | IndexOutOfBoundsException e) {e.printStackTrace();}
        return null;
    }
}
