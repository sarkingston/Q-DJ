package ie.tcd.scss.q_dj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by UI Team on 29/01/16.
 */

public class HostOrClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_or_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Store their Choice here and Direct them to the appropriate Screen

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /**No floating action btton available in HostorClient Activity*/
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        RadioButton btnH = (RadioButton)findViewById(R.id.radioButtonHost);
        RadioButton btnG = (RadioButton)findViewById(R.id.radioButtonGuest);
        /**Opens up QHost activity*/
        btnH.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HostOrClient.this, QHost.class));
            }
        });
        /**Opens up ChooseHost activity activity*/
        btnG.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HostOrClient.this, ChooseHost.class));
            }
        });
    }

}
