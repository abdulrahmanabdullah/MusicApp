package abdulrahmanjavanrd.com.musicbook_demo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.model.InternalMusic;

public class InternalMusicAdapter extends BaseAdapter {

    private List<InternalMusic> musicList;
    private Context context;

    public InternalMusicAdapter(Context context, List<InternalMusic> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public InternalMusic getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_music, parent, false);
        System.out.println("Hi from get View . ");
        TextView songName = v.findViewById(R.id.txv_song_name);
        TextView songArtiest = v.findViewById(R.id.txv_song_artist);
        songName.setText(getItem(position).getSongName());
        songArtiest.setText(getItem(position).getSongArtiest());
        return v;
    }
}
