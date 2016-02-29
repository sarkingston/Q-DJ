package ie.tcd.scss.q_dj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sam on 24/2/16.
 */
public class CreatePlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        Button btnM = (Button)findViewById(R.id.host);
        /**Opens up QHost activity*/
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePlaylistActivity.this, HostActivity.class));
            }
        });

    }
}
