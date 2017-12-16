package abdulrahmanjavanrd.com.musicbook_demo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;

/**
 * Created by
 * @author Abdulrahman.A
 * @since on 16/12/2017.
 */

public class WorkoutAdapter extends BaseAdapter {
    private List<Music> musicList;
    private Context context;

    public WorkoutAdapter(Context ctx, List<Music> list) {
        this.context = ctx;
        this.musicList = list;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Music getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_music, parent, false);
        TextView songName = v.findViewById(R.id.txv_song_name);
        TextView songArtist = v.findViewById(R.id.txv_song_artist);
        songName.setText(getItem(position).getSongName());
        songArtist.setText(getItem(position).getSongArtiest());
        return v;
    }
}
