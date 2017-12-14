package abdulrahmanjavanrd.com.musicbook_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import abdulrahmanjavanrd.com.musicbook_demo2.intents.InternalMusicActivity;
import abdulrahmanjavanrd.com.musicbook_demo2.intents.RawSongsActivity;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar ;
    ImageView imgInternal , imgRaw ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgInternal = findViewById(R.id.imgInternal);
        toolbar =findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        imgInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, InternalMusicActivity.class);
                startActivity(mIntent);
            }
        });
        imgRaw = findViewById(R.id.imgRaw);
        imgRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  mIntent = new Intent(MainActivity.this, RawSongsActivity.class);
                startActivity(mIntent);

            }
        });
    }
}
