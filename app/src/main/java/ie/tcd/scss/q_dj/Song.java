package ie.tcd.scss.q_dj;

/**
 * Created by Darragh on 08/02/2016.
 */
public class Song {

    private String title;
    private String artist;
    private double duration;
    private String spotifyID;
    String image;

    public Song(String title, String artist, double duration, String spotifyID){
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.spotifyID = spotifyID;
        this.image = image;
    }

    public void setTitle(String title){this.title=title;}
    public String getTitle(){return title;}
    public void setArtist(String artist){this.artist=artist;}
    public String getArtist(){return artist;}
    public void setDuration(long duration){this.duration=duration;}
    public double getDuration(){return duration;}
    public void setSpotifyID(String spotifyID){this.spotifyID=spotifyID;}
    public String getSpotifyID(){return spotifyID;}

    public void setImage(String image){this.image = image;}
    public String getImage(){return image;}
}