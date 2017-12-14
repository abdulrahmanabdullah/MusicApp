package abdulrahmanjavanrd.com.musicbook_demo2.intents;

import android.Manifest;
import android.content.ContentResolver;
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
import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.apaters.InternalMusicAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.model.InternalMusic;
import abdulrahmanjavanrd.com.musicbook_demo2.utilities.ConvertTime;

/**
 * Created by
 * @author abdulrahma abdullah
 * @since on 14/12/2017.
 */

public class InternalMusicActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 123 ;
    private int  indexSong ;
    Toolbar toolbar ;
    MediaPlayer mPlayer;
    Button playBtn , forwardBtn ,rewindBtn ;
    TextView currentSongName , songTimer ;
    SeekBar seekBar ;
    ListView listView ;
    List<InternalMusic> musicList = new ArrayList<>();
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intrnal_song_activity);
        /**  init views */
        toolbar = findViewById(R.id.thirdActivity_toolbar);
        listView =findViewById(R.id.list_view);
        currentSongName = findViewById(R.id.txvCurrentSong);
        songTimer = findViewById(R.id.txvTime);
        playBtn = findViewById(R.id.btnPlay);
        forwardBtn = findViewById(R.id.btnForward);
        rewindBtn = findViewById(R.id.btnRewind);
        seekBar =findViewById(R.id.skBar);
        /** ToolBar layout*/
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("music on storage device");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        MySongTrack myTrack = new MySongTrack();
        myTrack.start();
        /** check permission And Call extract method when user accept permission */
       checkPermission();

        /**
         * Play Button
         */
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InternalMusicActivity.this,"clicked ",Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * Rewind Button
         */
        rewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InternalMusicActivity.this,"clicked ",Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Forward Button
         */
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InternalMusicActivity.this,"clicked ",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= 23) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
                return;
            }
            /** When user come back again */
            extractMedia();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    extractMedia();
                }else {
                    //TODO: Here if user not accept to access internal storage .
                    Toast.makeText(InternalMusicActivity.this,"Sorry you should  Accept our term.",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void extractMedia(){
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int titleColumn =cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artiestColumn =cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do{
                int songID = cursor.getInt(idColumn);
                String songTitle = cursor.getString(titleColumn);
                String songArtiest = cursor.getString(artiestColumn);
                String songPath = cursor.getString(songData);
                musicList.add(new InternalMusic(songID,songTitle,songArtiest,songPath));
            }while (cursor.moveToNext());
        }
        InternalMusicAdapter adapter = new InternalMusicAdapter(InternalMusicActivity.this,musicList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseSong();
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(getMusicList(position).getSongPath());
                    mPlayer.prepare();
                    mPlayer.start();
                    seekBar.setMax(mPlayer.getDuration());
                    currentSongName.setText(getMusicList(position).getSongName());
                    playBtn.setBackgroundResource(R.drawable.ic_pause_white);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private InternalMusic getMusicList(int p){
       return musicList.get(p);
   }
    private void playSong(int position ,String songName){
        releaseSong();
        mPlayer = new MediaPlayer();
        try{
           mPlayer.setDataSource(musicList.get(position).getSongPath());
           mPlayer.prepare();
           mPlayer.start();
           currentSongName.setText(songName);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void releaseSong(){
        if (mPlayer !=null){
            mPlayer.release();
            mPlayer = null ;
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

    class MySongTrack extends Thread{


        public void run(){
           while (true){
               try{
                   Thread.sleep(1000);
               }catch (Exception e){
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
                       }else{
                           songTimer.setText("00:00");
                           seekBar.setProgress(0);
                       }
                   }
               });

           }
        }
    }
}
