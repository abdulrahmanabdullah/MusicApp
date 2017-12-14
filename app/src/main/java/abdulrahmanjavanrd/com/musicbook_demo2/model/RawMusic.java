package abdulrahmanjavanrd.com.musicbook_demo2.model;

/**
 * Created by nfs05 on 12/12/2017.
 */

public class RawMusic
{

    private int id;
    private String songName ;
    private String artistName ;

    public int getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public RawMusic(int id, String songName, String artistName) {
        this.id = id;
        this.songName = songName;
        this.artistName = artistName;
    }
}
