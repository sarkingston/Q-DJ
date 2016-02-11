package ie.tcd.scss.q_dj;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by Alex on 28/11/15.
 */

/**
 * Java Class intended
 *  to help the Main2Activity class
 *  populate a list within
 *  a layout
 */
public class CardViewHelper extends RecyclerView.Adapter<CardViewHelper.StockViewHolder>{


    List<ServerComms> stocks;
    public CardViewHelper(List<ServerComms> stocks){
        this.stocks = stocks;
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        StockViewHolder pvh = new StockViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int position)
    {
        //holder.stockName.setText(stocks.get(position).getName());
        //holder.stockAlbum.setText(stocks.get(position).getAlbum());
        /****************************************
         * load pic through here,RIGHT BELOW
         * **********************************************
         */
        Ion.with(holder.stockPhoto).error(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView stockName;
        TextView stockAlbum;
        ImageView stockPhoto;

        StockViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            stockName = (TextView)itemView.findViewById(R.id.name);
            stockAlbum = (TextView)itemView.findViewById(R.id.album);
            stockPhoto = (ImageView)itemView.findViewById(R.id.photo);
        }
    }
}