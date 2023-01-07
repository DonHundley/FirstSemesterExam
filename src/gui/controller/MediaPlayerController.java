package gui.controller;

import be.Movie;
import gui.model.Model;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MediaPlayerController implements Initializable {

    @FXML
    private ImageView volumeIcon, playIcon;
    @FXML
    private TextField filterTextFieldMP;
    @FXML
    private TableView<Movie> movieTVMP;
    @FXML
    private TableColumn<Movie, Integer> columnIDMP;
    @FXML
    private TableColumn<Movie, String> columnTitleMP;
    @FXML
    private Button playButton, previousButton, nextButton, backToMovieInfoButton, muteButton;


    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar mediaProgressBar;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private MediaView mediaViewWindow;


    private MediaPlayer mediaPlayer;
    private Media media;


    private boolean running;
    private Timer timer;
    private final int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private double current;
    private double end;


    private Model model = new Model();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        movieTVMP.setItems(model.getObsMovies());
        model.loadMovieList();

        columnIDMP.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnTitleMP.setCellValueFactory(new PropertyValueFactory<>("name"));


        movieTVMP.getSelectionModel().selectedItemProperty().addListener((observableValue, oldUser, selectedUser) -> {
            String name = movieTVMP.getSelectionModel().getSelectedItem().getFileLink();
            String s1 = name.substring(name.indexOf("moviesLocalFolder"));
            File movie = new File(s1);

            //System.out.println(movie);
            //System.out.println(movieTVMP.getSelectionModel().getSelectedItem().getFileLink());
            media = new Media(movie.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaViewWindow.setMediaPlayer(mediaPlayer);
            settingsAssurance();
        });

        tableFilterMP();
        playbackSpeed();
        volume();


    }



    private void tableFilterMP() {
        FilteredList<Movie> filteredDataMP = new FilteredList<>(model.getObsMovies(), b -> true);

        filterTextFieldMP.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataMP.setPredicate(Movie -> {

                // If there is no search value then it will continue to display as normal.
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();


                if (Movie.getName().toLowerCase().contains(searchKeyword)) {
                    return true; //This means we found a match.
                } else
                    return false;
            });
        });
        SortedList<Movie> sortedData = new SortedList<>(filteredDataMP);

        // Bind sorted result to Tableview.
        sortedData.comparatorProperty().bind(movieTVMP.comparatorProperty());

        // Apply filtered and sorted data to the Tableview.
        movieTVMP.setItems(sortedData);
    }

    /**
     * The purpose of this is so that we can assure our volume changes based on the change the user puts on the slider.
     */
    public void volume() {volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mediaPlayer.setVolume(volumeSlider.getValue() * 0.01));}

    /**
     * This is how we determine how much each number in our combobox affects our playback speed.
     */
    public void playbackSpeed()
    {for (int speed : speeds) {speedBox.getItems().add(speed + "%");}speedBox.setOnAction(this::changeSpeed);}

    /**
     * This method is what we use to let the user set the playback speed of our film.
     */
    public void changeSpeed (ActionEvent actionEvent){
        if(speedBox.getValue() == null) {mediaPlayer.setRate(1);}
        else {mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);}
    }

    /**
     * The timer assures our progress bar changes based on the percentage of the film that has played.
     * This also determines what happens when the film finishes.
     */
    public void beginTimer()
    {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {

                current = mediaPlayer.getCurrentTime().toSeconds();
                end = media.getDuration().toSeconds();
                mediaProgressBar.setProgress(current/end);

                if (current / end == 1) {
                    mediaPlayer.seek(Duration.seconds(0));
                    mediaPlayer.stop();
                    timer.cancel();
                    running = false;
                    playIcon.setImage(new Image("images/play_icon.png"));
                    settingsAssurance();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * Our play button has two functions. If the player is running it will stop it, let us know it is no longer running, and stop our timer.
     * If it is not running, it begins our timer, tells us that it is running, and begins our film.
     */
    @FXML
    private void playMedia (ActionEvent actionEvent){
        if (!running){
            beginTimer();
            running = true;
            mediaPlayer.play();
            playIcon.setImage(new Image("images/pause_icon.png"));
        } else
        {
            timer.cancel();
            running = false;
            mediaPlayer.pause();
            playIcon.setImage(new Image("images/play_icon.png"));
        }
    }

    @FXML
    private void nextMedia (ActionEvent actionEvent){mediaPlayer.seek(Duration.seconds(+30));}

    @FXML
    private void previousMedia (ActionEvent actionEvent){mediaPlayer.seek(Duration.seconds(-30));}



    /**
     * This method assures that our volume and playback speed match user settings at all times.
     */
    private void settingsAssurance() {
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        changeSpeed(null);}
    @FXML
    private void muteMP(ActionEvent actionEvent) {
        if(mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
            volumeIcon.setImage(new Image("images/volume_icon.png"));

        }
        else
        {
            mediaPlayer.setMute(true);
            volumeIcon.setImage(new Image("images/volume_mute.png"));
        }
    }
}