package abdulrahmanjavanrd.com.musicbook_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.adapters.TypesMusicAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.intents.InternalMusicActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.intents.RawSongsActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar ;
    ImageView imgInternal , imgRaw ;
    ListView listMusicTypes ;
    List<Music> musicList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imgInternal = findViewById(R.id.imgInternal);
        toolbar =findViewById(R.id.mainToolbar);
        listMusicTypes = findViewById(R.id.list_music_types);
        setSupportActionBar(toolbar);
        setMusicTypes();
        TypesMusicAdapter adapter = new TypesMusicAdapter(this,musicList);
        listMusicTypes.setAdapter(adapter);
//        imgInternal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        imgRaw = findViewById(R.id.imgRaw);
//        imgRaw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        listMusicTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Intent  mIntent = new Intent(MainActivity.this, RawSongsActivity.class);
                        startActivity(mIntent);
                    break;
                    case 2 :
                        Intent intent = new Intent(MainActivity.this, InternalMusicActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void setMusicTypes(){
        Music classicalMusic = new Music(R.drawable.classical_music_new_size,"Classical Music");
        Music workoutMusic = new Music(R.drawable.workout_music_new_size,"Workout Music");
        Music internalMusic = new Music(R.drawable.music_internal_new_size,"Internal Music");
       musicList.add(classicalMusic);
       musicList.add(workoutMusic);
       musicList.add(internalMusic);
    }
}
