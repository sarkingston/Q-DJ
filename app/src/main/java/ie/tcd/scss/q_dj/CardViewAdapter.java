package ie.tcd.scss.q_dj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DataObjectHolder> {

    private static final String TAG = "MyActivity";

    private ArrayList<Song> mDataset;
    private static MyClickListener myClickListener;

    String code;
    private static final String USER_ID = "0000";

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView song, artist, userID, upVoted, duration;
        ImageView upVote, image;
        public DataObjectHolder(View itemView, final Context context) {
            super(itemView);
            song = (TextView) itemView.findViewById(R.id.song_name);
            artist = (TextView) itemView.findViewById(R.id.artist_name);
            userID = (TextView) itemView.findViewById(R.id.userid);
            upVoted = (TextView) itemView.findViewById(R.id.upVoted);
            image = (ImageView) itemView.findViewById(R.id.photo);
            duration = (TextView) itemView.findViewById(R.id.length);
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
                    System.out.println("********************************");
                    System.out.println("********************************");
                    System.out.println("********************************");
                    System.out.println("********************************");
                    System.out.println("********************************");
                    System.out.println("********************************");
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
                        System.out.println("*************** SONG ADDED **************");
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

        //Code to change the 'double' duration to a string in time format
        double timeInMillis = mDataset.get(position).getDuration();
        long longTimeInMillis = (long)timeInMillis;
        String result = (calculateDifference(longTimeInMillis));
        holder.duration.setText(result);

        //holder.userID.setText(mDataset.get(position).getSpotifyID());

        holder.image.setImageBitmap(getImageBitmap(mDataset.get(position).getImage()));


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

    /**
     * Gets the url for the image and returns a bitmap image
     * @param url url passed from the JSONArray
     * @return
     */
    public Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

    private String calculateDifference(long timeInMillis){
        int minutes = (int) ((timeInMillis / 60 ) % 60);
        int seconds = (int) ((timeInMillis) % 60);
        return minutes+":"+seconds;
    }
}