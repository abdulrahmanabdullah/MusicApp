package abdulrahmanjavanrd.com.musicbook_demo2.model;

/**
 * Created by nfs05 on 12/12/2017.
 */

public class InternalMusic {

    private int id ;
    private String nameSong ;
    private String artistSong;
    private String dataSong ;


    public InternalMusic(int id, String nameSong, String artistSong, String dataSong) {
        this.id = id;
        this.nameSong = nameSong;
        this.artistSong = artistSong;
        this.dataSong = dataSong;
    }


    public int getId() {
        return id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public String getArtistSong() {
        return artistSong;
    }

    public String getDataSong() {
        return dataSong;
    }
}
