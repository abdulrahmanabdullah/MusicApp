package abdulrahmanjavanrd.com.musicbook_demo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import abdulrahmanjavanrd.com.musicbook_demo2.R;
import abdulrahmanjavanrd.com.musicbook_demo2.model.Music;

/**
 * Created by nfs05 on 16/12/2017.
 */

public class TypesMusicAdapter extends BaseAdapter {

    List<Music> listMusic;
    Context context;

    public TypesMusicAdapter(Context ctx, List<Music> list) {
        this.context = ctx;
        this.listMusic = list;
    }

    @Override
    public int getCount() {
        return listMusic.size();
    }

    @Override
    public Music getItem(int position) {
        return listMusic.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_main_music_layout, parent, false);
        ImageView img = v.findViewById(R.id.imgTypeMusic);
        TextView txv = v.findViewById(R.id.txvTypesMusic);
        img.setImageResource(getItem(position).getId());
        txv.setText(getItem(position).getSongName());
        return v;
    }
}
