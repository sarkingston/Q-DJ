package ie.tcd.scss.q_dj;

/**
 * Created by Darragh on 08/02/2016.
 */
public class Song {

    public String title;
    public String artist;
    public double duration;
    public String spotifyID;
    public String coverPhoto;

    public Song(String title, String artist, double duration, String spotifyID){
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.spotifyID = spotifyID;
    }
}
