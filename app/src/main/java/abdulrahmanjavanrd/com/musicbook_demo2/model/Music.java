package abdulrahmanjavanrd.com.musicbook_demo2.model;

/**
 * Created by abdulrahman on 12/15/17.
 */

public class Music {

    private int id ;
    private String songName ;
    private String songArtiest ;
    private String songPath;
    /** Constructor for raw Music*/
    public Music(int id, String songName, String songArtiest) {
        this.id = id;
        this.songName = songName;
        this.songArtiest = songArtiest;
    }
    /** Constructor for Internal storage Music*/
    public Music(int id, String songName, String songArtiest, String songPath) {
        this.id = id;
        this.songName = songName;
        this.songArtiest = songArtiest;
        this.songPath = songPath;
    }

    /** Getter */
    public int getId() {
        return id;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongArtiest() {
        return songArtiest;
    }

    public String getSongPath() {
        return songPath;
    }
}
