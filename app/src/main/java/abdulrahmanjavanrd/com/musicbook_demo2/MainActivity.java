package abdulrahmanjavanrd.com.musicbook_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import abdulrahmanjavanrd.com.musicbook_demo2.intents.RawSongsActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgInternal , imgRaw ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgInternal = findViewById(R.id.imgInternal);

        imgInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open new intent
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
