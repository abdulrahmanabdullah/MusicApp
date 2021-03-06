package abdulrahmanjavanrd.com.musicbook_demo2.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.MainActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.adapters.RawAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;
import abdulrahmanjavanrd.com.musicbook_demo2.utilities.ConvertTime;

/**
 * @author abdulrahman abdullah .
 */

public class ClassicalMusicActivity extends AppCompatActivity {
    /**
     * Views elements
     */
    TextView txvCurrentSong, txvTime;
    SeekBar seekBar;
    Button playBtn, rewindBtn, forwardBtn,backBtn;
    Toolbar toolbar;
    /**
     * Global var
     */
    List<Music> listMusic;
    ListView listView;
    MediaPlayer mPlayer;
    int lengthCurrentSong;// This var for save MediaPlayer getCurrentPosition TO pause music and resume.
    int currentSongIndex = 0;
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseSong();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classical_music);
        /** init views  */
        toolbar = findViewById(R.id.seActivity_toolbar);
        txvCurrentSong = findViewById(R.id.txvCurrentSong);
        txvTime = findViewById(R.id.txvTime);
        listView = findViewById(R.id.list_view);
        rewindBtn = findViewById(R.id.btnRewind);
        playBtn = findViewById(R.id.btnPlay);
        forwardBtn = findViewById(R.id.btnForward);
        backBtn = findViewById(R.id.btnBack);
        seekBar = findViewById(R.id.skBar);
        /**  toolbar */
        setSupportActionBar(toolbar);
        /** Back up button . */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        /** Call Thread */
        songTrack myTrack = new songTrack();
        myTrack.start();
        listView.setAdapter(new RawAdapter(this, getListMusic()));
        /**
         * ListView Listener .
         * When user click any song inside this list, it will  play automatically, Then Change Background button to
         *  pause icon.
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** when click call
                 *  {@link #playSong()}
                 *  get audio and the name of this audio
                 * */
                currentSongIndex = position;
                playSong(listMusic.get(position).getId(), listMusic.get(position).getSongName());
            }
        });

        /**
         * Play song-> BY default run first Music -> zero position in the list
         * Then when click again :
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
                } else if (!(mPlayer.isPlaying())) {
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
                    currentSongIndex--;
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
                    currentSongIndex++;
                } else {
                    playSong(listMusic.get(0).getId(), listMusic.get(0).getSongName());
                    currentSongIndex = 0;
                }
            }
        });
        /** Back to MainActivity*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  mIntent = new Intent(ClassicalMusicActivity.this, MainActivity.class);
                startActivity(mIntent);
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
        mPlayer = MediaPlayer.create(ClassicalMusicActivity.this, position);
        mPlayer.start();
        seekBar.setMax(mPlayer.getDuration());
        playBtn.setBackgroundResource(R.drawable.ic_pause_white);
        mPlayer.setOnCompletionListener(completionListener);
    }

    /**
     * Add Data inside {@link #listMusic}
     * This list i passed in {@link RawAdapter}.
     * @return {@link Music} object .
     */
    private List<Music> getListMusic() {
        listMusic = new ArrayList<>();
        listMusic.add(new Music(R.raw.ibrahim_turkish, "Ibrahim Turkish", "turkish"));
        listMusic.add(new Music(R.raw.la_la_land, "La La Land", "Movie Music"));
        listMusic.add(new Music(R.raw.motivational, "Best Motivation", "Motivations"));
        listMusic.add(new Music(R.raw.need_you, "Need you", "Team"));
        listMusic.add(new Music(R.raw.strangers_in_the_night, "Strangers in the Night", "Frank"));
        listMusic.add(new Music(R.raw.sway, "Sway", "sway"));
        return listMusic;
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
     * In This class I call {@link ConvertTime}class to show the current song time .
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
