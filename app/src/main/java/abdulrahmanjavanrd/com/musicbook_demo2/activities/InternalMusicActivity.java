package abdulrahmanjavanrd.com.musicbook_demo2.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.MainActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.adapters.InternalMusicAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;
import abdulrahmanjavanrd.com.musicbook_demo2.utilities.ConvertTime;

/**
 * Created by
 *
 * @author abdulrahma abdullah
 * @since on 14/12/2017.
 */

public class InternalMusicActivity extends AppCompatActivity {
    /**
     * Global Var
     */
    private final int REQUEST_PERMISSION = 123;
    private int indexSong = 0;
    private int lengthCurrentSong;
    List<Music> musicList = new ArrayList<>();
    /**
     * Views elements
     */
    Toolbar toolbar;
    MediaPlayer mPlayer;
    Button playBtn, forwardBtn, rewindBtn,backBtn;
    TextView currentSongName, songTimer;
    SeekBar seekBar;
    ListView listView;
    /**
     * On Complete Listener.
     */
    MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseSong();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_music);
        /**  initialize  view */
        toolbar = findViewById(R.id.thirdActivity_toolbar);
        listView = findViewById(R.id.list_view);
        currentSongName = findViewById(R.id.txvCurrentSong);
        songTimer = findViewById(R.id.txvTime);
        playBtn = findViewById(R.id.btnPlay);
        forwardBtn = findViewById(R.id.btnForward);
        rewindBtn = findViewById(R.id.btnRewind);
        backBtn = findViewById(R.id.btnBack);
        seekBar = findViewById(R.id.skBar);
        /** ToolBar layout*/
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("music on storage device");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        /** Object of inner class {@link MySongTrack()} */
        MySongTrack myTrack = new MySongTrack();
        myTrack.start();
        /** check permission And Call extract method when user accept permission */
        checkPermission();

        /**
         * Play Song BY default run first Music -> zero position in the list
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
                    playSong(getMusicList(indexSong).getSongPath(), getMusicList(indexSong).getSongName());
                } else if (mPlayer.isPlaying() && mPlayer != null) {
                    lengthCurrentSong = mPlayer.getCurrentPosition();
                    mPlayer.pause();
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
         * Rewind Button
         */
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong > 0) {
                    indexSong = indexSong - 1;
                    playSong(getMusicList(indexSong).getSongPath(), getMusicList(indexSong).getSongName());
                } else {
                    playSong(getMusicList(indexSong).getSongPath(), getMusicList(indexSong).getSongName());
                    indexSong = 0;
                }
            }
        });
        /**
         * Forward Button
         */
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indexSong < (musicList.size() - 1)) {
                    indexSong = indexSong + 1;
                    playSong(getMusicList(indexSong).getSongPath(), getMusicList(indexSong).getSongName());
                } else {
                    playSong(getMusicList(indexSong).getSongPath(), getMusicList(indexSong).getSongName());
                    indexSong = 0;
                }
            }
        });
        /** Back to MainActivity*/
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(InternalMusicActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                return;
            }
            /** When user come back again */
            extractMedia();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    extractMedia();
                } else {
                    Toast.makeText(InternalMusicActivity.this, "Sorry you should accept to access storage device", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * This method work for two process  :
     * First: extract data from internal storage, And save this data inside {@link #musicList}
     * Second : Create new obj of {@link InternalMusicAdapter} to set {@link #listView} Adapter .
     */
    private void extractMedia() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artiestColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                int songID = cursor.getInt(idColumn);
                String songTitle = cursor.getString(titleColumn);
                String songArtiest = cursor.getString(artiestColumn);
                String songPath = cursor.getString(songData);
                musicList.add(new Music(songID, songTitle, songArtiest, songPath));
            } while (cursor.moveToNext());
        }
        InternalMusicAdapter adapter = new InternalMusicAdapter(InternalMusicActivity.this, musicList);
        listView.setAdapter(adapter);
        /*ListView onClickListener */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** update index song when clicked list*/
                indexSong = position;
                playSong(getMusicList(position).getSongPath(), getMusicList(position).getSongName());
            }
        });
    }

    /**
     * @param p position of object {@link Music}.
     * @return current obj of Music
     */
    private Music getMusicList(int p) {
        return musicList.get(p);
    }

    /**
     * @param songPath to set source Media path .
     * @param songName to set song name in {@link #currentSongName}
     */
    private void playSong(String songPath, String songName) {
        releaseSong();
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(songPath);
            mPlayer.prepare();
            mPlayer.start();
            seekBar.setMax(mPlayer.getDuration());
            playBtn.setBackgroundResource(R.drawable.ic_pause_white);
            currentSongName.setText(songName);
            mPlayer.setOnCompletionListener(completionListener);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * when change song, finish the previous song , And when leave app OR the song is finished
     */
    private void releaseSong() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
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
     * Inner Class for update {@link #seekBar}.
     */
    private class MySongTrack extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPlayer != null) {
                            seekBar.setProgress(mPlayer.getCurrentPosition());
                            long currentPosition = mPlayer.getCurrentPosition();
                            String str = ConvertTime.millisecond(currentPosition);
                            songTimer.setText(str);
                        } else {
                            songTimer.setText("00:00");
                            seekBar.setProgress(0);
                        }
                    }
                });

            }
        }
    }
}
