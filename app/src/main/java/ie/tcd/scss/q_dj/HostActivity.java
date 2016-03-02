package ie.tcd.scss.q_dj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Sam on 24/2/16.
 */
public class HostActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    com.getbase.floatingactionbutton.FloatingActionButton play;
    boolean playActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        play = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.play);

        final int idPlay = R.mipmap.ic_play_arrow_white_24dp;
        final int idPause = R.mipmap.ic_pause_white_24dp;
        play.setIcon(idPlay);
        playActive = true;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playActive) {
                    play.setIcon(idPause);
                    playActive = false;
                }
                else {
                    play.setIcon(idPlay);
                    playActive = true;
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            mAdapter = new CardViewAdapter(ServerComms.getQueue("0"));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(mAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        ((CardViewAdapter) mAdapter).setOnItemClickListener(new CardViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
    }
}
