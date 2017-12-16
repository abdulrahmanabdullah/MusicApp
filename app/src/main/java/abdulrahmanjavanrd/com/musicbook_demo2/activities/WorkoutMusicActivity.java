package abdulrahmanjavanrd.com.musicbook_demo2.activities;

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

import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.adapters.WorkoutAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;
import abdulrahmanjavanrd.com.musicbook_demo2.utilities.ConvertTime;

/**
 * @author Abdulrahman.A
 * @since  on 16/12/2017.
 */

public class WorkoutMusicActivity extends AppCompatActivity {

    /**  Views elements */
    Toolbar toolbar ;
    ListView listView ;
    TextView currentSongName,songTime ;
    SeekBar seekBar ;
    Button playBtn , forwardBtn , rewindBtn ;
    /** Global var*/
    List<Music> musicList = new ArrayList<>();
    private int indexSong = 0;// Update this value when click any items in ListView
    private MediaPlayer mPlayer ;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseSong();
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        /** initializer views  */
        toolbar = findViewById(R.id.workoutActivity_toolbar);
        listView = findViewById(R.id.list_view);
        currentSongName = findViewById(R.id.txvCurrentSong);
        songTime = findViewById(R.id.txvTime);
        playBtn = findViewById(R.id.btnPlay);
        forwardBtn = findViewById(R.id.btnForward);
        rewindBtn = findViewById(R.id.btnRewind);
        seekBar = findViewById(R.id.skBar);
        /** ToolBar  */
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        createAdapter();
        /** Start Thread */
        SongTrack myTrack = new SongTrack();
        myTrack.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexSong = position;
                playSong(getMusicList(position).getId(),getMusicList(position).getSongName());
            }
        });
       /** Button for play music*/
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /** Button for rewind music*/
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /** Button forward music */
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * This method for call {@link #setMusicList()} And
     *   Create new object of {@link WorkoutAdapter} class Then pass
     *    {@link #musicList}.
     */
    private void createAdapter(){
        setMusicList();
        WorkoutAdapter adapter = new WorkoutAdapter(this,musicList);
        listView.setAdapter(adapter);
    }

    /**
     * Add music to the {@link #musicList}
     */
    private void setMusicList(){
        Music m1 = new Music(R.raw.cant_stop,"Can't stop","TheStarFactory");
        Music m2 = new Music(R.raw.bootie_hips,"Boot Hips","TheStarFactory");
        Music m3 = new Music(R.raw.dont_let_me_down,"Don't Let ME Down","TheStarFactory");
        Music m4 = new Music(R.raw.i_dont_like_it,"I Don't Like it","TheStarFactory");
        Music m5 = new Music(R.raw.lean_on,"Lean on","TheStarFactory");
        Music m6 = new Music(R.raw.let_me_love_you,"Let Me love you","TheStarFactory");
        Music m7 = new Music(R.raw.light_me_up,"Light me up","TheStarFactory");
        Music m11 = new Music(R.raw.no_excuses,"No Excuses","TheStarFactory");
        Music m8 = new Music(R.raw.music_2_workout,"Music 1 ","TheStarFactory");
        Music m9 = new Music(R.raw.music_3_workout,"Music 2","TheStarFactory");
        Music m10 = new Music(R.raw.music_4_workout,"Music 3","TheStarFactory");
        musicList.add(m1);
        musicList.add(m2);
        musicList.add(m3);
        musicList.add(m4);
        musicList.add(m5);
        musicList.add(m6);
        musicList.add(m7);
        musicList.add(m8);
        musicList.add(m9);
        musicList.add(m10);
        musicList.add(m11);
    }

    /**
     * @param p position of obj.
     * @return obj of {@link Music} class
     */
    private Music getMusicList(int p){
        return musicList.get(p);
    }

    /**
     * Release Music and set {@link #mPlayer} = null
     */
    private void releaseSong(){
       if (mPlayer !=null){
           mPlayer.release();
           mPlayer = null ;
       }
    }

    /**
     * @param position to set Media id .
     * @param songName to set current song name in {@link #currentSongName}
     */
    private void playSong(int position, String songName) {
        releaseSong();
        currentSongName.setText(songName);
        mPlayer = MediaPlayer.create(WorkoutMusicActivity.this, position);
        mPlayer.start();
        seekBar.setMax(mPlayer.getDuration());
        playBtn.setBackgroundResource(R.drawable.ic_pause_white);
        mPlayer.setOnCompletionListener(completionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseSong();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseSong();
    }

    /**
     * Inner Class
     */
    /**
     * Inner class for update seekBar and timer .
     * This class extends Thread Because i want run long process and avoid the ANR .
     * In This class I call {@link ConvertTime}class to show the current song time .
     */
    class SongTrack extends Thread {
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
                            songTime.setText(str);
                        } else {
                            songTime.setText("00:00");
                            seekBar.setProgress(0);
                        }
                    }
                });
            }
        }
    }
}
