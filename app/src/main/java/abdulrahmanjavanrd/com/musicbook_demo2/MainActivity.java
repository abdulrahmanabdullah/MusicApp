package abdulrahmanjavanrd.com.musicbook_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.activities.WorkoutMusicActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.adapters.TypesMusicAdapter;
import abdulrahmanjavanrd.com.musicbook_demo2.activities.InternalMusicActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.activities.ClassicalMusicActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listMusicTypes;
    List<Music> musicList;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.mainToolbar);
        listMusicTypes = findViewById(R.id.list_music_types);
        setSupportActionBar(toolbar);
        TypesMusicAdapter adapter = new TypesMusicAdapter(this, getMusicTypes());
        listMusicTypes.setAdapter(adapter);
        listMusicTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mIntent = new Intent(MainActivity.this, ClassicalMusicActivity.class);
                        startActivity(mIntent);
                        break;
                    case 1:
                        mIntent = new Intent(MainActivity.this, WorkoutMusicActivity.class);
                        startActivity(mIntent);
                        break;
                    case 2:
                        mIntent = new Intent(MainActivity.this, InternalMusicActivity.class);
                        startActivity(mIntent);
                        break;

                }
            }
        });
    }
    private List<Music> getMusicTypes() {
        musicList = new ArrayList<>();
        musicList.add(new Music(R.drawable.classical_music_new_size, "Classical Music"));
        musicList.add(new Music(R.drawable.workout_music_new_size, "Workout Music"));
        musicList.add(new Music(R.drawable.music_internal_new_size, "Internal Music"));
        return musicList;
    }
}
