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
    Button playBtn , forwardBtn , rewindBtn,backBtn ;
    /** Global var*/
    List<Music> musicList;
    private int indexSong = 0;// Update this value when click any items in ListView
    private int currentLengthSong ; // This var for save MediaPlayer getCurrentPosition TO pause music and resume.
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
        backBtn =findViewById(R.id.btnBack);
        seekBar = findViewById(R.id.skBar);
        /** ToolBar  */
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        createAdapter();
        /** Start Thread */
        final SongTrack myTrack = new SongTrack();
        myTrack.start();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexSong = position;
                playSong(getCurrentMusic(position).getId(),getCurrentMusic(position).getSongName());
            }
        });
       /** Button for play music, And pause music*/
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer == null){
                    playSong(getCurrentMusic(indexSong).getId(),getCurrentMusic(indexSong).getSongName());
                }
               else if( mPlayer != null && mPlayer.isPlaying()){
                   currentLengthSong = mPlayer.getCurrentPosition();
                   mPlayer.pause();
                   seekBar.setMax(mPlayer.getDuration());
                   playBtn.setBackgroundResource(R.drawable.ic_play_white);
                }
                else if (!(mPlayer.isPlaying())){
                  mPlayer.seekTo(currentLengthSong);
                  mPlayer.start();
                 playBtn.setBackgroundResource(R.drawable.ic_pause_white);
                }
            }
        });

        /** Button for rewind music*/
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong > 0){
                    indexSong-- ;
                    playSong(getCurrentMusic(indexSong).getId(),getCurrentMusic(indexSong).getSongName());
                }else{
                    playSong(getCurrentMusic(indexSong).getId(),getCurrentMusic(indexSong).getSongName());
                    indexSong = 0 ;
                }
            }
        });
        /** Button forward music */
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong < getMusicList().size()-1){
                    indexSong++;
                    playSong(getCurrentMusic(indexSong).getId(),getCurrentMusic(indexSong).getSongName());
                }
                else{
                    playSong(getCurrentMusic(indexSong).getId(),getCurrentMusic(indexSong).getSongName());
                    indexSong = 0 ;
                }
            }
        });
        /** Back to MainActivity*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(WorkoutMusicActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
    }

    /**
     * This method  call {@link #getMusicList()} And
     *   Create new object of {@link WorkoutAdapter} class Then pass
     *    {@link #getMusicList()}.
     */
    private void createAdapter(){
        WorkoutAdapter adapter = new WorkoutAdapter(this,getMusicList());
        listView.setAdapter(adapter);
    }

    /**
     * @return  {@link #musicList} After insert data .
     */
    private List<Music> getMusicList(){
        musicList = new ArrayList<>();
        musicList.add(new Music(R.raw.cant_stop,"Can't stop","TheStarFactory"));
        musicList.add(new Music(R.raw.bootie_hips,"Boot Hips","TheStarFactory"));
        musicList.add(new Music(R.raw.dont_let_me_down,"Don't Let ME Down","TheStarFactory"));
        musicList.add(new Music(R.raw.i_dont_like_it,"I Don't Like it","TheStarFactory"));
        musicList.add(new Music(R.raw.lean_on,"Lean on","TheStarFactory"));
        musicList.add(new Music(R.raw.let_me_love_you,"Let Me love you","TheStarFactory"));
        musicList.add(new Music(R.raw.light_me_up,"Light me up","TheStarFactory"));
        musicList.add(new Music(R.raw.no_excuses,"No Excuses","TheStarFactory"));
        musicList.add(new Music(R.raw.music_2_workout,"Music 1 ","TheStarFactory"));
        musicList.add(new Music(R.raw.music_3_workout,"Music 2","TheStarFactory"));
        musicList.add(new Music(R.raw.music_4_workout,"Music 3","TheStarFactory"));
        return musicList;
    }

    /**
     * @param p position of obj.
     * @return obj of {@link Music} class
     */
    private Music getCurrentMusic(int p){
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
     * In This class I call {@link ConvertTime}class to convert MediaPlayer getCurrentPosition .
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
