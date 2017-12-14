package abdulrahmanjavanrd.com.musicbook_demo2.model;

/**
 * Created by nfs05 on 12/12/2017.
 */

public class InternalMusic {
    private int id ;
    private String songName;
    private String songArtiest;
    private String songPath;
    public InternalMusic(int id, String nameSong, String songArtiest, String dataSong) {
        this.id = id;
        this.songName = nameSong;
        this.songArtiest = songArtiest;
        this.songPath = dataSong;
    }

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
