package abdulrahmanjavanrd.com.musicbook_demo2.intents;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.apaters.RawAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.model.RawMusic;
import abdulrahmanjavanrd.com.musicbook_demo2.utilities.ConvertTime;

/**
 * @author abdulrahman abdullah .
 */

public class RawSongsActivity extends AppCompatActivity {
    List<RawMusic> listMusic;
    GridView gridView;
    MediaPlayer mPlayer;
    int lengthCurrentSong;
    int currentSongIndex = 0;
    TextView txvCurrentSong, txvTime;
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseSong();
        }
    };
    SeekBar seekBar;
    Button playBtn, rewindBtn, forwardBtn;
    Toolbar toolbar ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raw_song_activity_layout);
        /** put data inside {@link #listMusic} */
        setListMusic();
        /** init views  */
        toolbar = findViewById(R.id.seActivity_toolbar);
        txvCurrentSong = findViewById(R.id.txvCurrentSong);
        txvTime = findViewById(R.id.txvTime);
        gridView = findViewById(R.id.grid_view);
        rewindBtn = findViewById(R.id.btnRewind);
        playBtn = findViewById(R.id.btnPlay);
        forwardBtn = findViewById(R.id.btnForward);
        seekBar = findViewById(R.id.skBar);
        /**  toolbar */
         setSupportActionBar(toolbar);
         /** Back up button . */
         ActionBar actionBar = getSupportActionBar();
         actionBar.setDisplayHomeAsUpEnabled(true);
        /**
         * Create own thread .
         */
         songTrack myTrack = new songTrack();
        myTrack.start();
        gridView.setAdapter(new RawAdapter(this, listMusic));
        /**
         * GridView Listener .
         * When user click any song inside this list, it will  play automatically, Then Change Background button to
         *  pause icon.
         */
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** when click call {@link #playSong()}
                 *  get audio and the name of this audio
                 * */
                playSong(listMusic.get(position).getId(), listMusic.get(position).getSongName());
            }
        });

        /**
         * Play button
         * OnClick play button :
         *  1- pause song .
         *  2- save the current position of music and put the value inside{@link #lengthCurrentSong}
         *                  to resume the current music.
         *  3- change the background button to play button .
         *  4- if click again , resume the current music  position.
         */
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer == null) {
                    playSong(listMusic.get(currentSongIndex).getId(), listMusic.get(currentSongIndex).getSongName());
                } else if (mPlayer.isPlaying() && mPlayer != null) {
                    mPlayer.pause();
                    lengthCurrentSong = mPlayer.getCurrentPosition();
                    playBtn.setBackgroundResource(R.drawable.ic_play_white);
                }
//                //TODO: solve this debug , when song end .
                else if (!(mPlayer.isPlaying())) {
                    mPlayer.seekTo(lengthCurrentSong);
                    mPlayer.start();
                    seekBar.setMax(mPlayer.getDuration());
                    playBtn.setBackgroundResource(R.drawable.ic_pause_white);
                }
            }
        });
/**
 *  Previous song .
 */
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex > 0) {
                    playSong(listMusic.get(currentSongIndex - 1).getId(), listMusic.get(currentSongIndex - 1).getSongName());
                    currentSongIndex = currentSongIndex - 1;
                } else {
                    playSong(listMusic.get(currentSongIndex).getId(), listMusic.get(currentSongIndex).getSongName());
                    currentSongIndex = 0;
                }
            }
        });
        /**
         *  Next Song .
         */
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex < (listMusic.size() - 1)) {
                    playSong(listMusic.get(currentSongIndex + 1).getId(), listMusic.get(currentSongIndex + 1).getSongName());
                    currentSongIndex = currentSongIndex + 1;
                } else {
                    playSong(listMusic.get(0).getId(), listMusic.get(0).getSongName());
                    currentSongIndex = 0;
                }
            }
        });
    }

    /**
     * @param position of song id .
     * @param songName to set songName when play song OR move to next song OR back to the Previous song.
     */
    private void playSong(int position, String songName) {
        releaseSong();
        txvCurrentSong.setText(songName);
        mPlayer = MediaPlayer.create(RawSongsActivity.this, position);
        mPlayer.start();
        seekBar.setMax(mPlayer.getDuration());
        playBtn.setBackgroundResource(R.drawable.ic_pause_white);
        mPlayer.setOnCompletionListener(completionListener);
    }

    /**
     * add songs method from  RawMusic.class then save it inside {@link #listMusic}
     * This list i passed in RawAdapter.class .
     */
    private void setListMusic() {
        listMusic = new ArrayList<>();
        listMusic.add(new RawMusic(R.raw.ibrahim_turkish, "Ibrahim Turkish", "turkish"));
        listMusic.add(new RawMusic(R.raw.la_la_land, "La La Land", "Movie Music"));
        listMusic.add(new RawMusic(R.raw.motivational, "Best Motivation", "Motivations"));
        listMusic.add(new RawMusic(R.raw.need_you, "Need you", "Team"));
        listMusic.add(new RawMusic(R.raw.strangers_in_the_night, "Strangers in the Night", "Frank"));
        listMusic.add(new RawMusic(R.raw.sway, "Sway", "sway"));
    }

    /**
     * If {@link #mPlayer} != null then release audio .
     * Call this method to play another audio softly.
     * And
     * If audio finish set the {@link #mPlayer} = null .
     */
    public void releaseSong() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * Release Song when user leave app .
     */
    @Override
    protected void onPause() {
        super.onPause();
        releaseSong();
    }

    /**
     * Release song when user Destroy app .
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseSong();
    }

    /**
     * Inner class for update seekBar and timer .
     * This class extends Thread Because i want run long process and avoid the ANR .
     * In This class I call ConvertTime class to show the current song time .
     */
    class songTrack extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPlayer != null) {
                            seekBar.setProgress(mPlayer.getCurrentPosition());
                            long millisecond = mPlayer.getCurrentPosition();
                            /**
                             * convert currentPosition by using this class {@link ConvertTime}
                             */
                            String str = ConvertTime.millisecond(millisecond);
                            txvTime.setText(str);
                        } else {
                            txvTime.setText("00:00");
                            seekBar.setProgress(0);
                        }
                    }
                });
            }
        }
    }
}
