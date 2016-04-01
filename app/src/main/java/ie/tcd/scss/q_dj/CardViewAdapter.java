package ie.tcd.scss.q_dj;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DataObjectHolder> {
    private ArrayList<Song> mDataset;
    private static MyClickListener myClickListener;

    String code;
    private static final String USER_ID = "0000";

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView song, artist, userID, upVoted;
        ImageView upVote, image;
        public DataObjectHolder(View itemView, final Context context) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.song_name);
            artist = (TextView) itemView.findViewById(R.id.artist_name);
            userID = (TextView) itemView.findViewById(R.id.userid);
            upVoted = (TextView) itemView.findViewById(R.id.upVoted);
            //image = (ImageView) itemView.findViewById(R.id.photo);
            upVoted.setVisibility(View.GONE);
            upVote = (ImageView) itemView.findViewById(R.id.upVote);
            upVote.setClickable(true);

            final MediaPlayer sound = MediaPlayer.create(context, R.raw.success1);
            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upVote.setVisibility(View.GONE);
                    upVoted.setVisibility(View.VISIBLE);
                    sound.start();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(userID.getText().toString() + "|" + song.getText().toString()
                            + "|" + artist.getText().toString());
                    try {
                        ServerComms.addSong(
                                USER_ID,
                                code,
                                userID.getText().toString(),
                                song.getText().toString(),
                                artist.getText().toString(),
                                3000
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        CardViewAdapter.myClickListener = myClickListener;
    }

    public CardViewAdapter(ArrayList<Song> myDataset, String code) {
        mDataset = myDataset;
        this.code = code;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row, parent, false);
        return new DataObjectHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.song.setText(mDataset.get(position).getTitle());
        holder.artist.setText(mDataset.get(position).getArtist());
        holder.userID.setText(mDataset.get(position).getSpotifyID());
        //Ion.with(holder.image).error(R.mipmap.ic_launcher).load(mDataset.get(position).getImage());
    }

    public void addItem(Song dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        if(mDataset != null)
            return mDataset.size();
        return 0;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}