package abdulrahmanjavanrd.com.musicbook_demo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.model.RawMusic;

/**
 * Created by nfs05 on 13/12/2017.
 */

public class RawAdapter extends BaseAdapter {
  private   Context context ;
   private List<RawMusic> listMusic ;

    public RawAdapter(Context ctx , List<RawMusic> theList){
        this.context = ctx ;
        this.listMusic = theList ;
    }
    @Override
    public int getCount() {
        return listMusic.size();
    }

    @Override
    public RawMusic getItem(int position) {
        return listMusic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_music,parent,false);
        TextView songName = v.findViewById(R.id.txv_song_name);
        TextView songArtist = v.findViewById(R.id.txv_song_artist);
        songName.setText(getItem(position).getSongName());
        songArtist.setText(getItem(position).getArtistName());
        return v;
    }
}
